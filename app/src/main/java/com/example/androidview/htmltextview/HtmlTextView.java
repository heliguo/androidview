package com.example.androidview.htmltextview;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import org.xml.sax.XMLReader;

/**
 * 文字中带有HTML TextView
 */
public class HtmlTextView extends AppCompatTextView {
    public static final String TAG = "HtmlTextView";
    public static final boolean DEBUG = false;

    public HtmlTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlTextView(Context context) {
        super(context);
    }

    public void setHtmlFromString(String html) {
        setHtmlFromString(html, false);
    }

    public void setHtmlFromString(String html, boolean useLocalDrawables) {
        Html.ImageGetter imgGetter;

        if (useLocalDrawables) {
            imgGetter = new LocalImageGetter();
        } else {
            imgGetter = new UrlImageGetter(this, getContext());
        }

        // this uses Android's Html class for basic parsing, and HtmlTagHandler
        setText(Html.fromHtml(html, imgGetter, new Html.TagHandler() {
            @Override
            public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

            }
        }));

    }
}