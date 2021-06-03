package com.example.androidview.calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.example.androidview.databinding.ActivityCalendarBinding;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2021/1/6 14:51
 * @description
 */
public class CalendarActivity extends AppCompatActivity implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        View.OnClickListener {

    ActivityCalendarBinding mCalendarBinding;

    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;

    private int mYear;

    CalendarLayout mCalendarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCalendarBinding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(mCalendarBinding.getRoot());
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        mCalendarBinding.wheel.setCyclic(false);
        mCalendarBinding.wheel.setCurrentItem(2);
        mCalendarBinding.wheel.setDividerType(WheelView.DividerType.CIRCLE);
//        mCalendarBinding.wheel.setDividerWidth(0);
        mCalendarBinding.wheel.setDividerColor(0x00000000);
        mCalendarBinding.wheel.setAdapter(new WheelAdapter<String>() {

            @Override
            public int getItemsCount() {
                return list.size();
            }

            @Override
            public String getItem(int index) {
                return list.get(index);
            }

            @Override
            public int indexOf(String o) {
                return list.indexOf(o);
            }

        });
        mCalendarBinding.wheel.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(CalendarActivity.this, "" + list.get(index), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {

    }

    @Override
    public void onYearChange(int year) {

    }
}
