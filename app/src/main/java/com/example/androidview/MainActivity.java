package com.example.androidview;

import android.Manifest;
import android.animation.ValueAnimator;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.androidview.TabLayout.TabLayoutActivity;
import com.example.androidview.backpress.BackPressActivity;
import com.example.androidview.backpress.BackPressObserver;
import com.example.androidview.dragandslideslip.SnapHelperActivity;
import com.example.androidview.pageview.CurlActivity;
import com.example.androidview.animation.FrameAnimationActivity;
import com.example.androidview.animation.Rotate3dActivity;
import com.example.androidview.calendar.CalendarActivity;
import com.example.androidview.calendar.CalendarReminderUtils;
import com.example.androidview.databinding.ActivityMainBinding;
import com.example.androidview.dialog.DialogFragmentActivity;
import com.example.androidview.expandrecyclerview.impl.ExpandableRecyclerviewActivity;
import com.example.androidview.guideview.GuideViewHelper;
import com.example.androidview.mpandroidchart.LineChartActivity;
import com.example.androidview.ntp.SntpUtils;
import com.example.androidview.realm.RealmActivity;
import com.example.androidview.rootview.CreateWidget;
import com.example.androidview.rootview.RootViewActivity;
import com.example.androidview.scratch.ScratchActivity;
import com.example.androidview.screenadapter.ScreenAutoActivity;
import com.example.androidview.slideBar.SlideBarActivity;
import com.example.androidview.smarttablayout.SmartTabLayoutActivity;
import com.example.androidview.span.SpanActivity;
import com.example.androidview.surfaceview.SurfaceViewActivity;
import com.example.androidview.view.DispatchActivity;
import com.example.androidview.view.FloatViewActivity;
import com.example.androidview.view.HorizontalScrollActivity;
import com.example.androidview.windows.WindowsActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author lgh
 */
public class MainActivity extends BaseActivity {

    private TextView mTextView;
    private int mMeasuredWidth;
    private int mMeasuredHeight;
    ActivityMainBinding mBinding;
    BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBackPress(this, new BackPressObserver() {
            @Override
            public boolean onBackPress() {
                return false;
            }
        });
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BackPressActivity.class));
            }
        });

        mBinding.snapHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SnapHelperActivity.class));
            }
        });

        mBinding.surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SurfaceViewActivity.class));
            }
        });

        mBinding.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CurlActivity.class));
            }
        });

        mBinding.CustomRoundAngleImageView.getContext();

        mBinding.tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SmartTabLayoutActivity.class));
            }
        });

        mBinding.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExpandableRecyclerviewActivity.class));
            }
        });

        mBinding.slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SlideBarActivity.class));
            }
        });

        mBinding.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LockScreenNotification lockScreenNotification = new LockScreenNotification(MainActivity.this);
                lockScreenNotification.send("title", "", 1);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, 100);
        }


        //        finishAffinity();

        mBinding.scratch.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ScratchActivity.class)));
//        mBinding.realm.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RealmActivity.class)));
        mBinding.chart.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LineChartActivity.class)));

        mBinding.screen.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ScreenAutoActivity.class)));

        mBinding.span.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SpanActivity.class)));

        mBinding.timer.setInitTime(5 * 60 * 1000).count();

        mBinding.rxTimer.setInitTime(5 * 60 * 1000).setTimeInterval(1000).setCountType(true).count();

        mBinding.floatView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FloatViewActivity.class)));

        mBinding.calendar.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CalendarActivity.class)));

        mBinding.calendarAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED ||
                            checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                CalendarReminderUtils.addCalendarEvent(MainActivity.this, "测试", "测试日程事件",
                        System.currentTimeMillis() + 3600 * 24 * 1000 * 2 + 10000, 2);
            }
        });

        LottieAnimationView mLottie = new LottieAnimationView(this);
        mLottie.setAnimation("diamond_new_user_guide.json");
        mLottie.setImageAssetsFolder("images_diamond_new_user_guide");
        mLottie.setBackgroundColor(Color.TRANSPARENT);
        mLottie.setRepeatCount(ValueAnimator.INFINITE);
        mLottie.playAnimation();
        GuideViewHelper guideViewHelper = new GuideViewHelper(1, 1.5f);
        guideViewHelper.show(mBinding.download, R.drawable.guide_hand);
        guideViewHelper.setOnViewLayoutListener(() -> {
            guideViewHelper.getReplaceLayoutParams().topMargin = 40;
            guideViewHelper.getTargetView().setOnClickListener(v -> {
                if (guideViewHelper.getSourceView() != null && guideViewHelper.getSourceView().getVisibility() == View.VISIBLE) {
                    guideViewHelper.remove();
                }
            });
        });
        mBinding.download.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, "long click", Toast.LENGTH_SHORT).show();
            return false;
        });


        //        new GuideViewHelper().show(mBinding.download, mLottie);

        recyclerviewAnimation();
        mBinding.pro.setLineWidth(20);
        EditText editText = findViewById(R.id.et);
        TextInputLayout layout = findViewById(R.id.input_layout);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                String v = s.toString();

                if (TextUtils.isEmpty(v)) {
                    layout.setHint("自定义提现（请输入20元以下金额）");
                    return;
                }
                if (Integer.parseInt(v) == 0) {
                    editText.setText("");
                    layout.setHint("自定义提现（请输入20元以下金额）");
                    return;
                }
                int limitNum = 200;
                if (Integer.parseInt(v) > limitNum) {
                    String limit = String.valueOf(limitNum);
                    if (v.length() > limit.length()) {
                        v = v.substring(0, limit.length());
                        editText.setText(v);
                        editText.setSelection(limit.length());
                    } else {
                        v = v.substring(0, limit.length() - 1);
                        editText.setText(v);
                        editText.setSelection(limit.length() - 1);
                    }
                }
                int input = Integer.parseInt(v) * 10000;
                if (input > 8888) {
                    layout.setHint(String.format("您需要%s金币，金币不足", input));
                } else
                    layout.setHint(String.format("您需要%s金币", input));
            }
        });
        mTextView = findViewById(R.id.test_draw_view);
        new Thread(() -> {
            Log.e("LOG", "requesting time..");

            Date dt = SntpUtils.getUTCDate();
            Log.e("LOG", "dt: " + dt.toString());

            long ts = SntpUtils.getUTCTimestamp();
            SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            String format1 = format.format(ts);
            String format2 = format.format(System.currentTimeMillis());
            Log.e("LOG", "时间: " + format1);
            Log.e("LOG", "时间2: " + format2);
            Log.e("LOG", "ts: " + ts);
        }).start();

        Looper.myQueue().addIdleHandler(() -> {
            Log.e("addIdleHandler", " mMeasuredWidth = " + mMeasuredWidth + "  mMeasuredHeight = " + mMeasuredHeight);
            Log.e(TAG, "ScaledTouchSlop: " + ViewConfiguration.get(this).getScaledTouchSlop());
            return false;
        });

    }

    /**
     * recyclerview item动画
     */
    private void recyclerviewAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(2000);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        mBinding.mainRv.setLayoutAnimation(controller);
    }

    /**
     * 获取view宽高的四种方式，第四种方式忽略
     * 1.onWindowFocusChanged
     * 当activity失去或获得焦点该方法均会被调用
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {

            //            ViewParent parent = mBinding.download.getParent();
            //            if (parent instanceof ViewGroup) {
            //                ViewGroup viewGroup = (ViewGroup) parent;
            //                int index = viewGroup.indexOfChild(mBinding.download);
            //                ViewGroup.LayoutParams layoutParams = mBinding.download.getLayoutParams();
            //                viewGroup.removeView(mBinding.download);
            //                FrameLayout frameLayout = new FrameLayout(this);
            //                frameLayout.addView(mBinding.download);
            //                ImageView imageView = new ImageView(this);
            //                imageView.setBackgroundResource(R.drawable.guide_hand);
            //                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //                params.gravity = Gravity.CENTER;
            //                params.topMargin = 20;
            //                frameLayout.addView(imageView, params);
            //                viewGroup.addView(frameLayout, index, layoutParams);
            //            }


            mMeasuredWidth = mTextView.getMeasuredWidth();
            mMeasuredHeight = mTextView.getMeasuredHeight();
            Log.e("onWindowFocusChanged", " mMeasuredWidth = " + mMeasuredWidth + "  mMeasuredHeight = " + mMeasuredHeight);
        }
        /**
         * 非粘性沉浸
         * 使用Flag SYSTEM_UI_FLAG_IMMERSIVE，
         * 隐藏系统栏。当用户在系统栏区域向内滑动时，系统栏会重新显示并保持可见。
         * 粘性沉浸
         * 当使用Flag SYSTEM_UI_FLAG_IMMERSIVE_STICKY，
         * 在系统栏的区域中向内滑动会引起系统栏出现一个半透明的状态，
         * 但是 flags 不会被清除，监听系统界面可见性变化的listeners并不会被调用。
         * 系统栏会在几秒之后再次动态隐藏，同样当用户点击了屏幕，系统栏也会隐藏。
         */
        //判断4.4以上版本
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //获得DecorView
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE //来帮助你的app来维持一个稳定的布局
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //确保appUI的主要部分不会因为被系统导航栏覆盖而结束
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //确保appUI的主要部分不会因为被系统状态栏覆盖而结束
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航栏
                            | View.SYSTEM_UI_FLAG_FULLSCREEN //表示全屏，会将状态栏隐藏，只会隐藏状态栏
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);//粘性沉浸

        }
    }

    /**
     * onContentChange是在setContentView之后的回调
     */
    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    /**
     * onPostCreate方法发生在onRestoreInstanceState之后，onResume之前，他代表着界面数据已经完全恢复，就差显示出来与用户交互了。
     * 在onStart方法被调用时这些操作尚未完成。
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    /**
     * onPostResume是在Resume方法被完全执行之后的回调。
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "onPause: ");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG", "onStart: ");
        /**
         * 获取view宽高的四种方式，第四种方式忽略
         * 2.view.post()
         * 通过post可以将一个runnable投递到消息队列的尾部，
         * 然后等待Looper调用此runnable的时候，View也已经初始化好了
         */
        mTextView.post(new Runnable() {
            @Override
            public void run() {
                mMeasuredWidth = mTextView.getMeasuredWidth();
                mMeasuredHeight = mTextView.getMeasuredHeight();
                Log.e("post", " mMeasuredWidth = " + mMeasuredWidth + "  mMeasuredHeight = " + mMeasuredHeight);
            }
        });

        /**
         * 获取view宽高的四种方式，第四种方式忽略
         * 3.ViewTreeObserver
         */
        mTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mMeasuredWidth = mTextView.getMeasuredWidth();
                mMeasuredHeight = mTextView.getMeasuredHeight();
                Log.e("getViewTreeObserver", " mMeasuredWidth = " + mMeasuredWidth + "  mMeasuredHeight = " + mMeasuredHeight);

                //onGLobalLayout会被多次调用，再获取到之后要移除观察者
                if (mMeasuredHeight != 0 || mMeasuredWidth != 0) {
                    mTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    public void floatingWindows(View view) {
        startActivity(new Intent(this, WindowsActivity.class));
    }

    public void viewFlipper(View view) {
        startActivity(new Intent(this, ViewFlipperActivity.class));
    }

    public void horizontalScroll(View view) {
        startActivity(new Intent(this, HorizontalScrollActivity.class));
    }

    public void dialogFragment(View view) {
        startActivity(new Intent(this, DialogFragmentActivity.class));
    }

    public void rootView(View view) {
        startActivity(new Intent(this, RootViewActivity.class));

    }

    public void frameAnimation(View view) {
        startActivity(new Intent(this, FrameAnimationActivity.class));
    }

    public void rotate(View view) {
        startActivity(new Intent(this, Rotate3dActivity.class));
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("++++++++", "onDestroy: ");
    }

    @Override
    public void finish() {
        super.finish();
        Log.e("==========", "finish: ");
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    public void motion(View view) {

        Intent intent = new Intent(this, CreateWidget.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 222);
        startActivityForResult(intent, 3333);
        //        startActivity(new Intent(this, MotionLayoutActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3333) {
            Log.e("=========", "onActivityResult: " + resultCode);
        }
    }

    private static final String TAG = "MainActivity";

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: ");
        //        Thread.dumpStack();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: ");
        return super.onTouchEvent(event);
    }

    public void dispatch(View view) {
        startActivity(new Intent(this, DispatchActivity.class));
    }

    public void test(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void addRecommendationsWidget() {
        int APPWIDGET_HOST_ID = 1024;
        AppWidgetHost appWidgetHost = new AppWidgetHost(getApplicationContext(), APPWIDGET_HOST_ID);
        int appWidgetId = appWidgetHost.allocateAppWidgetId();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        List<AppWidgetProviderInfo> providers = appWidgetManager.getInstalledProviders();
        if (providers == null) {
            Log.e(TAG, "failed to find installed widgets ");
            return;
        }
        final int providerCount = providers.size();
        AppWidgetProviderInfo appWidgetProviderInfo = null;
        for (int i = 0; i < providerCount; i++) {
            ComponentName provider = providers.get(i).provider;
            if (provider != null && provider.getPackageName().equals(getPackageName())) {
                appWidgetProviderInfo = providers.get(i);
                break;
            }
        }
        if (appWidgetProviderInfo == null) {
            Log.e(TAG, "failed to find recommendations widget ");
            return;
        }
        int sdkVersion = Integer.parseInt(android.os.Build.VERSION.SDK);
        if (sdkVersion > 15) {
            final String methodName = "bindAppWidgetIdIfAllowed";
            boolean success = bindAppWidgetId(appWidgetManager, appWidgetId, appWidgetProviderInfo.provider, methodName);
            if (!success) {
                addWidgetPermission(appWidgetManager);
                boolean bindAllowed = bindAppWidgetId(appWidgetManager, appWidgetId, appWidgetProviderInfo.provider, methodName);
                if (!bindAllowed) {
                    Log.e(TAG, " failed to bind widget id : " + appWidgetId);
                    return;
                }
            }
        } else {
            boolean success = bindAppWidgetId(appWidgetManager, appWidgetId, appWidgetProviderInfo.provider, "bindAppWidgetId");
            if (!success) {
                Log.e(TAG, " failed to bind widget id : " + appWidgetId);
                return;
            }
        }
        Log.d(TAG, " successful to bind widget id : " + appWidgetId);
        AppWidgetHostView hostView = appWidgetHost.createView(this, appWidgetId, appWidgetProviderInfo);
        appWidgetHost.startListening();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, appWidgetProviderInfo.minHeight);
        LinearLayout widgetLayout = findViewById(R.id.appwidget_rv);
        widgetLayout.addView(hostView, params);
    }

    private void addWidgetPermission(AppWidgetManager appWidgetManager) {
        String methodName = "setBindAppWidgetPermission";
        try {
            Class<?>[] argsClass = new Class[]{String.class, boolean.class};
            Method method = appWidgetManager.getClass().getMethod(methodName, argsClass);
            Object[] args = new Object[]{this.getPackageName(), true};
            try {
                method.invoke(appWidgetManager, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static boolean bindAppWidgetId(AppWidgetManager appWidgetManager, int appWidgetId, ComponentName componentName, String methodName) {
        boolean success = false;
        Class<?>[] argsClass = new Class[]{int.class, ComponentName.class};
        try {
            Method method = appWidgetManager.getClass().getMethod(methodName, argsClass);
            Object[] args = new Object[]{appWidgetId, componentName};
            try {
                method.invoke(appWidgetManager, args);
                success = true;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 设置红点
     *
     * @param
     */
    private void setRedPoint(View iconView) {
        ViewParent parent = iconView.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = ((ViewGroup) parent);
            group.setClipToPadding(false);
            group.setClipChildren(false);
            //            int px = ViewUtil.dp2px(this, 6);
            int px = 6;
            group.setClipChildren(false);
            group.setClipToPadding(false);
            int index = group.indexOfChild(iconView);
            ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            group.removeView(iconView);
            FrameLayout frameLayout = new FrameLayout(this);
            frameLayout.setClipChildren(false);
            frameLayout.setClipToPadding(false);
            frameLayout.setId(iconView.getId());
            group.addView(frameLayout, index, layoutParams);
            frameLayout.addView(iconView);
            ImageView imageView = new ImageView(this);
            imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.bitmap));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.TOP | Gravity.END;
            params.rightMargin = -(px / 2);
            params.topMargin = -(px / 2);
            frameLayout.addView(imageView, params);
        }
    }

    public void tab(View view) {
        startActivity(new Intent(this, TabLayoutActivity.class));
    }
}
