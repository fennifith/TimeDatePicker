package me.jfenn.timedatepickers.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

import me.jfenn.timedatepickers.R;
import me.jfenn.timedatepickers.utils.NumberUtils;

public class LinearDatePickerView extends LinearPickerView<Object> {

    private int year, month;
    private int yearStart;
    private OnDateChangedListener listener;

    public LinearDatePickerView(Context context) {
        this(context, null);
    }

    public LinearDatePickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearDatePickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH);
        yearStart = year - 50;

        setAll(new Object[][]{
                NumberUtils.fillArray(yearStart, year + 50),
                new DateFormatSymbols().getMonths(),
                NumberUtils.fillArray(1, new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 0).getActualMaximum(Calendar.DAY_OF_MONTH))
        }, new String[]{
                context.getString(R.string.timedatepickers_year),
                context.getString(R.string.timedatepickers_month),
                context.getString(R.string.timedatepickers_day)
        }, new int[]{
                50,
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH) - 1
        });
    }

    public void setOnDateChangedListener(OnDateChangedListener listener) {
        this.listener = listener;
    }

    @Override
    Class getClassType() {
        return Object.class;
    }

    @Override
    void onPositionsChanged(int[] positions) {
        if (positions[0] != year - yearStart || positions[1] != month) {
            year = yearStart + positions[0];
            month = positions[1];
            int maxDays = new GregorianCalendar(year, month, 0).getActualMaximum(Calendar.DAY_OF_MONTH);
            setRow(2, NumberUtils.fillArray(1, maxDays), getContext().getString(R.string.timedatepickers_day), getSelectedIndex(2) % maxDays);
        }

        if (listener != null)
            listener.onDateChanged(getYear(), getMonth(), getDay());
    }

    public int getYear() {
        return yearStart + getSelectedIndex(0);
    }

    public int getMonth() {
        return getSelectedIndex(1);
    }

    public int getDay() {
        return getSelectedIndex(0) + 1;
    }

    public void setDate(int year, int month, int day) {
        setSelectedIndex(0, year - yearStart);
        setSelectedIndex(1, month);
        setSelectedIndex(2, day - 1);
    }

    public interface OnDateChangedListener {
        void onDateChanged(int year, int month, int day);
    }
}
