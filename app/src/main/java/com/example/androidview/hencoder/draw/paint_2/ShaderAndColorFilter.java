package com.example.androidview.hencoder.draw.paint_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ComposePathEffect;
import android.graphics.ComposeShader;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.EmbossMaskFilter;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SumPathEffect;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidview.R;

/**
 * @author lgh on 2020/11/28 11:35
 * @description 着色器和颜色过滤器
 * <p>
 * 直接设置颜色的 API 用来给图形和文字设置颜色；
 * setColorFilter() 用来基于颜色进行过滤处理；
 * setXfermode() 用来处理源图像和 View 已有内容的关系。
 */
public class ShaderAndColorFilter extends View {

    private Paint paint;
    private Shader mShader;//着色器
    private ColorFilter colorFilter;//颜色过滤
    private Xfermode xfermode;

    //athEffect 来给图形的轮廓设置效果。对 Canvas 所有的图形绘制有效，
    // 也就是 drawLine() drawCircle() drawPath() 这些方法
    private PathEffect pathEffect;

    private MaskFilter maskFilter;

    public ShaderAndColorFilter(Context context) {
        this(context, null);
    }

    public ShaderAndColorFilter(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderAndColorFilter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        /**
         * 线性渐变
         *
         * Shader.TileMode.CLAMP,延续终点颜色
         * MIRROR 是镜像模式；REPEAT 是重复模式
         */
        mShader = new LinearGradient(100, 100, 500, 500, Color.parseColor("#e91e63"),
                Color.parseColor("2196f3"), Shader.TileMode.CLAMP);
        /**
         * 辐射渐变
         * 从中心向周围辐射状的渐变
         */
        mShader = new RadialGradient(300, 300, 200, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);

        /**
         *圆形扫描
         */
        mShader = new SweepGradient(300, 300, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"));

        /**
         * 可实现圆形bitmap
         */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap);
        mShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        /**
         * 组合
         * 注：ComposeShader() 在硬件加速下不支持两个相同类型的 Shader
         *
         * PorterDuff.Mode 用来指定两个图像共同绘制时的颜色策略
         *
         * https://developer.android.google.cn/reference/kotlin/android/graphics/PorterDuff.Mode?hl=en#ENUM_VALUE:DARKEN
         */
        // 第一个 Shader：头像的 Bitmap
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap);
        Shader shader1 = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        // 第二个 Shader：从上到下的线性渐变（由透明到黑色）
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap);
        Shader shader2 = new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        // ComposeShader：结合两个 Shader
        mShader = new ComposeShader(shader1, shader2, PorterDuff.Mode.SRC_OVER);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * LightingColorFilter 模拟光照效果
         * LightingColorFilter(int mul, int add)
         *
         * 算法
         * R' = R * mul.R / 0xff + add.R
         * G' = G * mul.G / 0xff + add.G
         * B' = B * mul.B / 0xff + add.B
         */

        /***
         * PorterDuffColorFilter 使用一个指定的颜色和一种指定的 PorterDuff.Mode 来与绘制对象进行合成
         *
         */

        /**
         * ColorMatrixColorFilter
         *
         * ColorMatrix 是一个 4x5 的矩阵
         * [ a, b, c, d, e,
         *   f, g, h, i, j,
         *   k, l, m, n, o,
         *   p, q, r, s, t ]
         *
         * 算法
         * R’ = a*R + b*G + c*B + d*A + e;
         * G’ = f*R + g*G + h*B + i*A + j;
         * B’ = k*R + l*G + m*B + n*A + o;
         * A’ = p*R + q*G + r*B + s*A + t;
         *
         */

        colorFilter = new LightingColorFilter(1, 1);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        /**Xfermode  Transfer mode
         * 以绘制的内容作为源图像，以 View 中已有的内容作为目标图像，选取一个 PorterDuff.Mode 作为绘制内容的颜色处理方案
         *
         * 使用时需使用离屏缓冲
         * Canvas.saveLayer()  可以做短时的离屏缓冲
         *
         * View.setLayerType() View.setLayerType() 是直接把整个 View 都绘制在离屏缓冲中。
         * setLayerType(LAYER_TYPE_HARDWARE) 是使用 GPU 来缓冲
         * setLayerType(LAYER_TYPE_SOFTWARE) 是直接直接用一个 Bitmap 来缓冲。
         */
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        paint.setXfermode(xfermode);


        ////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * 线条形状
         */
        paint.setStrokeWidth(5);//线宽
        paint.setStrokeCap(Paint.Cap.SQUARE);//BUTT 平头、ROUND 圆头、SQUARE 方头。默认为 BUTT
        paint.setStrokeJoin(Paint.Join.BEVEL);//设置拐角的形状,MITER 尖角、 BEVEL 平角和 ROUND 圆角。默认为 MITER
        paint.setStrokeMiter(2);//设置 MITER 型拐角的延长线的最大值,是线条在 Join 类型为 MITER 时对于 MITER 的长度限制

        //////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * 色彩优化
         * 抖动指把图像从较高色彩深度（即可用的颜色数）向较低色彩深度的区域绘制时
         * 在图像中有意地插入噪点，通过有规律地扰乱图像来让图像对于肉眼更加真实的做法
         */

        paint.setDither(true);//设置图像的抖动

        /**
         * 图像在放大绘制的时候，默认使用的是最近邻插值过滤，这种算法简单，但会出现马赛克现象；
         * 而如果开启了双线性过滤，就可以让结果图像显得更加平滑
         */
        paint.setFilterBitmap(true);//是否使用双线性过滤来绘制 Bitmap

        /////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * 注意： PathEffect 在有些情况下不支持硬件加速，需要关闭硬件加速才能正常使用：
         *Canvas.drawLine() 和 Canvas.drawLines() 方法画直线时，setPathEffect() 是不支持硬件加速的；
         *PathDashPathEffect 对硬件加速的支持也有问题，所以当使用 PathDashPathEffect 的时候，最好也把硬件加速关了。
         */

        /**
         * CornerPathEffect 把所有拐角变成圆角,效果为加圆角
         */
        pathEffect = new CornerPathEffect(20);

        /**
         * DiscretePathEffect 把线条进行随机的偏离
         * DiscretePathEffect 具体的做法是，把绘制改为使用定长的线段来拼接，并且在拼接的时候对路径进行随机偏离。
         * 它的构造方法 DiscretePathEffect(float segmentLength, float deviation) 的两个参数中，
         * segmentLength 是用来拼接的每个线段的长度， deviation 是偏离量
         */
        pathEffect = new DiscretePathEffect(20, 5);

        /**
         * DashPathEffect 使用虚线来绘制线条
         * 构造方法 DashPathEffect(float[] intervals, float phase) 中， 第一个参数 intervals 是一个数组，它指定了虚线的格式：
         * 数组中元素必须为偶数（最少是 2 个），按照「画线长度、空白长度、画线长度、空白长度」……的顺序排列，
         * 例如上面代码中的 20, 5, 10, 5 就表示虚线是按照「画 20 像素、空 5 像素、画 10 像素、空 5 像素」的模式来绘制；
         * 第二个参数 phase 是虚线的偏移量
         */
        pathEffect = new DashPathEffect(new float[]{10, 5}, 10);

        /**
         * PathDashPathEffect 使用图形代替画笔
         *
         * 构造方法 PathDashPathEffect(Path shape, float advance, float phase, PathDashPathEffect.Style style) 中，
         * shape 参数是用来绘制的 Path ； advance 是两个相邻的 shape 段之间的间隔，
         * 不过注意，这个间隔是两个 shape 段的起点的间隔，而不是前一个的终点和后一个的起点的距离；
         * phase 和 DashPathEffect 中一样，是虚线的偏移；最后一个参数 style，是用来指定拐弯改变的时候 shape 的转换方式。
         * style 的类型为 PathDashPathEffect.Style ，是一个 enum ，具体有三个值：
         * TRANSLATE：位移
         * ROTATE：旋转
         * MORPH：变体
         */
        Path dashPath = new Path(); // 使用一个三角形来做 dash
        pathEffect = new PathDashPathEffect(dashPath, 40, 0,
                PathDashPathEffect.Style.TRANSLATE);

        /**
         * SumPathEffect 组合效果类的 PathEffect 。使用两种 PathEffect 分别对目标进行绘制
         */
        PathEffect dashEffect = new DashPathEffect(new float[]{20, 10}, 0);
        PathEffect discreteEffect = new DiscretePathEffect(20, 5);
        pathEffect = new SumPathEffect(dashEffect, discreteEffect);

        /**
         * ComposePathEffect 组合效果类的 PathEffect 。不过它是先对目标 Path 使用一个 PathEffect，
         * 然后再对这个改变后的 Path 使用另一个 PathEffect
         */

        PathEffect dashEffect1 = new DashPathEffect(new float[]{20, 10}, 0);
        PathEffect discreteEffect1 = new DiscretePathEffect(20, 5);
        pathEffect = new ComposePathEffect(dashEffect1, discreteEffect1);

        paint.setPathEffect(pathEffect);

        //////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * 设置附加效果 在绘制层的下方附加效果
         *
         * radius 是阴影的模糊范围； dx dy 是阴影的偏移量； shadowColor 是阴影的颜色
         *
         * 在硬件加速开启的情况下， setShadowLayer() 只支持文字的绘制，文字之外的绘制必须关闭硬件加速才能正常绘制阴影。
         *
         * 如果 shadowColor 是半透明的，阴影的透明度就使用 shadowColor 自己的透明度；而如果 shadowColor 是不透明的，
         * 阴影的透明度就使用 paint 的透明度
         */
        paint.setShadowLayer(10, 0, 0, Color.RED);
        paint.clearShadowLayer();

        /////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * MaskFilter 绘制层上方的附加效果。
         */

        /**
         * 模BlurMaskFilter 糊效果
         * 构造方法 BlurMaskFilter(float radius, BlurMaskFilter.Blur style) 中， radius 参数是模糊的范围，
         *
         * style 是模糊的类型。一共有四种
         * NORMAL: 内外都模糊绘制
         * SOLID: 内部正常绘制，外部模糊
         * INNER: 内部模糊，外部不绘制
         * OUTER: 内部不绘制，外部模糊
         */
        paint.setMaskFilter(new BlurMaskFilter(50,BlurMaskFilter.Blur.NORMAL));

        /**
         * EmbossMaskFilter 浮雕效果
         * 构造方法 EmbossMaskFilter(float[] direction, float ambient, float specular, float blurRadius) 的参数里，
         * direction 是一个 3 个元素的数组，指定了光源的方向； ambient 是环境光的强度，数值范围是 0 到 1；
         * specular 是炫光的系数； blurRadius 是应用光线的范围
         */
        paint.setMaskFilter(new EmbossMaskFilter(new float[]{0, 1, 1}, 0.2f, 8, 10));


        //////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * 获取绘制的path
         * 认情况下（线条宽度为 0、没有 PathEffect），原 Path 和实际 Path 是一样的；
         * 而在线条宽度不为 0 （并且模式为 STROKE 模式或 FLL_AND_STROKE ）
         * 或者设置了 PathEffect 的时候，实际 Path 就和原 Path 不一样了
         *
         * 通过 getFillPath(src, dst) 方法就能获取这个实际 Path。方法的参数里，src 是原 Path ，
         * 而 dst 就是实际 Path 的保存位置。 getFillPath(src, dst) 会计算出实际 Path，然后把结果保存在 dst 里。
         */
        paint.getFillPath(new Path(),new Path());

        /**
         * getTextPath
         */

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setShader(mShader);
        canvas.drawCircle(300, 300, 200, paint);

        int saveLayer = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);//离屏缓冲，保存
        paint.setXfermode(xfermode);
        paint.setXfermode(null);//用完及时清除 Xfermode
        canvas.restoreToCount(saveLayer);//恢复

    }
}
