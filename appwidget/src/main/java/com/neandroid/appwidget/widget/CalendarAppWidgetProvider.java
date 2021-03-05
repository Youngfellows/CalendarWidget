package com.neandroid.appwidget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;

import com.neandroid.appwidget.utils.Utils;

/**
 * Calendar日历小部件
 */
public class CalendarAppWidgetProvider extends AppWidgetProvider {

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        Log.d(TAG, "onReceive:: action:" + action);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "onUpdate:: ");

        // 加载Widget小部件
        performUpdate(context, appWidgetManager, appWidgetIds);
    }

    /**
     * 加载Widget小部件
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    private void performUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Log.d(TAG, "Building widget update...");

            // 桌面小部件
            CalendarRemoteViews views = new CalendarRemoteViews(context, appWidgetManager);

            // 设置日期时间
            Time time = new Time(Utils.getTimeZone(context, null));
            time.setToNow();
            long millis = time.toMillis(true);
            String dayOfWeek = DateUtils.getDayOfWeekString(time.weekDay + 1, DateUtils.LENGTH_MEDIUM);
            String date = Utils.formatDateRange(context, millis, millis, DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_YEAR);
            views.setDate(date, dayOfWeek);

            // 启动日历主界面
            views.setLauncherCalendarClickPendingIntent(millis);

            // 为ListView绑定数据,设置item点击事件
            views.bindListViewAdapter(appWidgetId);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.d(TAG, "onAppWidgetOptionsChanged:: ");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(TAG, "onDeleted:: ");
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(TAG, "onEnabled:: ");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(TAG, "onDisabled:: ");
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Log.d(TAG, "onRestored:: ");
    }
}
