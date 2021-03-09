package com.example.androidview.smarttablayout;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.androidview.R;
import com.example.androidview.TabLayout.Fragment.TabFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;

/**
 * @author lgh on 2021/3/1 17:45
 * @description
 */
public class SmartTabLayoutActivity extends AppCompatActivity implements SmartTabLayout.TabProvider {

    String[] tabs = new String[]{"全部", "情绪", "关系", "人格", "职场", "两性", "财运", "运势",};

    private ArrayList<Fragment> mFragments;
    private final String[] mTitles = {"Android", "iOS", "Web", "Other", "Android", "iOS", "Web", "Other"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
        final SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.smart_tablayout);
        FragmentPagerItems pages = new FragmentPagerItems(this);

        for (String titleResId : tabs) {
            pages.add(FragmentPagerItem.of(titleResId, DemoFragment.class));
        }

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);


        viewPagerTab.setCustomTabView(this);
        //        initFragments();
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
        viewPagerTab.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //                View tab = viewPagerTab.getTabAt(position);
                //                View mark = tab.findViewById(R.id.custom_tab_notification_mark);
                //                mark.setVisibility(View.GONE);
            }
        });
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        for (String title : mTitles) {
            mFragments.add(TabFragment.getInstance(title));
        }
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        Resources res = container.getContext().getResources();
        View tab = inflater.inflate(R.layout.custom_tab_icon_and_text, container, false);
        RelativeLayout root = tab.findViewById(R.id.tab_root);
        int widthPixels = ScreenUtil.getScreenWidth(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(widthPixels/6, ScreenUtil.dp2px(container.getContext(),48));
        root.setLayoutParams(params);
        //        android:background="?attr/selectableItemBackground"
        ImageView icon = (ImageView) tab.findViewById(R.id.custom_tab_icon);
        View view = tab.findViewById(R.id.tab_line);
        TextView title = tab.findViewById(R.id.custom_text);
        title.setText(tabs[position]);
        switch (position % 3) {
            case 0:
                icon.setImageDrawable(res.getDrawable(R.drawable.ic_home_white_24dp));
                if (position == 0)
                    view.setVisibility(View.VISIBLE);
                break;
            case 1:
                icon.setImageDrawable(res.getDrawable(R.drawable.ic_search_white_24dp));
                break;
            case 2:
                icon.setImageDrawable(res.getDrawable(R.drawable.ic_person_white_24dp));
                break;
            default:
                break;
        }
        return tab;
    }
}
