package com.example.androidview.view;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * An ImageView for displaying an Icon. Avoids reloading the Icon when possible.
 * Copy from CachingIconView
 */
public class CachingImageView extends AppCompatImageView {

    private String mLastPackage;
    private int mLastResId;
    private boolean mInternalSetDrawable;
    private boolean mForceHidden;
    private int mDesiredVisibility;

    private Uri mUri;
    private int mResource = 0;

    public CachingImageView(@NonNull Context context) {
        this(context, null);
    }

    public CachingImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CachingImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void setImageIcon(@Nullable Icon icon) {
        if (!testAndSetCache(icon)) {
            mInternalSetDrawable = true;
            // This calls back to setImageDrawable, make sure we don't clear the cache there.
            super.setImageIcon(icon);
            mInternalSetDrawable = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Runnable setImageIconAsync(@Nullable Icon icon) {
        resetCache();
        return new ImageDrawableCallback(icon == null ? null : icon.loadDrawable(getContext()), null, 0);
    }


    private class ImageDrawableCallback implements Runnable {
        private final Drawable drawable;
        private final Uri uri;
        private final int resource;

        ImageDrawableCallback(Drawable drawable, Uri uri, int resource) {
            this.drawable = drawable;
            this.uri = uri;
            this.resource = resource;
        }

        @Override
        public void run() {
            setImageDrawable(drawable);
            mUri = uri;
            mResource = resource;
        }
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        if (!testAndSetCache(resId)) {
            mInternalSetDrawable = true;
            // This calls back to setImageDrawable, make sure we don't clear the cache there.
            super.setImageResource(resId);
            mInternalSetDrawable = false;
        }
    }

    public Runnable setImageResourceAsync(@DrawableRes int resId) {
        resetCache();
        Drawable d = null;
        if (resId != 0) {
            try {
                d = ContextCompat.getDrawable(getContext(), resId);
            } catch (Exception e) {
                resId = 0;
            }
        }
        return new ImageDrawableCallback(d, null, resId);
    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        resetCache();
        super.setImageURI(uri);
    }

    public Runnable setImageURIAsync(@Nullable Uri uri) {
        resetCache();
        if (mResource != 0 || (mUri != uri && (uri == null || mUri == null || !uri.equals(mUri)))) {
            Drawable d = uri == null ? null : getDrawableFromUri(uri);
            if (d == null) {
                // Do not set the URI if the drawable couldn't be loaded.
                uri = null;
            }
            return new ImageDrawableCallback(d, uri, 0);
        }
        return null;
    }

    private Drawable getDrawableFromUri(Uri uri) {
        final String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme)) {
            try {
                // Load drawable through Resources, to get the source density information
                OpenResourceIdResult r = getResourceId(uri);
                return ResourcesCompat.getDrawable(getResources(),r.id, getContext().getTheme());
            } catch (Exception e) {
            }
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)
                || ContentResolver.SCHEME_FILE.equals(scheme)) {
            try {
                ImageDecoder.Source src = ImageDecoder.createSource(getContext().getContentResolver(), uri);
                return ImageDecoder.decodeDrawable(src, (decoder, info, s) -> {
                    decoder.setAllocator(ImageDecoder.ALLOCATOR_SOFTWARE);
                });
            } catch (IOException e) {
            }
        } else {
            return Drawable.createFromPath(uri.toString());
        }
        return null;
    }

    private static class OpenResourceIdResult {
        public Resources r;
        public int id;
    }

    public OpenResourceIdResult getResourceId(Uri uri) throws FileNotFoundException {
        String authority = uri.getAuthority();
        Resources r;
        if (TextUtils.isEmpty(authority)) {
            throw new FileNotFoundException("No authority: " + uri);
        } else {
            try {
                r = getContext().getPackageManager().getResourcesForApplication(authority);
            } catch (PackageManager.NameNotFoundException ex) {
                throw new FileNotFoundException("No package found for authority: " + uri);
            }
        }
        List<String> path = uri.getPathSegments();
        if (path == null) {
            throw new FileNotFoundException("No path: " + uri);
        }
        int len = path.size();
        int id;
        if (len == 1) {
            try {
                id = Integer.parseInt(path.get(0));
            } catch (NumberFormatException e) {
                throw new FileNotFoundException("Single path segment is not a resource ID: " + uri);
            }
        } else if (len == 2) {
            id = r.getIdentifier(path.get(1), path.get(0), authority);
        } else {
            throw new FileNotFoundException("More than two path segments: " + uri);
        }
        if (id == 0) {
            throw new FileNotFoundException("No resource found for: " + uri);
        }
        OpenResourceIdResult res = new OpenResourceIdResult();
        res.r = r;
        res.id = id;
        return res;
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        if (!mInternalSetDrawable) {
            // Only clear the cache if we were externally called.
            resetCache();
        }
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        resetCache();
        super.setImageBitmap(bm);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetCache();
    }

    /**
     * @return true if the currently set image is the same as {@param icon}
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    private synchronized boolean testAndSetCache(Icon icon) {
        if (icon != null && icon.getType() == Icon.TYPE_RESOURCE) {
            String iconPackage = normalizeIconPackage(icon);

            boolean isCached = mLastResId != 0
                    && icon.getResId() == mLastResId
                    && Objects.equals(iconPackage, mLastPackage);

            mLastPackage = iconPackage;
            mLastResId = icon.getResId();

            return isCached;
        } else {
            resetCache();
            return false;
        }
    }

    /**
     * @return true if the currently set image is the same as {@param resId}
     */
    private synchronized boolean testAndSetCache(int resId) {
        boolean isCached;
        if (resId == 0 || mLastResId == 0) {
            isCached = false;
        } else {
            isCached = resId == mLastResId && null == mLastPackage;
        }
        mLastPackage = null;
        mLastResId = resId;
        return isCached;
    }

    /**
     * Returns the normalized package name of {@param icon}.
     *
     * @return null if icon is null or if the icons package is null, empty or matches the current
     * context. Otherwise returns the icon's package context.
     */
    private String normalizeIconPackage(Icon icon) {
        if (icon == null) {
            return null;
        }

        String pkg = icon.getResPackage();
        if (TextUtils.isEmpty(pkg)) {
            return null;
        }
        if (pkg.equals(getContext().getPackageName())) {
            return null;
        }
        return pkg;
    }

    private synchronized void resetCache() {
        mLastResId = 0;
        mLastPackage = null;
    }

    /**
     * Set the icon to be forcibly hidden, even when it's visibility is changed to visible.
     */
    public void setForceHidden(boolean forceHidden) {
        mForceHidden = forceHidden;
        updateVisibility();
    }

    @Override
    public void setVisibility(int visibility) {
        mDesiredVisibility = visibility;
        updateVisibility();
    }

    private void updateVisibility() {
        int visibility = mDesiredVisibility == VISIBLE && mForceHidden ? INVISIBLE
                : mDesiredVisibility;
        super.setVisibility(visibility);
    }

}

