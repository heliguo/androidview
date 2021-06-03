package com.example.androidview.span;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.util.Property;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.R;
import com.example.androidview.databinding.ActivitySpanBinding;

/**
 * @author lgh on 2020/12/15 9:49
 * @description SpannableString
 * 参照
 * https://blog.csdn.net/u012422440/article/details/52133037
 * 富文本 span 类型
 * https://juejin.cn/post/6844903950882177032
 */
public class SpanActivity extends AppCompatActivity {

    ActivitySpanBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySpanBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        /**
         * 注意padding
         */
        RestSpan restSpan = new RestSpan(this);
        SpannableString s = new SpannableString("自定义span");
        s.setSpan(restSpan, 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span.setText(s);

        String CONTENT = "BulletSpan影响段落层次文字的格式，它让你在段落开头添加一个黑圆点";
        BulletSpan span = new BulletSpan(15, Color.RED);
        SpannableString spannableString = new SpannableString(CONTENT);
        spannableString.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span2.setText(spannableString);

        String CONTENT1 = "QuoteSpan影响段落层次文字的格式，它可以在段落前边添加一个竖直的引用线";
        QuoteSpan span2 = new QuoteSpan(Color.RED);
        SpannableString spannableString2 = new SpannableString(CONTENT1);
        spannableString2.setSpan(span2, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span3.setText(spannableString2);

        String CONTENT2 = "AlignSpan.Standard影响段落层次文字的格式，它允许你控制段落的对齐方式，有居中对齐，右侧对齐和左侧对齐。";
        AlignmentSpan.Standard span3 = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
        SpannableString spannableString3 = new SpannableString(CONTENT2);
        spannableString3.setSpan(span3, 0, 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span4.setText(spannableString3);

        String CONTENT3 = "StrikethroughSpan影响字符层次上的文字的格式，它允许你在文字上添加删除线。它内部使用Paint.setStrikeThruText(true))来实现。";
        StrikethroughSpan span4 = new StrikethroughSpan();
        SpannableString spannableString4 = new SpannableString(CONTENT3);
        spannableString4.setSpan(span4, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span5.setText(spannableString4);

        String CONTENT4 = "SubscriptSpan影响字符层次上的文字的格式，它允许你把文字作为下标进行显示。";
        SubscriptSpan span5 = new SubscriptSpan();
        SpannableString spannableString5 = new SpannableString(CONTENT4);
        spannableString5.setSpan(span5, spannableString5.length() - 2, spannableString5.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span6.setText(spannableString5);

        String CONTENT5 = "SuperscriptSpan影响字符层次上的文字的格式，它允许你把文字作为上标进行显示。";
        SuperscriptSpan span6 = new SuperscriptSpan();
        SpannableString spannableString6 = new SpannableString(CONTENT5);
        spannableString6.setSpan(span6, spannableString6.length() - 2, spannableString6.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span7.setText(spannableString6);

        String CONTENT6 = "BackgroundColorSpan影响字符层次上的文字的格式，你可以使用它设置文字的背景颜色";
        BackgroundColorSpan span7 = new BackgroundColorSpan(Color.RED);
        SpannableString spannableString7 = new SpannableString(CONTENT6);
        spannableString7.setSpan(span7, 0, spannableString7.length() / 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span8.setText(spannableString7);

        String CONTENT7 = "ForegroundColorSpan影响字符层次上的文字的格式，你可以使用它设置文字的自己的颜色";
        ForegroundColorSpan span8 = new ForegroundColorSpan(Color.RED);
        SpannableString spannableString8 = new SpannableString(CONTENT7);
        spannableString8.setSpan(span8, 0, spannableString8.length() / 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span9.setText(spannableString8);

        String CONTENT8 = "ImageSpan影响字符层次上的文字的格式，你可以使用它在文字中间添加图片";
        ImageSpan span9 = new ImageSpan(this, R.drawable.rest);
        SpannableString spannableString9 = new SpannableString(CONTENT8);
        spannableString9.setSpan(span9, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span10.setText(spannableString9);

        String CONTENT9 = "StyleSpan影响字符层次上的文字的格式。它允许你设置文字的类型(bold, italic, normal)";
        StyleSpan span10 = new StyleSpan(Typeface.BOLD);
        SpannableString spannableString10 = new SpannableString(CONTENT9);
        spannableString10.setSpan(span10, 0, spannableString10.length() / 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span11.setText(spannableString10);

        String CONTENT10 = "TypefaceSpan影响字符层次上的文字的格式。它允许你设置文字的字体族(monospace, serif等)";
        TypefaceSpan span11 = new TypefaceSpan("serif");
        SpannableString spannableString11 = new SpannableString(CONTENT10);
        spannableString11.setSpan(span11, 0, spannableString11.length() / 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span12.setText(spannableString11);

        String CONTENT11 = "AbsoluteSizeSpan影响字符层次上的文字的格式。它允许你设置文字的绝对字体大小";
        AbsoluteSizeSpan span12 = new AbsoluteSizeSpan(24, true);
        SpannableString spannableString12 = new SpannableString(CONTENT11);
        spannableString12.setSpan(span12, 0, spannableString12.length() / 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span13.setText(spannableString12);

        String CONTENT12 = "RelativeSizeSpan影响字符层次上的文字的格式。它允许你设置文字的相对字体大小";
        RelativeSizeSpan span13 = new RelativeSizeSpan(2.0f);
        SpannableString spannableString13 = new SpannableString(CONTENT12);
        spannableString13.setSpan(span13, 0, spannableString13.length() / 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span14.setText(spannableString13);

        String CONTENT13 = "ScaleXSpan影响字符层次上的文字的格式。它让你让文字在ｘ方向上进行缩放";
        ScaleXSpan span14 = new ScaleXSpan(2.0f);
        SpannableString spannableString14 = new SpannableString(CONTENT13);
        spannableString14.setSpan(span14, 0, spannableString14.length() / 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span15.setText(spannableString14);

        String CONTENT14 = "MaskFilterSpan影响字符层次上的文字的格式。它让你在文字上设置android.graphics.MaskFilter";
        SpannableString spannableString15 = new SpannableString(CONTENT14);
        //Blur a character 高斯模糊
        MaskFilterSpan span15 = new MaskFilterSpan(new BlurMaskFilter(getResources().getDisplayMetrics().density * 2, BlurMaskFilter.Blur.NORMAL));
        spannableString15.setSpan(span15, 0, spannableString15.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span16.setText(spannableString15);

        //Emboss a character 浮雕类型
        SpannableString spannableString16 = new SpannableString(CONTENT14);
        MaskFilterSpan span16 = new MaskFilterSpan(new EmbossMaskFilter(new float[]{1, 1, 1}, 0.4f, 6, 3.5f));
        spannableString15.setSpan(span16, 0, spannableString15.length() / 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.span17.setText(spannableString16);

        //        MutableForegroundColorSpan span = new MutableForegroundColorSpan(255, Color.BLUE);
        //        final SpannableString spannableString = new SpannableString("使用ObjectAnimator对MutableForegroundColorSpan实现属性动画");
        //        spannableString.setSpan(span, 5, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(span, MUTABLE_FOREGROUND_COLOR_SPAN_PROPERTY, Color.BLACK, Color.RED);
        //        objectAnimator.setEvaluator(new ArgbEvaluator());
        //        objectAnimator.addUpdateListener(animation -> {
        //            //refresh
        //            mBinding.span.setText(spannableString);
        //        });
        //        objectAnimator.setDuration(600);
        //        objectAnimator.start();
    }

    private static final Property<MutableForegroundColorSpan, Integer> MUTABLE_FOREGROUND_COLOR_SPAN_PROPERTY =

            new Property<MutableForegroundColorSpan, Integer>(Integer.class, "MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY") {

                @Override
                public void set(MutableForegroundColorSpan alphaForegroundColorSpanGroup, Integer value) {
                    alphaForegroundColorSpanGroup.setForegroundColor(value);
                }

                @Override
                public Integer get(MutableForegroundColorSpan span) {
                    return span.getForegroundColor();
                }
            };

}
