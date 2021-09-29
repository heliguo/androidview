package com.example.androidview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.util.Vector;

/**
 * @author lgh on 2021/9/28 14:21
 * @description 自动换行TextView
 */
public class AutoBreakTextView extends androidx.appcompat.widget.AppCompatTextView {

    public int mTextHeight; // 文本的高度
    public int mTextWidth;// 文本的宽度

    private String string;
    private final Vector<String> mM_string;
    private final float[] mWidths;

    public AutoBreakTextView(Context context, AttributeSet set) {
        super(context, set);
        mTextWidth = getResources().getDisplayMetrics().widthPixels;
        mM_string = new Vector<>();
        mWidths = new float[1];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredHeight = measureHeight();
        int measuredWidth = measureWidth(widthMeasureSpec);
        mTextWidth = measuredWidth - getPaddingLeft() - getPaddingRight();
        this.setMeasuredDimension(measuredWidth, measuredHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(string)) {
            return;
        }
        char ch;
        int w = 0;
        int istart = 0;
        int m_iFontHeight;
        int m_iRealLine = 0;
        int x = getPaddingLeft();
        int y = getPaddingTop();

        Paint.FontMetrics fm = getPaint().getFontMetrics();
        m_iFontHeight = (int) (fm.bottom - fm.top + fm.leading);// 计算字体高度（字体高度＋行间距）

        for (int i = 0; i < string.length(); i++) {
            ch = string.charAt(i);
            String srt = String.valueOf(ch);
            getPaint().getTextWidths(srt, mWidths);

            if (ch == '\n') {
                m_iRealLine++;
                mM_string.addElement(string.substring(istart, i));
                istart = i + 1;
                w = 0;
            } else {
                w += (int) (Math.ceil(mWidths[0]));
                if (w > mTextWidth) {
                    m_iRealLine++;
                    mM_string.addElement(string.substring(istart, i));
                    istart = i;
                    i--;
                    w = 0;
                } else {
                    if (i == (string.length() - 1)) {
                        m_iRealLine++;
                        mM_string.addElement(string.substring(istart));
                    }
                }
            }
        }
        for (int i = 0, j = 1; i < m_iRealLine; i++, j++) {
            canvas.drawText((String) (mM_string.elementAt(i)), x, y
                    + m_iFontHeight * j, getPaint());
        }
    }

    private int measureHeight() {
        initHeight();
        return mTextHeight;
    }

    private void initHeight() {
        if (TextUtils.isEmpty(string)) {
            return;
        }
        mTextHeight = 0;
        Paint.FontMetrics fm = getPaint().getFontMetrics();
        int m_iFontHeight = (int) (fm.bottom - fm.top + fm.leading) + 5;
        int line = 0;

        int w = 0;
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            float[] widths = new float[1];
            String srt = String.valueOf(ch);
            getPaint().getTextWidths(srt, widths);

            if (ch == '\n') {
                line++;
                w = 0;
            } else {
                w += (int) (Math.ceil(widths[0]));
                if (w > mTextWidth) {
                    line++;
                    i--;
                    w = 0;
                } else {

                    if (i == (string.length() - 1)) {
                        if (mTextWidth - w < widths[0] * 3) {
                            line++;
                        }
                        line++;
                    }
                }
            }
        }
        mTextHeight = (line) * m_iFontHeight;
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // Default size if no limits are specified.
        int result = getResources().getDisplayMetrics().widthPixels;
        if (specMode == MeasureSpec.AT_MOST) {
            // Calculate the ideal size of your control
            // within this maximum size.
            // If your control fills the available space
            // return the outer bound.
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            // If your control can fit within these bounds return that value.
            result = specSize;
        }
        return result;
    }

    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        mM_string.clear();
        string = text;
        initHeight();
        invalidate();
        requestLayout();
    }

    public String getText() {
        return string;
    }
}
