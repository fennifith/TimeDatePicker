package me.jfenn.timedatepickers.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Array;

import me.jfenn.timedatepickers.R;
import me.jfenn.timedatepickers.utils.ConversionUtils;

public abstract class LinearPickerView<T extends Object> extends View implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private Paint lineAccentPaint;
    private Paint accentPaint;
    private Paint textPrimaryPaint;
    private Paint textSecondaryPaint;
    private Paint textAccentPaint;
    private Paint backgroundPrimaryPaint;
    private Paint backgroundSecondaryPaint;

    private int colorAccent;
    private int textColorSecondary;

    private T[][] items;
    private String[] labels;
    private int[] selectedPositions;
    private float[] actualPositions;
    private int itemWidth;

    private boolean scrollTriggered;

    private GestureDetector gestureDetector;

    private final int[] dp = new int[]{
            ConversionUtils.dpToPx(2),
            ConversionUtils.dpToPx(4),
            ConversionUtils.dpToPx(6),
            ConversionUtils.dpToPx(8),
            ConversionUtils.dpToPx(12),
            ConversionUtils.dpToPx(16)
    };

    public LinearPickerView(Context context) {
        this(context, null);
    }

    public LinearPickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setAll(
                (T[][]) Array.newInstance(Array.newInstance(getClassType(), 0).getClass(), 0),
                new String[0],
                new int[0],
                false
        );

        colorAccent = ContextCompat.getColor(context, R.color.timedatepicker_colorAccent);
        textColorSecondary = ContextCompat.getColor(context, R.color.timedatepicker_textColorSecondary);

        lineAccentPaint = new Paint();
        lineAccentPaint.setStyle(Paint.Style.FILL);
        lineAccentPaint.setColor(colorAccent);
        lineAccentPaint.setAlpha(50);

        accentPaint = new Paint();
        accentPaint.setStyle(Paint.Style.FILL);
        accentPaint.setColor(Color.BLUE);
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

        backgroundPrimaryPaint = new Paint();
        backgroundPrimaryPaint.setStyle(Paint.Style.FILL);
        backgroundPrimaryPaint.setColor(ContextCompat.getColor(context, R.color.timedatepicker_colorBackgroundPrimary));

        backgroundSecondaryPaint = new Paint();
        backgroundSecondaryPaint.setStyle(Paint.Style.FILL);
        backgroundSecondaryPaint.setColor(ContextCompat.getColor(context, R.color.timedatepicker_colorBackgroundSecondary));

        gestureDetector = new GestureDetector(context, this);
        setFocusable(true);
        setClickable(true);
        setOnTouchListener(this);
    }

    abstract Class getClassType();

    abstract void onPositionsChanged(int[] positions);

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
        this.selectedPositions = selectedPositions;
        actualPositions = new float[selectedPositions.length];

        if (!animate) {
            for (int i = 0; i < items.length; i++)
                actualPositions[i] = selectedPositions[i];
        }

        postInvalidate();
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
        selectedPositions[row] = selectedPosition;

        if (!animate)
            actualPositions[row] = selectedPosition;

        postInvalidate();
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

    public void setSelectedIndex(int row, int column, boolean animate) {
        selectedPositions[row] = column;

        if (!animate)
            actualPositions[row] = column;

        postInvalidate();
    }

    public int getSelectedIndex(int row) {
        return selectedPositions[row];
    }

    private int getStringWidth(String str, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    private int getMaxStringWidth(Object[] strs, Paint paint) {
        int max = 0;
        for (Object str : strs) {
            int width = str instanceof Object[] ? getMaxStringWidth((Object[]) str, paint) : getStringWidth(str.toString(), paint);
            if (width > max)
                max = width;
        }

        return max;
    }

    private boolean containsInt(int[] ints, int i) {
        for (int iter : ints) {
            if (iter == i)
                return true;
        }

        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        itemWidth = Math.max((canvas.getHeight() / 2) - dp[4], getMaxStringWidth(items, textPrimaryPaint));
        int itemHeight = canvas.getHeight() / items.length;
        boolean shouldInvalidate = false;
        for (int row = 0; row < items.length; row++) {
            boolean bool = drawItems(
                    canvas,
                    row,
                    labels[row],
                    row % 2 == 0 ? backgroundPrimaryPaint : backgroundSecondaryPaint,
                    itemWidth,
                    row * itemHeight,
                    (row + 1) * itemHeight
            );

            if (bool)
                shouldInvalidate = true;
        }

        if (shouldInvalidate)
            postInvalidate();
    }

    private boolean drawItems(Canvas canvas, int row, String label, Paint backgroundPaint, int itemWidth, int startY, int endY) {
        int textOffset = (int) -(textPrimaryPaint.descent() + textPrimaryPaint.ascent()) / 2;
        int labelWidth = getStringWidth(label, textPrimaryPaint);

        canvas.drawRect(0, startY, canvas.getWidth(), endY, backgroundPaint);
        canvas.drawRect((canvas.getWidth() / 2) - (itemWidth / 2) - dp[1], startY, (canvas.getWidth() / 2) + (itemWidth / 2) + dp[1], endY, lineAccentPaint);

        for (int col = 0; col < items[row].length; col++) {
            Float xPos = getSafeFloat((canvas.getWidth() / 2) + ((col - actualPositions[row]) * (itemWidth + dp[1])), (itemWidth + dp[1]) * items[row].length, canvas.getWidth());
            if (xPos != null) {
                if (selectedPositions[row] == col) {
                    textSecondaryPaint.setColor(colorAccent);
                } else {
                    textSecondaryPaint.setColor(textColorSecondary);
                    textSecondaryPaint.setAlpha((int) (Math.abs(xPos > canvas.getWidth() / 2 ? (canvas.getWidth() - xPos) / (canvas.getWidth() / 2) : (xPos - (xPos < labelWidth + dp[3] ? (xPos > labelWidth ? xPos * (xPos - labelWidth) / dp[3] : xPos) : 0)) / (canvas.getWidth() / 2)) * textSecondaryPaint.getAlpha()));
                }

                canvas.drawText(items[row][col].toString(), xPos, ((startY + endY) / 2) + textOffset, textSecondaryPaint);
            }
        }

        canvas.drawText(label, dp[5], ((startY + endY) / 2) + textOffset, textPrimaryPaint);

        if (!scrollTriggered) {
            float nextPosition = (((actualPositions[row] * 7) + selectedPositions[row]) / 8);
            if ((int) Math.abs((nextPosition - selectedPositions[row]) * (itemWidth + dp[2])) > 0) {
                actualPositions[row] = nextPosition;
                return true;
            } else if (actualPositions[row] != selectedPositions[row]) {
                actualPositions[row] = selectedPositions[row];
                return true;
            }
        }

        return false;
    }

    @Nullable
    private Float getSafeFloat(float n, int interval, int width) {
        if (n < 0) {
            n %= interval;
            n += interval;
            return n <= width ? n : null;
        } else if (n > width) {
            n %= interval;
            return n <= width ? n : null;
        } else return n;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY
                ? MeasureSpec.getSize(heightMeasureSpec) : items.length * ConversionUtils.dpToPx(56));
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        int row = (int) (e.getY() / (getHeight() / items.length));
        Float nextPosition = getSafeFloat(actualPositions[row] + ((e.getX() - (getWidth() / 2)) / itemWidth), items[row].length, items[row].length);
        if (nextPosition != null) {
            selectedPositions[row] = Math.round(nextPosition);
            actualPositions[row] = selectedPositions[row] - ((e.getX() - (getWidth() / 2)) / itemWidth);
            scrollTriggered = false;
            postInvalidate();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int row = (int) (e1.getY() / (getHeight() / items.length));
        Float nextPosition = getSafeFloat(actualPositions[row] + (distanceX / itemWidth), items[row].length, items[row].length);
        if (nextPosition != null) {
            actualPositions[row] = nextPosition;
            selectedPositions[row] = Math.round(nextPosition);
            scrollTriggered = true;
            postInvalidate();
        }

        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            scrollTriggered = false;
            onPositionsChanged(selectedPositions);
        }

        return false;
    }
}
