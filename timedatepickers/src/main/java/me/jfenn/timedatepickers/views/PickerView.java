package me.jfenn.timedatepickers.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Array;

import me.jfenn.timedatepickers.R;
import me.jfenn.timedatepickers.interfaces.Themable;
import me.jfenn.timedatepickers.utils.ConversionUtils;

public abstract class PickerView<T> extends View implements Themable {

    public static final int STYLE_CIRCLE = 0;
    public static final int STYLE_BOX = 1;

    Paint lineAccentPaint;
    Paint accentPaint;
    Paint textPrimaryPaint;
    Paint textSecondaryPaint;
    Paint textAccentPaint;
    Paint backgroundPrimaryPaint;
    Paint backgroundSecondaryPaint;

    int colorAccent;
    int colorLineAccent;
    int textColorSecondary;
    int textColorAccent;
    int itemStyle;

    T[][] items;
    String[] labels;

    final int[] dp = new int[]{
            ConversionUtils.dpToPx(2),
            ConversionUtils.dpToPx(4),
            ConversionUtils.dpToPx(6),
            ConversionUtils.dpToPx(8),
            ConversionUtils.dpToPx(12),
            ConversionUtils.dpToPx(16)
    };

    public PickerView(Context context) {
        this(context, null);
    }

    public PickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setAll(
                (T[][]) Array.newInstance(Array.newInstance(getClassType(), 0).getClass(), 0),
                new String[0],
                new int[0],
                false
        );

        colorAccent = ContextCompat.getColor(context, R.color.timedatepicker_colorAccent);
        colorLineAccent = Color.argb(50, Color.red(colorAccent), Color.green(colorAccent), Color.blue(colorAccent));
        textColorSecondary = ContextCompat.getColor(context, R.color.timedatepicker_textColorSecondary);

        lineAccentPaint = new Paint();
        lineAccentPaint.setStyle(Paint.Style.FILL);
        lineAccentPaint.setColor(colorLineAccent);
        lineAccentPaint.setAntiAlias(true);
        lineAccentPaint.setDither(true);

        accentPaint = new Paint();
        accentPaint.setStyle(Paint.Style.FILL);
        accentPaint.setColor(colorAccent);
        accentPaint.setAntiAlias(true);
        accentPaint.setDither(true);

        textPrimaryPaint = new Paint();
        textPrimaryPaint.setTextAlign(Paint.Align.LEFT);
        textPrimaryPaint.setTextSize(ConversionUtils.spToPx(16));
        textPrimaryPaint.setColor(ContextCompat.getColor(context, R.color.timedatepicker_textColorPrimary));
        textPrimaryPaint.setAntiAlias(true);
        textPrimaryPaint.setDither(true);

        textSecondaryPaint = new Paint();
        textSecondaryPaint.setTextAlign(Paint.Align.CENTER);
        textSecondaryPaint.setTextSize(ConversionUtils.spToPx(12));
        textSecondaryPaint.setColor(textColorSecondary);
        textSecondaryPaint.setAntiAlias(true);
        textSecondaryPaint.setDither(true);

        textAccentPaint = new Paint();
        textAccentPaint.setTextAlign(Paint.Align.CENTER);
        textAccentPaint.setTextSize(ConversionUtils.spToPx(12));
        textAccentPaint.setColor(ContextCompat.getColor(context, R.color.timedatepicker_colorAccent));
        textAccentPaint.setAntiAlias(true);
        textAccentPaint.setDither(true);

        textColorAccent = ContextCompat.getColor(context, R.color.timedatepicker_textColorAccent);

        backgroundPrimaryPaint = new Paint();
        backgroundPrimaryPaint.setStyle(Paint.Style.FILL);
        backgroundPrimaryPaint.setColor(ContextCompat.getColor(context, R.color.timedatepicker_colorBackgroundPrimary));

        backgroundSecondaryPaint = new Paint();
        backgroundSecondaryPaint.setStyle(Paint.Style.FILL);
        backgroundSecondaryPaint.setColor(ContextCompat.getColor(context, R.color.timedatepicker_colorBackgroundSecondary));
    }

    @Override
    public void setSelectionColor(int color) {
        setSelectionColor(color, 50);
    }

    public void setSelectionColor(@ColorInt int accentColor, int dimmedAlpha) {
        colorAccent = accentColor;
        accentPaint.setColor(colorAccent);
        colorLineAccent = Color.argb(dimmedAlpha, Color.red(accentColor), Color.green(accentColor), Color.blue(accentColor));
        lineAccentPaint.setColor(colorLineAccent);
        postInvalidate();
    }

    @Override
    public int getSelectionColor() {
        return colorAccent;
    }

    @Override
    public void setSelectionTextColor(int color) {
        textColorAccent = color;
        textAccentPaint.setColor(color);
        postInvalidate();
    }

    @Override
    public int getSelectionTextColor() {
        return textColorAccent;
    }

    public void setPrimaryTextColor(@ColorInt int textColor) {
        textPrimaryPaint.setColor(textColor);
        postInvalidate();
    }

    @Override
    public int getPrimaryTextColor() {
        return textPrimaryPaint.getColor();
    }

    @Override
    public void setSecondaryTextColor(@ColorInt int textColor) {
        textColorSecondary = textColor;
        textSecondaryPaint.setColor(textColor);
        postInvalidate();
    }

    @Override
    public int getSecondaryTextColor() {
        return textColorSecondary;
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    @Override
    public int getBackgroundColor() {
        return getBackground() instanceof ColorDrawable ? ((ColorDrawable) getBackground()).getColor() : Color.TRANSPARENT;
    }

    @Override
    public void setPrimaryBackgroundColor(int color) {
        backgroundPrimaryPaint.setColor(color);
        postInvalidate();
    }

    @Override
    public int getPrimaryBackgroundColor() {
        return backgroundPrimaryPaint.getColor();
    }

    @Override
    public void setSecondaryBackgroundColor(int color) {
        backgroundSecondaryPaint.setColor(color);
        postInvalidate();
    }

    @Override
    public int getSecondaryBackgroundColor() {
        return backgroundSecondaryPaint.getColor();
    }

    abstract Class getClassType();

    public void setItemStyle(int itemStyle) {
        this.itemStyle = itemStyle;
        postInvalidate();
    }

    /**
     * Set up all of the required attributes of the picker at once.
     *
     * @param items a two-dimensional array of all the items in the picker
     * @param labels an array of the labels to be applied to each row
     * @param selectedPositions an array of the selected positions in each row
     */
    public void setAll(T[][] items, String[] labels, int[] selectedPositions) {
        setAll(items, labels, selectedPositions, true);
    }

    /**
     * Set up all of the required attributes of the picker at once.
     *
     * @param items a two-dimensional array of all the items in the picker
     * @param labels an array of the labels to be applied to each row
     * @param selectedPositions an array of the selected positions in each row
     * @param animate whether to animate the switch between attributes
     */
    public void setAll(T[][] items, String[] labels, int[] selectedPositions, boolean animate) {
        this.items = items;
        this.labels = labels;
    }

    /**
     * Set all the attributes for a single row at once.
     *
     * @param row the index of the row
     * @param items the new items in the row
     * @param label the new label of the row
     * @param selectedPosition the new selected position of the row
     */
    public void setRow(int row, T[] items, String label, int selectedPosition) {
        setRow(row, items, label, selectedPosition, true);
    }

    /**
     * Set all the attributes for a single row at once.
     *
     * @param row the index of the row
     * @param items the new items in the row
     * @param label the new label of the row
     * @param selectedPosition the new selected position of the row
     * @param animate whether to animate the switch between rows
     */
    public void setRow(int row, T[] items, String label, int selectedPosition, boolean animate) {
        this.items[row] = items;
        labels[row] = label;
    }

    /**
     * Set the value of a single item.
     *
     * @param row the row of the item
     * @param col the column of the item
     * @param item the (new) item to be set
     */
    public void setItem(int row, int col, T item) {
        items[row][col] = item;
        postInvalidate();
    }

    /**
     * Get the current value of an item.
     *
     * @param row the row of the item
     * @param col the index of the item
     * @return the value of the item
     */
    public T getItem(int row, int col) {
        return items[row][col];
    }

    /**
     * Set the label of a row.
     *
     * @param row the index of the row
     * @param label the (new) label to be set
     */
    public void setLabel(int row, String label) {
        labels[row] = label;
        postInvalidate();
    }

    /**
     * Get the current label of a row.
     *
     * @param row the index of the row
     * @return the current label of the row
     */
    public String getLabel(int row) {
        return labels[row];
    }

    public void setSelectedIndex(int row, int column) {
        setSelectedIndex(row, column, true);
    }

    public abstract void setSelectedIndex(int row, int column, boolean animate);

    public abstract int getSelectedIndex(int row);

    @Override
    protected abstract void onDraw(Canvas canvas);

    @Override
    protected abstract void onMeasure(int widthMeasureSpec, int heightMeasureSpec);

}
