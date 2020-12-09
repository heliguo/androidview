package com.example.androidview.hencoder.draw.canvas_4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/12/1 10:25
 * @description
 */
public class ClipAndMatrix extends View {

    private int top;
    private int left;
    private int right;
    private int bottom;
    private Bitmap bitmap;
    private Paint paint;

    public ClipAndMatrix(Context context) {
        super(context);
    }

    public ClipAndMatrix(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClipAndMatrix(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         * 剪裁
         */

        /**
         * clipRect() clipPath()
         * Canvas.save() 和 Canvas.restore() 来及时恢复绘制范围
         */
        float x = 0;
        float y = 0;
        canvas.save();
        canvas.clipRect(left, top, right, bottom);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         * 几何变换
         *
         * 使用 Canvas 来做常见的二维变换；
         * 使用 Matrix 来做常见和不常见的二维变换；
         * 使用 Camera 来做三维变换
         */

        /**
         * Canvas.translate(float dx, float dy) 平移
         */
        canvas.save();
        canvas.translate(200, 0);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        /**
         * Canvas.rotate(float degrees, float px, float py) 旋转
         */
        canvas.save();
        canvas.rotate(45, 100, 100);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        /**
         * Canvas.scale(float sx, float sy, float px, float py) 放缩
         */
        canvas.save();
        canvas.scale(1.3f, 1.3f, x + bitmap.getWidth() / 2f, y + bitmap.getHeight() / 2f);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        /**
         * skew(float sx, float sy) 错切
         * 参数里的 sx 和 sy 是 x 方向和 y 方向的错切系数。
         */
        canvas.save();
        canvas.skew(0, 0.5f);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        /**
         * Matrix 做常见变换的方式：
         *
         * 创建 Matrix 对象；
         * 调用 Matrix 的 pre/postTranslate/Rotate/Scale/Skew() 方法来设置几何变换；
         * 使用 Canvas.setMatrix(matrix) 或 Canvas.concat(matrix) 来把几何变换应用到 Canvas。
         *
         * 把 Matrix 应用到 Canvas 有两个方法： Canvas.setMatrix(matrix) 和 Canvas.concat(matrix)。
         *
         * Canvas.setMatrix(matrix)：用 Matrix 直接替换 Canvas 当前的变换矩阵，即抛弃 Canvas 当前的变换，
         * 改用 Matrix 的变换（注：不同的系统中 setMatrix(matrix) 的行为可能不一致 ，尽量用 concat(matrix)）；
         * Canvas.concat(matrix)：用 Canvas 当前的变换矩阵和 Matrix 相乘，即基于 Canvas 当前的变换，叠加上 Matrix 中的变换。
         */
        Matrix matrix = new Matrix();


        matrix.reset();
//        matrix.postTranslate();
//        matrix.postRotate();

        canvas.save();
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        /**
         * Matrix.setPolyToPoly(float[] src, int srcIndex, float[] dst, int dstIndex, int pointCount) 用点对点映射的方式设置变换
         * poly 就是「多」的意思。setPolyToPoly() 的作用是通过多点的映射的方式来直接设置变换。
         * 「多点映射」的意思就是把指定的点移动到给出的位置，从而发生形变。
         * 例如：(0, 0) -> (100, 100) 表示把 (0, 0) 位置的像素移动到 (100, 100) 的位置，这个是单点的映射，
         * 单点映射可以实现平移。而多点的映射，就可以让绘制内容任意地扭曲。
         */

        float[] pointsSrc = {left, top, right, top, left, bottom, right, bottom};
        float[] pointsDst = {left - 10, top + 50, right + 120, top - 90, left + 20, bottom + 30, right + 20, bottom + 60};

        matrix.reset();
        matrix.setPolyToPoly(pointsSrc, 0, pointsDst, 0, 4);

        canvas.save();
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        /**
         * Camera 的三维变换有三类：旋转、平移、移动相机。
         */

        /**
         * Camera.rotate*() 三维旋转
         * 一共有四个方法： rotateX(deg) rotateY(deg) rotateZ(deg) rotate(x, y, z)
         *
         * Canvas 的几何变换顺序是反的，要把移动到中心的代码写在下面，把从中心移动回来的代码写在上面
         */
        Camera camera = new Camera();
        canvas.save();

        camera.rotateX(30); // 旋转 Camera 的三维空间
        camera.applyToCanvas(canvas); // 把旋转投影到 Canvas

        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();

        /**
         * Camera.setLocation(x, y, z) 设置虚拟相机的位置
         * 它的参数的单位不是像素，而是 inch，英寸。
         *
         * 在 Camera 中，相机的默认位置是 (0, 0, -8)（英寸）。8 x 72 = 576，所以它的默认位置是 (0, 0, -576)（像素）
         */
    }
}
