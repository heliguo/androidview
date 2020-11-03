package com.example.androidview.rootview;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2020/11/2 9:41
 * @description
 */

public class WalkAppWidgetProvider extends AppWidgetProvider {

    public static boolean a;

    public static int b;

    private static List<Integer> c = new ArrayList<Integer>();

    private static String d;

    static {
        a = false;
        b = 0;
    }

    public static void a(Context paramContext) {
        a = a.c();
        if (a) {
            Intent intent = new Intent(paramContext, WalkAppWidgetProvider.class);
            intent.setAction("com.qsmy.widget.UPDATE_ALL");
            paramContext.sendBroadcast(intent);
        } else {
            d = null;
            b.a(paramContext);
        }
        b.b(paramContext);
    }

    private static void b() {
        a.a(g.c(), 1);
        a.a(g.e(), System.currentTimeMillis());
        a.a(g.d(), 0);
    }

    private void b(Context paramContext) {
        b.c(paramContext);
        if (a)
            b();
    }

    public void onDeleted(Context paramContext, int[] paramArrayOfint) {
        super.onDeleted(paramContext, paramArrayOfint);
        if (paramArrayOfint != null && paramArrayOfint.length > 0)
            for (int i = 0; i < paramArrayOfint.length; i++) {
                if (c.contains(Integer.valueOf(paramArrayOfint[i])))
                    c.remove(Integer.valueOf(paramArrayOfint[i]));
            }
    }

    public void onDisabled(Context paramContext) {
        super.onDisabled(paramContext);
        a = false;
        d = null;
        b();
        a.a(a);
        b.a(paramContext);
    }

    public void onEnabled(Context paramContext) {
        super.onEnabled(paramContext);
        b(paramContext);
        b = 0;
        a = true;
        d.a("");
        a.a(a);
        a.b().postDelayed(new Runnable(this) {
            public void run() {
                String str2 = WalkAppWidgetProvider.a(paramContext);
                String str1 = str2;
                if (TextUtils.isEmpty(str2))
                    str1 = "3";
                a.a("4700004", "entry", "null", "null", str1, "show");
            }
        } ,1500L);
    }

    public void onReceive(Context paramContext, Intent paramIntent) {
        super.onReceive(paramContext, paramIntent);
        if (paramContext != null && paramIntent != null) {
            String str1;
            String str2 = paramIntent.getAction();
            if ("com.qsmy.widget.UPDATE_ALL".equals(str2)) {
                if (paramIntent.getBooleanExtra("key_next_data", false))
                    b++;
                b(paramContext);
                return;
            }
            if ("com.qsmy.widget.step.click".equals(str2)) {
                if (!a) {
                    a = true;
                    a.a(a);
                }
                if (!c.b()) {
                    b++;
                    str1 = "0";
                } else {
                    str1 = "1";
                }
                c.a(paramContext, 1);
                a.a("4700001", "page", "null", "null", str1, "click");
                b(paramContext);
                return;
            }
            if ("com.qsmy.widget.action.click".equals(str2)) {
                if (!a) {
                    a = true;
                    a.a(a);
                }
                a.b(paramContext, a.a());
                b++;
                b(paramContext);
                return;
            }
            if ("com.qsmy.widget.action.ADD".equals(str2)) {
                if (!a) {
                    a = true;
                    a.a(a);
                }
                d = str1.getStringExtra("key_add_widget_from");
            }
        }
    }

    public void onUpdate(Context paramContext, AppWidgetManager paramAppWidgetManager, int[] paramArrayOfint) {
        super.onUpdate(paramContext, paramAppWidgetManager, paramArrayOfint);
        int i = 0;
        if (paramArrayOfint != null && paramArrayOfint.length > 0) {
            a = true;
            while (i < paramArrayOfint.length) {
                int j = paramArrayOfint[i];
                if (!c.contains(Integer.valueOf(j)))
                    c.add(Integer.valueOf(j));
                i++;
            }
            b(paramContext);
        } else {
            a = false;
        }
        a.a(a);
    }
}

