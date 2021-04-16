package com.example.androidview.TabLayout;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.androidview.R;
import com.example.androidview.TabLayout.Listener.CollapsedStateListener;
import com.example.androidview.TabLayout.Listener.LoadHeaderImagesListener;
import com.example.androidview.TabLayout.Listener.OnTabSelectedListener;
import com.example.androidview.utils.StatusBarUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;


/**
 * @创建者 lgh
 * @创建时间 2019-08-16 16:35
 * @描述 自定义 view
 */
public class CoordinatorTabLayout extends FrameLayout {

    private Context mContext;

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TabLayout mTabLayout;
    private ImageView mImageView;
    private TextView mTextView;

    private LoadHeaderImagesListener mLoadHeaderImagesListener;
    private OnTabSelectedListener mOnTabSelectedListener;

    private int[] mImageArray, mColorArray;

    public CoordinatorTabLayout(Context context) {
        this(context, null);

    }

    public CoordinatorTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoordinatorTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        if (!isInEditMode()) {
            initView(context);
            initWidget(context, attrs);
        }
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_coordinatortablayout, this, true);
        initToolbar();
        mTextView = findViewById(R.id.toolbar_title);
        mAppBarLayout = findViewById(R.id.app_bar_layout);
        mCollapsingToolbarLayout = findViewById(R.id.collapsingtoolbarlayout);
        mTabLayout = findViewById(R.id.tabLayout);
        mImageView = findViewById(R.id.imageview);
    }

    private void initWidget(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.CoordinatorTabLayout);
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);

        int contentScrimColor = typedArray.getColor(
                R.styleable.CoordinatorTabLayout_contentScrim, typedValue.data);
        mCollapsingToolbarLayout.setContentScrimColor(contentScrimColor);

        int tabIndicatorColor = typedArray.getColor(R.styleable.CoordinatorTabLayout_tabIndicatorColor, Color.WHITE);
        mTabLayout.setSelectedTabIndicatorColor(tabIndicatorColor);

        int tabTextColor = typedArray.getColor(R.styleable.CoordinatorTabLayout_tabTextColor, Color.WHITE);
        mTabLayout.setTabTextColors(ColorStateList.valueOf(tabTextColor));
        typedArray.recycle();
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        ((AppCompatActivity) mContext).setSupportActionBar(mToolbar);
        mActionBar = ((AppCompatActivity) mContext).getSupportActionBar();

    }

    /**
     * 设置Toolbar标题是否隐藏
     *
     * @param enabled 隐藏
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setTitleEnabled(boolean enabled) {
        if (mActionBar != null) {
            mActionBar.setDisplayShowTitleEnabled(enabled);
        }
        return this;
    }

    /**
     * 设置Toolbar左上角标题
     *
     * @param title 左上角标题
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setActionBarTitle(String title) {
        if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
        return this;
    }

    /**
     * 设置Toolbar居中标题
     *
     * @param title 居中标题
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setCenterTitle(String title) {
        if (mActionBar != null && mToolbar != null) {
            mCollapsingToolbarLayout.setTitleEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            mToolbar.setTitle("");
            mTextView.setText(title);
        }
        return this;
    }

    /**
     * 设置Toolbar显示返回按钮及标题
     *
     * @param canBack 是否返回
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setBackEnable(Boolean canBack) {
        if (canBack && mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        }
        return this;//返回对象本身
    }

    /**
     * 设置每个tab对应的头部图片
     *
     * @param imageArray 图片数组
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setImageColorArray(@NonNull int[] imageArray) {
        mImageArray = imageArray;
        return this;
    }

    /**
     * 设置每个tab对应的头部照片和ContentScrimColor
     *
     * @param imageArray 图片、颜色数组
     * @param colorArray ContentScrimColor数组
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setImageColorArray(@NonNull int[] imageArray, @NonNull int[] colorArray) {
        mImageArray = imageArray;
        mColorArray = colorArray;
        return this;
    }

    /**
     * 设置每个tab对应的ContentScrimColor
     *
     * @param colorArray 颜色数组
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setContentScrimColorArray(@NonNull int[] colorArray) {
        mColorArray = colorArray;
        return this;
    }

    /**
     * 设置TabLayout TabMode
     *
     * @param mode
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setTabMode(@TabLayout.Mode int mode) {
        mTabLayout.setTabMode(mode);
        return this;
    }


    /**
     * 设置与该组件搭配的ViewPager
     *
     * @param viewPager 与TabLayout结合的ViewPager
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setupWithViewPager(ViewPager viewPager) {
        setupTabLayout();
        mTabLayout.setupWithViewPager(viewPager);
        return this;
    }

    /**
     * 获取该组件中的ActionBar
     */
    public ActionBar getActionBar() {
        return mActionBar;
    }

    /**
     * 获取该组件中的TabLayout
     */
    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    /**
     * 获取该组件中的ImageView
     */
    public ImageView getImageView() {
        return mImageView;
    }

    /**
     * 设置LoadHeaderImagesListener
     *
     * @param loadHeaderImagesListener 设置LoadHeaderImagesListener
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setLoadHeaderImagesListener(LoadHeaderImagesListener loadHeaderImagesListener) {
        mLoadHeaderImagesListener = loadHeaderImagesListener;
        return this;
    }

    /**
     * 设置onTabSelectedListener
     *
     * @param onTabSelectedListener 设置onTabSelectedListener
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout addOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        mOnTabSelectedListener = onTabSelectedListener;
        return this;
    }

    private void setupTabLayout() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mImageView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_dismiss));
                if (mLoadHeaderImagesListener == null) {
                    if (mImageArray != null) {
                        mImageView.setBackgroundResource(mImageArray[tab.getPosition()]);
                    }
                } else {
                    mLoadHeaderImagesListener.loadHeaderImages(mImageView, tab);
                }
                if (mColorArray != null) {
                    mCollapsingToolbarLayout.setContentScrimColor(
                            ContextCompat.getColor(
                                    mContext, mColorArray[tab.getPosition()]));
                }
                mImageView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_show));

                if (mOnTabSelectedListener != null) {
                    mOnTabSelectedListener.onTabSelected(tab);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (mOnTabSelectedListener != null) {
                    mOnTabSelectedListener.onTabUnselected(tab);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (mOnTabSelectedListener != null) {
                    mOnTabSelectedListener.onTabReselected(tab);
                }
            }
        });

    }

    /**
     * 监听toolbar折叠效果
     */

    public CoordinatorTabLayout getCollapsedState() {
        mAppBarLayout.addOnOffsetChangedListener(new CollapsedStateListener() {

            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开


                } else if (state == State.COLLAPSED) {
                    //折叠


                } else {
                    //其他


                }
            }
        });
        return this;
    }

}
