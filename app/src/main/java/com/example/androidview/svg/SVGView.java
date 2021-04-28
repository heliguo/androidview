package com.example.androidview.svg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;

import com.example.androidview.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author lgh on 2021/4/28 16:07
 * @description
 */
public class SVGView extends View {

    private final int[] colorArray = new int[]{0xFF239BD7, 0xFF30A9E5, 0xFF80CBF1, 0xFFFFFFFF};

    private List<ProvinceItem> mItemList;

    private ProvinceItem selectProvince;

    private RectF totalRect;

    private float scale = 1.0f;

    private Paint paint;

    public SVGView(Context context) {
        this(context, null);
    }

    public SVGView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SVGView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        localThread.start();
        mItemList = new ArrayList<>(34);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //        获取到当前控件宽高值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (totalRect != null) {
            double mapWidth = totalRect.width();
            scale = (float) (width / mapWidth);
        }

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

    }

    private Thread localThread = new Thread() {
        @Override
        public void run() {
            InputStream inputStream = getContext().getResources().openRawResource(R.raw.china);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc;
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                doc = builder.parse(inputStream);
                Element rootElement = doc.getDocumentElement();
                NodeList items = rootElement.getElementsByTagName("path");

                float left = -1;
                float right = -1;
                float top = -1;
                float bottom = -1;
                List<ProvinceItem> list = new ArrayList<>(34);

                for (int i = 0; i < items.getLength(); i++) {
                    Element element = (Element) items.item(i);
                    String pathData = element.getAttribute("android:pathData");
                    Path path = PathParser.createPathFromPathData(pathData);
                    ProvinceItem item = new ProvinceItem(path);
                    item.setDrawColor(colorArray[i % 4]);
                    RectF rect = new RectF();
                    path.computeBounds(rect, true);
                    left = left == -1 ? rect.left : Math.min(left, rect.left);
                    right = right == -1 ? rect.right : Math.max(right, rect.right);
                    top = top == -1 ? rect.top : Math.min(top, rect.top);
                    bottom = bottom == -1 ? rect.bottom : Math.max(bottom, rect.bottom);
                    list.add(item);
                }
                mItemList.clear();
                mItemList.addAll(list);
                Log.e("TAGTAGTAGTAGTAG", "run: " + mItemList.size());
                totalRect = new RectF(left, top, right, bottom);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    requestLayout();
                    invalidate();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mItemList != null && !mItemList.isEmpty()) {
            canvas.save();
            canvas.scale(scale, scale);
            for (ProvinceItem item : mItemList) {
                item.drawItem(canvas, paint, selectProvince == item);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handleTouch(event.getX() / scale, event.getY() / scale);
        return super.onTouchEvent(event);
    }

    private void handleTouch(float x, float y) {
        if (mItemList == null || mItemList.isEmpty()) {
            return;
        }
        ProvinceItem current = null;
        for (ProvinceItem item : mItemList) {
            if (item.isTouch(x, y)) {
                current = item;
            }
        }
        if (current != null) {
            selectProvince = current;
            postInvalidate();
        }
    }
}
