package me.jfenn.timedatepickers.interfaces;

import android.support.annotation.ColorInt;

public interface Themable {
    void setSelectionColor(@ColorInt int color);

    @ColorInt
    int getSelectionColor();

    void setSelectionTextColor(@ColorInt int color);

    @ColorInt
    int getSelectionTextColor();

    void setPrimaryTextColor(@ColorInt int color);

    @ColorInt
    int getPrimaryTextColor();

    void setSecondaryTextColor(@ColorInt int color);

    @ColorInt
    int getSecondaryTextColor();

    void setBackgroundColor(@ColorInt int color);

    @ColorInt
    int getBackgroundColor();

    void setPrimaryBackgroundColor(@ColorInt int color);

    @ColorInt
    int getPrimaryBackgroundColor();

    void setSecondaryBackgroundColor(@ColorInt int color);

    @ColorInt
    int getSecondaryBackgroundColor();
}
