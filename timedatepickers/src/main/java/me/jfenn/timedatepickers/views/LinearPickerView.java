package me.jfenn.timedatepickers.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import me.jfenn.timedatepickers.utils.ConversionUtils;
import me.jfenn.timedatepickers.utils.NumberUtils;
import me.jfenn.timedatepickers.utils.StringUtils;

public abstract class LinearPickerView<T> extends PickerView<T> implements GestureDetector.OnGestureListener, View.OnTouchListener {

    public static final int STYLE_CIRCLE = 0;
    public static final int STYLE_BOX = 1;

    private T[][] items;
    private String[] labels;
    private int[] selectedPositions;
    private float[] actualPositions;
    private float[][] actualSelectedPositions;
    private int itemWidth;
    private boolean shouldInvalidate = true;

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

        gestureDetector = new GestureDetector(context, this);
        setFocusable(true);
        setClickable(true);
        setOnTouchListener(this);
    }

    @Override
    public void setAll(T[][] items, String[] labels, int[] selectedPositions, boolean animate) {
        this.items = items;
        this.labels = labels;
        this.selectedPositions = selectedPositions;
        actualPositions = new float[selectedPositions.length];
        actualSelectedPositions = new float[items.length][];
        for (int row = 0; row < items.length; row++)
            actualSelectedPositions[row] = new float[items[row].length];

        if (!animate) {
            for (int i = 0; i < items.length; i++)
                actualPositions[i] = selectedPositions[i];
        }

        postInvalidate();
    }

    @Override
    public void setRow(int row, T[] items, String label, int selectedPosition, boolean animate) {
        this.items[row] = items;
        labels[row] = label;
        selectedPositions[row] = selectedPosition;
        float[] actualSelectedPositions = new float[items.length];
        if (animate) {
            for (int col = 0; col < actualSelectedPositions.length; col++)
                actualSelectedPositions[col] = this.actualSelectedPositions[row][col % this.actualSelectedPositions[row].length];
        } else {
            actualPositions[row] = selectedPosition;
            actualSelectedPositions[selectedPosition] = 1;
        }

        this.actualSelectedPositions[row] = actualSelectedPositions;

        postInvalidate();
    }

    @Override
    public void setSelectedIndex(int row, int column, boolean animate) {
        selectedPositions[row] = column;

        if (!animate)
            actualPositions[row] = column;

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        itemWidth = Math.max((canvas.getHeight() / 2) - dp[4], StringUtils.getMaxStringWidth(items, textPrimaryPaint));
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

        if (this.shouldInvalidate || shouldInvalidate)
            postInvalidate();

        this.shouldInvalidate = shouldInvalidate;
    }

    private boolean drawItems(Canvas canvas, int row, String label, Paint backgroundPaint, int itemWidth, int startY, int endY) {
        int textOffset = (int) -(textPrimaryPaint.descent() + textPrimaryPaint.ascent()) / 2;
        int labelWidth = StringUtils.getStringWidth(label, textPrimaryPaint);

        canvas.drawRect(0, startY, canvas.getWidth(), endY, itemStyle == STYLE_BOX ? backgroundPaint : backgroundPrimaryPaint);

        for (int col = 0; col < items[row].length; col++) {
            Float xPos = NumberUtils.getSafeFloat((canvas.getWidth() / 2) + ((col - actualPositions[row]) * (itemWidth + dp[1])), (itemWidth + dp[1]) * items[row].length, canvas.getWidth());
            if (xPos != null) {
                lineAccentPaint.setAlpha((int) (actualSelectedPositions[row][col] * Color.alpha(itemStyle == STYLE_CIRCLE ? colorAccent : colorLineAccent)));
                if (itemStyle == STYLE_CIRCLE)
                    canvas.drawCircle(xPos, (startY + endY) / 2, (((endY - startY) / 2) - dp[3]) * actualSelectedPositions[row][col], lineAccentPaint);
                else if (itemStyle == STYLE_BOX)
                    canvas.drawRect(xPos - (itemWidth / 2) - dp[1], startY, xPos + (itemWidth / 2) + dp[1], endY, lineAccentPaint);

                textSecondaryPaint.setColor(selectedPositions[row] == col ? (itemStyle == STYLE_CIRCLE ? textColorAccent : colorAccent) : textColorSecondary);
                textSecondaryPaint.setAlpha((int) (Math.abs(xPos > canvas.getWidth() / 2 ? (canvas.getWidth() - xPos) / (canvas.getWidth() / 2) : (xPos - (xPos < labelWidth + dp[5] ? (xPos > labelWidth ? xPos * (xPos - labelWidth) / dp[5] : xPos) : 0)) / (canvas.getWidth() / 2)) * textSecondaryPaint.getAlpha()));
                canvas.drawText(items[row][col].toString(), xPos, ((startY + endY) / 2) + textOffset, textSecondaryPaint);
            }

            actualSelectedPositions[row][col] = ((actualSelectedPositions[row][col] * 4) + (selectedPositions[row] == col ? 1 : 0)) / 5;
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

    @Override
    public int getSelectedIndex(int row) {
        return selectedPositions[row];
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
        Float nextPosition = actualPositions[row] + ((e.getX() - (getWidth() / 2)) / (itemWidth + dp[2]));
        nextPosition = (itemWidth + dp[2]) * items[row].length > getWidth()
                ? NumberUtils.getSafeFloat(actualPositions[row] + ((e.getX() - (getWidth() / 2)) / (itemWidth + dp[2])), items[row].length, items[row].length)
                : (nextPosition >= 0 && nextPosition <= items[row].length - 1 ? nextPosition : null);
        if (nextPosition != null && selectedPositions[row] != Math.round(nextPosition)) {
            selectedPositions[row] = Math.round(nextPosition);
            actualPositions[row] = selectedPositions[row] - ((e.getX() - (getWidth() / 2)) / (itemWidth + dp[2]));
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
        Float nextPosition = actualPositions[row] + (distanceX / (itemWidth + dp[2]));
        nextPosition = (itemWidth + dp[2]) * items[row].length > getWidth()
                ? NumberUtils.getSafeFloat(actualPositions[row] + (distanceX / (itemWidth + dp[2])), items[row].length, items[row].length)
                : (nextPosition >= 0 && nextPosition <= items[row].length - 1 ? nextPosition : null);

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
        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            scrollTriggered = false;
            onPositionsChanged(selectedPositions);
            postInvalidate();
        }

        return false;
    }
}
