package com.neandroid.appwidget.widget;

import android.content.Intent;
import android.provider.CalendarContract;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.widget.RemoteViewsService;

import com.neandroid.appwidget.utils.Utils;


/**
 * 管理RemoteViews的服务
 */
public class CalendarAppWidgetService extends RemoteViewsService {

    public static final int EVENT_MIN_COUNT = 20;
    public static final int EVENT_MAX_COUNT = 100;
    // Minimum delay between queries on the database for widget updates in ms
    public static final int WIDGET_UPDATE_THROTTLE = 500;

    public static final int MAX_DAYS = 7;

    public static final long SEARCH_DURATION = MAX_DAYS * DateUtils.DAY_IN_MILLIS;

    /**
     * 表索引
     */
    public static final int INDEX_ALL_DAY = 0;
    public static final int INDEX_BEGIN = 1;
    public static final int INDEX_END = 2;
    public static final int INDEX_TITLE = 3;
    public static final int INDEX_EVENT_LOCATION = 4;
    public static final int INDEX_EVENT_ID = 5;
    public static final int INDEX_START_DAY = 6;
    public static final int INDEX_END_DAY = 7;
    public static final int INDEX_COLOR = 8;
    public static final int INDEX_SELF_ATTENDEE_STATUS = 9;

    /**
     * 查询条件
     */
    public static final String EVENT_SORT_ORDER = CalendarContract.Instances.START_DAY + " ASC, "
            + CalendarContract.Instances.START_MINUTE + " ASC, " + CalendarContract.Instances.END_DAY + " ASC, "
            + CalendarContract.Instances.END_MINUTE + " ASC LIMIT " + EVENT_MAX_COUNT;

    public static final String EVENT_SELECTION = CalendarContract.Calendars.VISIBLE + "=1";
    public static final String EVENT_SELECTION_HIDE_DECLINED = CalendarContract.Calendars.VISIBLE + "=1 AND "
            + CalendarContract.Instances.SELF_ATTENDEE_STATUS + "!=" + CalendarContract.Attendees.ATTENDEE_STATUS_DECLINED;

    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Instances.ALL_DAY,
            CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.END,
            CalendarContract.Instances.TITLE,
            CalendarContract.Instances.EVENT_LOCATION,
            CalendarContract.Instances.EVENT_ID,
            CalendarContract.Instances.START_DAY,
            CalendarContract.Instances.END_DAY,
            CalendarContract.Instances.DISPLAY_COLOR, // If SDK < 16, set to Instances.CALENDAR_COLOR.
            CalendarContract.Instances.SELF_ATTENDEE_STATUS,
    };

    static {
        if (!Utils.isJellybeanOrLater()) {
            EVENT_PROJECTION[INDEX_COLOR] = CalendarContract.Instances.CALENDAR_COLOR;
        }
    }

    /**
     * Update interval used when no next-update calculated, or bad trigger time in past.
     * Unit: milliseconds.
     */
    public static final long UPDATE_TIME_NO_EVENTS = DateUtils.HOUR_IN_MILLIS * 6;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CalendarFactory(this, intent);
    }


}
