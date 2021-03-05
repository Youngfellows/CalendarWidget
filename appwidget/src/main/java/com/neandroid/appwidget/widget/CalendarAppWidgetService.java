package com.neandroid.appwidget.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;


/**
 * 管理RemoteViews的服务
 */
public class CalendarAppWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CalendarFactory(this, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
