package com.example.androidview.mpandroidchart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.databinding.ActivityChartBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author lgh on 2021/1/19 14:52
 * @description
 */
public class LineChartActivity extends AppCompatActivity {

    ActivityChartBinding mBinding;

    private LineChart lineChart;
    private XAxis xAxis;
    private YAxis leftYAxis;//左侧Y轴
    private YAxis rightYAxis;//右侧Y轴
    private Legend mLegend;//图例
    private LimitLine mLimitLine;//限制线

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityChartBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Gson gson = new Gson();
        lineChart = mBinding.chart;
        initChart();

        String json = getJson(this, "test.json");

        LineChartBean lineChartBean = gson.fromJson(json, LineChartBean.class);
        List<LineChartBean.GRID0Bean.ResultBean.ClientAccumulativeRateBean> clientAccumulativeRate = lineChartBean.getGRID0().getResult().getClientAccumulativeRate();

        showLineChart(clientAccumulativeRate,"我的收益",Color.CYAN);

    }

    private void initChart() {
        lineChart.setLogEnabled(false);
        lineChart.setNoDataText("您还没有添加血糖值,快来记录第一条数据吧!");
        lineChart.setBorderColor(Color.BLACK);
        lineChart.setBorderWidth(1);
        lineChart.setVisibleXRangeMaximum(10);

        lineChart.setDrawGridBackground(false);//是否显示网格线
        lineChart.setDrawBorders(false);//是否显示边界
        lineChart.setDragEnabled(true);//是否可拖动
        lineChart.setTouchEnabled(true);
        //        lineChart.animateX(1500);
        //        lineChart.animateY(2500);
        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYAxis = lineChart.getAxisRight();
        //保证Y轴从零开始
        leftYAxis.setAxisMinimum(0);
        rightYAxis.setAxisMinimum(0);
        //折线图图例
        mLegend = lineChart.getLegend();

        mLegend.setForm(Legend.LegendForm.LINE);
        mLegend.setTextSize(12f);

        mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        mLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        mLegend.setDrawInside(false);//是否绘制在图表里

        mLegend.setEnabled(false);//设置不显示曲线名称（图例）Legend

        //修改背景去掉边界
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setDrawBorders(false);

        //网格线
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        leftYAxis.setDrawGridLines(false);
        rightYAxis.setDrawGridLines(false);

        //设置X Y轴网格线为虚线（实体线长度、间隔距离、偏移量：通常使用 0）
        leftYAxis.enableAxisLineDashedLine(10f,10f,0);
        //去掉右侧Y轴
        rightYAxis.setEnabled(false);

        //设置一页最大显示个数为6，超出部分就滑动
//        float ratio = (float) xValueList.size()/(float) 6;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        lineChart.zoom(2,1f,0,0);
        //定义X轴的值
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf(value);
            }
        });
        //设置X轴分割数量,true代表强制均分，可能会导致数据显示不均匀
        xAxis.setLabelCount(6,false);

        leftYAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf(value);
            }
        });
        leftYAxis.setLabelCount(8);

        Description description = new Description();
        //        description.setText("需要展示的内容");
        description.setEnabled(false);
        lineChart.setDescription(description);

    }

    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点类型是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线填充
//        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15f);
        if (mode != null) {
            //设置曲线展示为圆滑曲线（如果不设置默认折线）
//            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet.setMode(mode);
        }

        //不显示值
        lineDataSet.setDrawValues(false);
    }

    private void showLineChart(List<LineChartBean.GRID0Bean.ResultBean.ClientAccumulativeRateBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            LineChartBean.GRID0Bean.ResultBean.ClientAccumulativeRateBean incomeBean = dataList.get(i);
            Entry entry = new Entry(i, ((float) incomeBean.getValue()));
            entries.add(entry);
        }
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, null);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        //        float[] dataObjects = {1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1};
        //        List<Entry> entries = new ArrayList<>();
        //        //        for (int i = 0; i < dataObjects.length; i++) {
        //        //            float data = dataObjects[i];
        //        //            entries.add(new Entry(i, data));
        //        //        }
        //

        //        int count = 0;
        //        BloodBean bloodBean = gson.fromJson(getJson(this), BloodBean.class);
        //        if (bloodBean != null) {
        //            Log.e("********", "onCreate: ");
        //            List<BloodBean.DataBean.ListBean> list = bloodBean.getData().getList();
        //            for (int i = 0; i < list.size(); i++) {
        //                BloodBean.DataBean.ListBean listBean = list.get(i);
        //                for (int j = 0; j < listBean.getList().size(); j++) {
        //                    BloodBean.DataBean.ListBean.ListBean1 listBean1 = listBean.getList().get(j);
        //                    long measureTs = listBean1.getMeasureTs();
        //                    count++;
        //                    entries.add(new Entry(count, (float) listBean1.getValue()));
        //                }
        //            }
        //        }
        //
    }

    /**
     * 设置 可以显示X Y 轴自定义值的 MarkerView
     */
    public void setMarkerView() {
        LineChartMarkView mv = new LineChartMarkView(this, xAxis.getValueFormatter());
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);
        lineChart.invalidate();
    }

    private String getJson(Context context, String filePath) {

        try {
            InputStream inputStream = context.getAssets().open(filePath);
            return convertStreamToString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * input 流转换为字符串
     */
    private static String convertStreamToString(InputStream is) {
        String s = null;
        try {
            //格式转换
            Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
            if (scanner.hasNext()) {
                s = scanner.next();
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 设置线条填充背景颜色
     *
     * @param drawable
     */
    public void setChartFillDrawable(Drawable drawable) {
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            LineDataSet lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            //避免在 initLineDataSet()方法中 设置了 lineDataSet.setDrawFilled(false); 而无法实现效果
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillDrawable(drawable);
            lineChart.invalidate();
        }
    }
}
