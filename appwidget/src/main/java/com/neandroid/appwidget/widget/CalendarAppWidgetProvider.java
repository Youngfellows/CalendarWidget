package com.neandroid.appwidget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;

import com.neandroid.appwidget.AllInOneActivity;
import com.neandroid.appwidget.EventInfoActivity;
import com.neandroid.appwidget.utils.Utils;

import static android.provider.CalendarContract.EXTRA_EVENT_ALL_DAY;
import static android.provider.CalendarContract.EXTRA_EVENT_BEGIN_TIME;
import static android.provider.CalendarContract.EXTRA_EVENT_END_TIME;

/**
 * Calendar日历小部件
 */
public class CalendarAppWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "AppWidgetProvider";

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


    /**
     * Build the {@link PendingIntent} used to trigger an update of all calendar
     * widgets. Uses {@link Utils#getWidgetScheduledUpdateAction(Context)} to
     * directly target all widgets instead of using
     * {@link AppWidgetManager#EXTRA_APPWIDGET_IDS}.
     *
     * @param context Context to use when building broadcast.
     */
    public static PendingIntent getUpdateIntent(Context context) {
        Intent intent = new Intent(Utils.getWidgetScheduledUpdateAction(context));
        intent.setDataAndType(CalendarContract.CONTENT_URI, Utils.APPWIDGET_DATA_TYPE);
        return PendingIntent.getBroadcast(context, 0 /* no requestCode */, intent,
                0 /* no flags */);
    }


    /**
     * Build an {@link Intent} available as FillInIntent to launch the Calendar app.
     * This should be used in combination with
     * {@link RemoteViews#setOnClickFillInIntent(int, Intent)}.
     * If the go to time is 0, then calendar will be launched without a starting time.
     *
     * @param //goToTime time that calendar should take the user to, or 0 to
     *                   indicate no specific start time.
     */
    public static Intent getLaunchFillInIntent(Context context, long id, long start, long end, boolean allDay) {
        final Intent fillInIntent = new Intent();
        String dataString = "content://com.android.calendar/events";
        if (id != 0) {
            fillInIntent.putExtra(Utils.INTENT_KEY_DETAIL_VIEW, true);
            fillInIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);

            dataString += "/" + id;
            Log.d(TAG, "getLaunchFillInIntent:: ");
            // If we have an event id - start the event info activity
            fillInIntent.setClass(context, EventInfoActivity.class);
        } else {
            // If we do not have an event id - start AllInOne
            fillInIntent.setClass(context, AllInOneActivity.class);
        }
        Uri data = Uri.parse(dataString);
        fillInIntent.setData(data);
        fillInIntent.putExtra(EXTRA_EVENT_BEGIN_TIME, start);
        fillInIntent.putExtra(EXTRA_EVENT_END_TIME, end);
        fillInIntent.putExtra(EXTRA_EVENT_ALL_DAY, allDay);

        return fillInIntent;
    }
}
