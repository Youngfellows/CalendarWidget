package com.neandroid.appwidget.pojo;


/**
 * {@link RowInfo} is a class that represents a single row in the widget. It
 * is actually only a pointer to either a {@link DayInfo} or an
 * {@link EventInfo} instance, since a row in the widget might be either a
 * day header or an event.
 */
public class RowInfo {
    public static final int TYPE_DAY = 0;
    static final int TYPE_MEETING = 1;

    /**
     * mType is either a day header (TYPE_DAY) or an event (TYPE_MEETING)
     */
    public final int mType;

    /**
     * If mType is TYPE_DAY, then mData is the index into day infos.
     * Otherwise mType is TYPE_MEETING and mData is the index into event
     * infos.
     */
    public final int mIndex;

    RowInfo(int type, int index) {
        mType = type;
        mIndex = index;
    }
}