package com.neandroid.appwidget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.neandroid.appwidget.AllInOneActivity;
import com.neandroid.appwidget.R;

/**
 * Calendar桌面小部件
 */
public class CalendarRemoteViews extends RemoteViews {

    private Context mContext;

    private AppWidgetManager mAppWidgetManager;

    public CalendarRemoteViews(Context context, AppWidgetManager appWidgetManager) {
        super(context.getPackageName(), R.layout.layout_calendar_widget);
        this.mContext = context;
        this.mAppWidgetManager = appWidgetManager;
    }

    /**
     * 为ListView绑定数据可点击事件
     */
    public void bindListViewAdapter(int appWidgetId) {
        // 为ListView绑定数据
        Intent updateIntent = new Intent(mContext, CalendarAppWidgetService.class);
        updateIntent.setData(Uri.parse(updateIntent.toUri(Intent.URI_INTENT_SCHEME)));
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setRemoteAdapter(appWidgetId, R.id.events_listview, updateIntent);
        mAppWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.events_listview);

        // 设置ListView的点击事件
        PendingIntent updateEventIntent = getLaunchPendingIntentTemplate(mContext);
        setPendingIntentTemplate(R.id.events_listview, updateEventIntent);
    }

    /**
     * 主界面的意图
     *
     * @param context
     * @return
     */
    private PendingIntent getLaunchPendingIntentTemplate(Context context) {
        Intent launchIntent = new Intent();
        launchIntent.setAction(Intent.ACTION_VIEW);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        launchIntent.setClass(context, AllInOneActivity.class);
        return PendingIntent.getActivity(context, 0 /* no requestCode */, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 启动日历主界面
     *
     * @param millis 当前时间毫秒值
     */
    public void setLauncherCalendarClickPendingIntent(long millis) {
        Intent launchCalendarIntent = new Intent(Intent.ACTION_VIEW);
        launchCalendarIntent.setClass(mContext, AllInOneActivity.class);
        launchCalendarIntent.setData(Uri.parse("content://com.android.calendar/time/" + millis));
        PendingIntent launchCalendarPendingIntent = PendingIntent.getActivity(mContext, 0 /* no requestCode */, launchCalendarIntent, 0 /* no flags */);
        setOnClickPendingIntent(R.id.header, launchCalendarPendingIntent);

        setOnClickPendingIntent(R.id.time_date, launchCalendarPendingIntent);
    }

    /**
     * 更新ListView
     */
    public void notifyAppWidgetViewDataChanged(int appWidgetId) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(mContext);
        if (appWidgetId == -1) {
            int[] ids = widgetManager.getAppWidgetIds(getComponentName(mContext));
            widgetManager.notifyAppWidgetViewDataChanged(ids, R.id.events_listview);
        } else {
            widgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.events_listview);
        }
    }

    /**
     * 获取AppWidgetProvider组件
     *
     * @param context
     * @return
     */
    private ComponentName getComponentName(Context context) {
        return new ComponentName(context, CalendarAppWidgetProvider.class);
    }

    /**
     * 设置时间日期
     *
     * @param date      日期
     * @param dayOfWeek 周几
     */
    public void setDate(String date, String dayOfWeek) {
        setTextViewText(R.id.tv_day_of_week, dayOfWeek);
        setTextViewText(R.id.tv_date, date);
    }
}
