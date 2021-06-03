package com.example.androidview;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.example.androidview.backpress.BackPressObserver;
import com.example.androidview.backpress.BackPressRegistry;

/**
 * @author lgh on 2021/4/1 12:08
 * @description
 */
public class BaseActivity extends AppCompatActivity {

    public final static boolean GRAY = false;

    private final BackPressRegistry mBackPressRegistry = new BackPressRegistry();
    private Toast mToast;

    public void registerBackPress(LifecycleOwner owner, BackPressObserver backPressObserver) {

        mBackPressRegistry.registerBackPress(owner, backPressObserver);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(this.getClass().getSimpleName(), "onCreate: " + getWindow().getDecorView());
        if (GRAY) {
            Paint paint = new Paint();
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
        }
    }

    @Override
    public void onBackPressed() {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, "you click back press", Toast.LENGTH_SHORT);
        mToast.show();
        if (mBackPressRegistry.dispatchBackPress())
            return;
        super.onBackPressed();
    }

    //    @Nullable
    //    @Override
    //    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
    //        if (!GRAY) {
    //            return super.onCreateView(name, context, attrs);
    //        }
    //        try {
    //            if ("FrameLayout".equals(name)) {
    //                int count = attrs.getAttributeCount();
    //                for (int i = 0; i < count; i++) {
    //                    String attributeName = attrs.getAttributeName(i);
    //                    String attributeValue = attrs.getAttributeValue(i);
    //                    if (attributeName.equals("id")) {
    //                        int id = Integer.parseInt(attributeValue.substring(1));
    //                        String idVal = getResources().getResourceName(id);
    //                        if ("android:id/content".equals(idVal)) {
    //                            return new GrayFrameLayout(context, attrs);
    //                        }
    //                    }
    //                }
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //
    //        return super.onCreateView(name, context, attrs);
    //
    //    }


}
