package me.jfenn.timedatepickers.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.AttributeSet;

import java.util.Calendar;

import me.jfenn.timedatepickers.R;
import me.jfenn.timedatepickers.utils.NumberUtils;

public class LinearTimePickerView extends LinearPickerView<Object> {

    private OnTimeChangedListener listener;

    public LinearTimePickerView(Context context) {
        this(context, null);
    }

    public LinearTimePickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearTimePickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Calendar now = Calendar.getInstance();
        setAll(DateFormat.is24HourFormat(context)
                ? new Integer[][]{NumberUtils.fillArray(1, 24), NumberUtils.fillArray(0, 59)}
                : new Object[][]{NumberUtils.fillArray(1, 12), NumberUtils.fillArray(0, 59), new String[]{"AM", "PM"}
        }, new String[]{
                context.getString(R.string.timedatepickers_hours),
                context.getString(R.string.timedatepickers_minutes),
                ""
        }, new int[]{
                (now.get(Calendar.HOUR_OF_DAY) - 1) % (DateFormat.is24HourFormat(context) ? 24 : 12),
                now.get(Calendar.MINUTE),
                now.get(Calendar.HOUR_OF_DAY) >= 12 ? 1 : 0
        });
    }

    public void setListener(OnTimeChangedListener listener) {
        this.listener = listener;
    }

    @Override
    Class getClassType() {
        return Object.class;
    }

    @Override
    void onPositionsChanged(int[] positions) {
        if (listener != null)
            listener.onTimeChanged(getHourOfDay(), getMinute());
    }

    public void setTime(int hourOfDay, int minute) {
        setSelectedIndex(1, minute);
        if (DateFormat.is24HourFormat(getContext()))
            setSelectedIndex(0, hourOfDay - 1);
        else {
            setSelectedIndex(0, (hourOfDay - 1) % 12);
            setSelectedIndex(2, hourOfDay >= 12 && hourOfDay < 24 ? 1 : 0);
        }

        postInvalidate();
    }

    public int getHourOfDay() {
        int hour = getSelectedIndex(0) + 1;
        if (DateFormat.is24HourFormat(getContext()))
            return hour;
        else return getSelectedIndex(2) == 0 ? hour % 12 : hour + 12;
    }

    public int getMinute() {
        return getSelectedIndex(1);
    }

    public interface OnTimeChangedListener {
        void onTimeChanged(int hourOfDay, int minute);
    }
}
