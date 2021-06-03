package com.example.androidview.scratch;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * 可擦除遮罩
 */
public class ScratchView extends View {

    /**
     * 最小的橡皮擦尺寸大小
     */
    private final static float DEFAULT_ERASER_SIZE = 40f;
    /**
     * 默认蒙板的颜色
     */
    private final static int DEFAULT_MASKER_COLOR = 0xffcccccc;
    /**
     * 默认擦除比例
     */
    private final static int DEFAULT_PERCENT = 70;
    /**
     * 产生遮罩效果的 Bitmap
     */
    private Bitmap mMaskBitmap;
    /**
     * 绘制遮罩的 Canvas
     */
    private Canvas mMaskCanvas;
    /**
     * 普通绘制 Bitmap 用的 Paint
     */
    private Paint mBitmapPaint;
    /**
     * 橡皮檫画笔
     */
    private Paint mErasePaint;
    /**
     * 擦除轨迹
     */
    private Path mErasePath;
    /**
     * 擦除效果起始点的x坐标
     */
    private float mStartX;
    /**
     * 擦除效果起始点的y坐标
     */
    private float mStartY;
    /**
     * 最小滑动距离
     */
    private int mTouchSlop;
    /**
     * 完成擦除
     */
    private boolean mIsCompleted = false;
    /**
     * 最大擦除比例
     */
    private int mMaxPercent = DEFAULT_PERCENT;

    /**
     * 存放蒙层像素信息的数组
     */
    private int[] mPixels;

    private int resId;//水印图标

    private int maskColor = DEFAULT_MASKER_COLOR;

    private int defaultWith;

    private int defaultHeight;

    /**
     * 擦除回调
     */
    private OnEraseStatusListener mOnEraseStatusListener;

    public ScratchView(Context context) {
        this(context, null);
    }

    public ScratchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScratchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setFilterBitmap(true);
        mBitmapPaint.setDither(true);

        setWatermark(-1);

        mErasePaint = new Paint();
        mErasePaint.setAntiAlias(true);
        mErasePaint.setDither(true);
        mErasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//设置擦除效果
        mErasePaint.setStyle(Paint.Style.STROKE);
        mErasePaint.setStrokeJoin(Paint.Join.ROUND);//拐角圆角
        mErasePaint.setStrokeCap(Paint.Cap.ROUND);//设置笔尖形状，让绘制的边缘圆滑

        setEraserSize(DEFAULT_ERASER_SIZE);

        mErasePath = new Path();

        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = viewConfiguration.getScaledTouchSlop();//最小滑动距离

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = measureSize(0, widthMeasureSpec);
        int measuredHeight = measureSize(1, heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMaskBitmap != null)
            canvas.drawBitmap(mMaskBitmap, 0, 0, mBitmapPaint);//绘制图层遮罩
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsCompleted)
            return super.onTouchEvent(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startErase(event.getX(), event.getY());
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                erase(event.getX(), event.getY());
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                stopErase();
                invalidate();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createMasker(w, h);
    }

    /**
     * 创建蒙层
     */
    private void createMasker(int width, int height) {
        Rect rect = new Rect(0, 0, width, height);
        mMaskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mMaskCanvas = new Canvas(mMaskBitmap);
        Shader maskShader = new LinearGradient(0, 0, width, height, maskColor, maskColor, Shader.TileMode.CLAMP);//纯色遮罩
        if (resId != -1) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
            Shader waterShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);//水印
            Shader shader = new ComposeShader(maskShader, waterShader, PorterDuff.Mode.SRC_OVER);
            mBitmapPaint.setShader(shader);
        } else {
            mBitmapPaint.setShader(maskShader);
        }
        mMaskCanvas.drawRect(rect, mBitmapPaint);//绘制生成和控件大小一致的遮罩 Bitmap

        mPixels = new int[width * height];
    }

    /**
     * @param m           0 w 1 h
     * @param measureSpec mode
     * @return w or h
     */
    private int measureSize(int m, int measureSpec) {
        int size = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            size = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                if (m == 0) {
                    size = defaultWith;
                } else {
                    size = defaultHeight;
                }

            }
        }
        return size;
    }

    public void setSize(int width, int height) {
        this.defaultWith = width;
        this.defaultHeight = height;
        invalidate();
    }

    /**
     * 开始擦除
     */
    private void startErase(float x, float y) {
        mErasePath.reset();
        mErasePath.moveTo(x, y);
        this.mStartX = x;
        this.mStartY = y;
    }


    /**
     * 擦除
     */
    private void erase(float x, float y) {
        int dx = (int) Math.abs(x - mStartX);
        int dy = (int) Math.abs(y - mStartY);
        if (dx >= mTouchSlop || dy >= mTouchSlop) {
            this.mStartX = x;
            this.mStartY = y;

            mErasePath.lineTo(x, y);
            mMaskCanvas.drawPath(mErasePath, mErasePaint);

            mErasePath.reset();
            mErasePath.moveTo(mStartX, mStartY);
        }
    }


    /**
     * 停止擦除
     */
    private void stopErase() {
        this.mStartX = 0;
        this.mStartY = 0;
        mErasePath.reset();
        updateErasePercent();
    }

    private void updateErasePercent() {
        int width = getWidth();
        int height = getHeight();
        mMaskBitmap.getPixels(mPixels, 0, width, 0, 0, width, height);//获取覆盖图层中所有的像素信息，stride用于表示一行的像素个数有多少

        float erasePixelCount = 0;//擦除的像素个数
        float totalPixelCount = width * height;//总像素个数

        for (int pos = 0; pos < totalPixelCount; pos++) {
            if (mPixels[pos] == 0) {//透明的像素值为0
                erasePixelCount++;
            }
        }

        int percent;
        if (erasePixelCount >= 0 && totalPixelCount > 0) {
            percent = Math.round(erasePixelCount * 100 / totalPixelCount);
            if (percent > mMaxPercent) {
                mIsCompleted = true;
                if (mOnEraseStatusListener != null) {
                    mOnEraseStatusListener.onCompleted(this);
                }
            }
        }

    }


    /**
     * 设置擦除监听器
     */
    public void setOnEraseStatusListener(OnEraseStatusListener listener) {
        this.mOnEraseStatusListener = listener;
    }

    /**
     * 设置水印图标
     *
     * @param resId 图标资源id，-1表示去除水印
     */
    public void setWatermark(int resId) {
        this.resId = resId;
    }

    /**
     * 设置蒙板颜色
     *
     * @param color 十六进制颜色值，如：0xffff0000（不透明的红色）
     */
    public void setMaskColor(int color) {
        this.maskColor = color;
    }

    /**
     * 设置最大的擦除比例
     *
     * @param max 大于0，小于等于100
     */
    public void setMaxPercent(int max) {
        if (max > 100 || max <= 0) {
            return;
        }
        this.mMaxPercent = max;
    }

    /**
     * 设置橡皮檫尺寸大小（默认大小是 60）
     *
     * @param eraserSize 橡皮檫尺寸大小
     */
    public void setEraserSize(float eraserSize) {
        mErasePaint.setStrokeWidth(eraserSize);
    }

    /**
     * 重置为初始状态
     */
    public void reset() {
        mIsCompleted = false;
        int width = getWidth();
        int height = getHeight();
        createMasker(width, height);
        invalidate();
    }


    /**
     * 设置为true 不可擦除
     * @param completed true or false
     */
    public void setCompleted(boolean completed){
        this.mIsCompleted = completed;
    }

    /**
     * 清除整个图层
     */
    public void clear() {
        if (!mIsCompleted) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        mMaskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mMaskCanvas = new Canvas(mMaskBitmap);
        Rect rect = new Rect(0, 0, width, height);
        mMaskCanvas.drawRect(rect, mErasePaint);
        invalidate();
    }


    /**
     * 擦除状态监听器
     */
    public interface OnEraseStatusListener {
        void onCompleted(ScratchView scratchView);
    }
}
