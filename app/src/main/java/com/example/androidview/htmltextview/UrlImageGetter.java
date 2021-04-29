package com.example.androidview.htmltextview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class UrlImageGetter implements Html.ImageGetter {
    Context c;
    TextView container;
    Paint mPaint;
    float scale = 1f;

    /**
     * @param t
     * @param c
     */
    public UrlImageGetter(TextView t, Context c) {
        this.c = c;
        this.container = t;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(t.getTextSize());
    }

    @Override
    public Drawable getDrawable(String source) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        Glide.with(container.getContext()).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap loadedImage, @Nullable Transition<? super Bitmap> transition) {
                scale = getTextHeight() / loadedImage.getHeight();
                urlDrawable.bitmap = loadedImage;
                urlDrawable.setBounds(0, 0, ((int) (loadedImage.getWidth() * scale)),
                        ((int) (loadedImage.getHeight() * scale)));
                container.invalidate();
                container.setText(container.getText());
            }
        });
        return urlDrawable;
    }

    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Bitmap bitmap;
        ColorMatrix cm = new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                -1, -1, -1, 0, 765//将图片白色转换成透明
        });

        @Override
        public void draw(Canvas canvas) {
            if (bitmap != null) {
                getPaint().setColorFilter(new ColorMatrixColorFilter(cm));
                Matrix matrix = new Matrix();
                matrix.setScale(scale, scale);
                canvas.drawBitmap(bitmap, matrix, getPaint());
            }
        }
    }

    private float getTextHeight() {
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }
}