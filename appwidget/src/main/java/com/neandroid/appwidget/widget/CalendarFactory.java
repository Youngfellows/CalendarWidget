package com.neandroid.appwidget.widget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


/**
 * 管理RemoteViews工程,刷新列表等
 * 类似Adapter
 */
public class CalendarFactory extends BroadcastReceiver implements RemoteViewsService.RemoteViewsFactory {

    private String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private int mAppWidgetId;

    public CalendarFactory(Context mContext, Intent intent) {
        this.mContext = mContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate:: ");
    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged:: ");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy:: ");
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(TAG, "getViewAt:: ");
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
