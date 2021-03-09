package com.example.androidview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.androidview.R;

/**
 * @author lgh on 2021/3/3 19:55
 * @description
 */
public class CustomRoundAngleImageView extends AppCompatImageView {

    private float width, height;
    private Path mPath;

    private final int defaultRadius = 10;
    private int radius;
    private int leftTopRadius;
    private int rightTopRadius;
    private int rightBottomRadius;
    private int leftBottomRadius;

    public CustomRoundAngleImageView(Context context) {
        this(context, null);
    }

    public CustomRoundAngleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRoundAngleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPath = new Path();
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomRoundAngleImageView);
        radius = array.getDimensionPixelOffset(R.styleable.CustomRoundAngleImageView_radius, defaultRadius);
        leftTopRadius = array.getDimensionPixelOffset(R.styleable.CustomRoundAngleImageView_left_top_radius, defaultRadius);
        rightTopRadius = array.getDimensionPixelOffset(R.styleable.CustomRoundAngleImageView_right_top_radius, defaultRadius);
        rightBottomRadius = array.getDimensionPixelOffset(R.styleable.CustomRoundAngleImageView_right_bottom_radius, defaultRadius);
        leftBottomRadius = array.getDimensionPixelOffset(R.styleable.CustomRoundAngleImageView_left_bottom_radius, defaultRadius);

        //如果四个角的值没有设置，那么就使用通用的radius的值。
        if (defaultRadius == leftTopRadius) {
            leftTopRadius = radius;
        }
        if (defaultRadius == rightTopRadius) {
            rightTopRadius = radius;
        }
        if (defaultRadius == rightBottomRadius) {
            rightBottomRadius = radius;
        }
        if (defaultRadius == leftBottomRadius) {
            leftBottomRadius = radius;
        }
        array.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //只有图片的宽高大于设置的圆角距离的时候才进行裁剪
        int maxLeft = Math.max(leftTopRadius, leftBottomRadius);
        int maxRight = Math.max(rightTopRadius, rightBottomRadius);
        int minWidth = maxLeft + maxRight;
        int maxTop = Math.max(leftTopRadius, rightTopRadius);
        int maxBottom = Math.max(leftBottomRadius, rightBottomRadius);
        int minHeight = maxTop + maxBottom;
        if (width >= minWidth && height > minHeight) {
            mPath.reset();
            //四个角：右上，右下，左下，左上
            mPath.moveTo(leftTopRadius, 0);
            mPath.lineTo(width - rightTopRadius, 0);
            mPath.quadTo(width, 0, width, rightTopRadius);

            mPath.lineTo(width, height - rightBottomRadius);
            mPath.quadTo(width, height, width - rightBottomRadius, height);

            mPath.lineTo(leftBottomRadius, height);
            mPath.quadTo(0, height, 0, height - leftBottomRadius);

            mPath.lineTo(0, leftTopRadius);
            mPath.quadTo(0, 0, leftTopRadius, 0);

            canvas.clipPath(mPath);
        }
        super.onDraw(canvas);
    }

}

