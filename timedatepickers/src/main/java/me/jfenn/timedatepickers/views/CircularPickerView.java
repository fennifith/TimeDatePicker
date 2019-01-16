package me.jfenn.timedatepickers.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public abstract class CircularPickerView<T> extends PickerView<T> {

    private Coordinate[][] points;
    private int radius;

    public CircularPickerView(Context context) {
        this(context, null);
    }

    public CircularPickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAll(T[][] items, String[] labels, int[] selectedPositions, boolean animate) {
        super.setAll(items, labels, selectedPositions, animate);

        points = new Coordinate[items.length][];
        for (int r = 0; r < items.length; r++) {
            points[r] = new Coordinate[items[r].length];
            for (int c = 0; c < items[r].length; c++) {
                points[r][c] = new Coordinate((float) Math.PI * 2 * (c + 1) / items[r].length);
            }
        }
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
        int size = getMeasuredWidth();
        setMeasuredDimension(size, size);
        radius = (size / 2) - dp[3];
    }


    private static class Coordinate {

        private float radian;

        private Coordinate(float radian) {
            this.radian = radian;
        }

        private int getX(float radius) {
            return (int) (Math.cos(radian) * radius);
        }

        private int getY(float radius) {
            return (int) (Math.sin(radian) * radius);
        }

    }

}
