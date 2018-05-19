package me.jfenn.timedatepickers.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public abstract class CircularPickerView<T> extends PickerView<T> {

    public CircularPickerView(Context context) {
        super(context);
    }

    public CircularPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    void onPositionsChanged(int[] positions) {

    }

    @Override
    public void setAll(T[][] items, String[] labels, int[] selectedPositions, boolean animate) {

    }

    @Override
    public void setRow(int row, T[] items, String label, int selectedPosition, boolean animate) {

    }

    @Override
    public void setSelectedIndex(int row, int column, boolean animate) {

    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    }
}
