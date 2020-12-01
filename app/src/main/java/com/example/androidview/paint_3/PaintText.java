package com.example.androidview.paint_3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Locale;

/**
 * @author lgh on 2020/11/30 17:21
 * @description 自定义 View drawText() 文字的绘制
 */
public class PaintText extends View {

    private Paint paint;
    private Path mPath;
    private TextPaint mTextPaint;

    public PaintText(Context context) {
        super(context);
    }

    public PaintText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PaintText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        paint = new Paint();
        mPath = new Path();
        mTextPaint = new TextPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("444", 0, 0, paint);

        /**
         * 在路径上绘制文字
         */
        canvas.drawTextOnPath("new Path(),new Paint()", mPath, 0, 0, paint);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         * StaticLayout 的构造方法是 StaticLayout(CharSequence source, TextPaint paint, int width,
         * Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad)，其中参数里：
         *
         * width 是文字区域的宽度，文字到达这个宽度后就会自动换行；
         * align 是文字的对齐方向；
         * spacingmult 是行间距的倍数，通常情况下填 1 就好；
         * spacingadd 是行间距的额外增加值，通常情况下填 0 就好；
         * includepad 是指是否在文字上下添加额外的空间，来避免某些过高的字符的绘制出现越界。
         */

        String text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
        @SuppressLint("DrawAllocation") StaticLayout staticLayout1 = new StaticLayout(text1, mTextPaint, 600,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        String text2 = "a\nbc\ndefghi\njklm\nnopqrst\nuvwx\nyz";
        @SuppressLint("DrawAllocation") StaticLayout staticLayout2 = new StaticLayout(text2, mTextPaint, 600,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);

        canvas.save();
        canvas.translate(50, 100);
        staticLayout1.draw(canvas);
        canvas.translate(0, 200);
        staticLayout2.draw(canvas);
        canvas.restore();

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         * 文字显示效果类
         */

        /**
         * 设置字体
         */
        String text = "shitehiosxoshi";
        paint.setTypeface(Typeface.DEFAULT);
        canvas.drawText(text, 100, 150, paint);
        paint.setTypeface(Typeface.SERIF);
        canvas.drawText(text, 100, 300, paint);
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Satisfy-Regular.ttf"));
        canvas.drawText(text, 100, 450, paint);

        paint.setFakeBoldText(true);//伪粗体

        paint.setStrikeThruText(true);//删除线

        paint.setUnderlineText(true);//下划线

        paint.setTextSkewX(-0.5f);//倾斜度

        paint.setTextScaleX(1.2f);//文字横向缩放，胖瘦

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            paint.setLetterSpacing(0.2f);//字间距
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            paint.setFontFeatureSettings("smcp");//使用 CSS 的 font-feature-settings 的方式来设置文字
        }

        paint.setTextAlign(Paint.Align.CENTER);//设置文字的对齐方式。一共有三个值：LEFT CETNER 和 RIGHT。默认值为 LEFT

        //设置语言区域
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            paint.setTextLocale(Locale.CHINA); // 简体中文
            canvas.drawText(text, 150, 150, paint);
            paint.setTextLocale(Locale.TAIWAN); // 繁体中文
            canvas.drawText(text, 150, 150 , paint);
            paint.setTextLocale(Locale.JAPAN); // 日语
            canvas.drawText(text, 150, 150 , paint);
        }

        paint.setHinting(Paint.HINTING_OFF);//字体微调

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            paint.setElegantTextHeight(true);//调整文字的高度
        }

        paint.setSubpixelText(true);//像素级别的抗锯齿

        paint.setLinearText(true);//

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         * 测量文字尺寸类
         */

        /**
         * getFontSpacing()
         * 获取推荐的行距。
         */
        String[] texts = {"566","44","89"};
        canvas.drawText(texts[0], 100, 150, paint);
        canvas.drawText(texts[1], 100, 150 + paint.getFontSpacing(), paint);
        canvas.drawText(texts[2], 100, 150 + paint.getFontSpacing() * 2, paint);

        /**
         * FontMetircs getFontMetrics()
         * FontMetrics 提供的就是 Paint 根据当前字体和字号，
         * 得出的这些值的推荐值。它把这些值以变量的形式存储，供开发者需要时使用。
         *
         * FontMetrics.ascent：float 类型。
         * FontMetrics.descent：float 类型。
         * FontMetrics.top：float 类型。
         * FontMetrics.bottom：float 类型。
         * FontMetrics.leading：float 类型。
         */

        /**
         * getTextBounds(String text, int start, int end, Rect bounds)
         * 获取文字的显示范围。
         *
         * 参数里，text 是要测量的文字，start 和 end 分别是文字的起始和结束位置，bounds 是存储文字显示范围的对象，
         * 方法在测算完成之后会把结果写进 bounds。
         */
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, 0, 0, paint);

        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        bounds.left += 0;
        bounds.top += 0;
        bounds.right += 0;
        bounds.bottom += 0;
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(bounds, paint);

        /**
         *  float measureText(String text)
         * 测量文字的宽度并返回
         */
        canvas.drawText(text, 0, 0, paint);
        float textWidth = paint.measureText(text);
        canvas.drawLine(0, 0, 0 + textWidth, 0, paint);

        /**
         * getTextWidths(String text, float[] widths)
         * 获取字符串中每个字符的宽度，并把结果填入参数 widths
         */

        /**
         *  int breakText(String text, boolean measureForwards, float maxWidth, float[] measuredWidth)
         * 这个方法也是用来测量文字宽度的。但和 measureText() 的区别是， breakText() 是在给出宽度上限的前提下测量文字的宽度。
         * 如果文字的宽度超出了上限，那么在临近超限的位置截断文字。
         */
        int measuredCount;
        float[] measuredWidth = {0};

        // 宽度上限 300 （不够用，截断）
        measuredCount = paint.breakText(text, 0, text.length(), true, 300, measuredWidth);
        canvas.drawText(text, 0, measuredCount, 150, 150, paint);

        // 宽度上限 400 （不够用，截断）
        measuredCount = paint.breakText(text, 0, text.length(), true, 400, measuredWidth);
        canvas.drawText(text, 0, measuredCount, 150, 150 , paint);

        // 宽度上限 500 （够用）
        measuredCount = paint.breakText(text, 0, text.length(), true, 500, measuredWidth);
        canvas.drawText(text, 0, measuredCount, 150, 150 , paint);

        // 宽度上限 600 （够用）
        measuredCount = paint.breakText(text, 0, text.length(), true, 600, measuredWidth);
        canvas.drawText(text, 0, measuredCount, 150, 150, paint);

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         * 光标相关
         */

        /**
         * etRunAdvance(CharSequence text, int start, int end, int contextStart, int contextEnd, boolean isRtl, int offset)
         * 对于一段文字，计算出某个字符处光标的 x 坐标。 start end 是文字的起始和结束坐标；
         * contextStart contextEnd 是上下文的起始和结束坐标；isRtl 是文字的方向；offset 是字数的偏移，即计算第几个字符处的光标。
         */
        int length = text.length();
        float advance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            advance = paint.getRunAdvance(text, 0, length, 0, length, false, length);
        }
        canvas.drawText(text, 100, 100, paint);
        canvas.drawLine(100 + advance,  50, 100 + advance, 100 + 10, paint);

        /**
         * getOffsetForAdvance(CharSequence text, int start, int end, int contextStart, int contextEnd, boolean isRtl, float advance)
         * 给出一个位置的像素值，计算出文字中最接近这个位置的字符偏移量（即第几个字符最接近这个坐标）
         */

        /**
         * hasGlyph(String string)
         * 检查指定的字符串中是否是一个单独的字形 (glyph
         */

    }
}
