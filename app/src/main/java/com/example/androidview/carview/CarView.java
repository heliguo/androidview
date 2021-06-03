package com.example.androidview.carview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidview.R;

/**
 * @author lgh on 2021/4/28 11:14
 * @description
 */
public class CarView extends View {

    private Path mPath;
    private PathMeasure mPathMeasure;
    private float distanceRatio = 0f;
    private Paint circlePaint;
    private Paint carPaint;
    private Matrix mMatrix;
    private Bitmap carBitmap;


    public CarView(Context context) {
        this(context, null);
    }

    public CarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        carBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_car);
        mPath = new Path();
        mPath.addCircle(0, 0, 200, Path.Direction.CW);
        mPathMeasure = new PathMeasure(mPath, false);
        mMatrix = new Matrix();

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStrokeWidth(5);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.BLACK);

        carPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        carPaint.setColor(Color.DKGRAY);
        carPaint.setStrokeWidth(2);
        carPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        int width = getWidth();
        int height = getHeight();

        canvas.translate(width / 2f, height / 2f);
        mMatrix.reset();
        distanceRatio += 0.006f;
        if (distanceRatio >= 1) {
            distanceRatio = 0;
        }

        float distance = mPathMeasure.getLength() * distanceRatio;
        float[] pos = new float[2];
        float[] tan = new float[2];

        mPathMeasure.getPosTan(distance, pos, tan);
        //tan[1] sin , tan[0] cos
        float degree = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);

        mMatrix.postRotate(degree, carBitmap.getWidth() / 2f, carBitmap.getHeight() / 2f);

        mMatrix.postTranslate(pos[0] - carBitmap.getWidth() / 2f, pos[1] - carBitmap.getHeight() / 2f);

        canvas.drawPath(mPath, circlePaint);

        canvas.drawBitmap(carBitmap, mMatrix, carPaint);

        invalidate();
    }
}
