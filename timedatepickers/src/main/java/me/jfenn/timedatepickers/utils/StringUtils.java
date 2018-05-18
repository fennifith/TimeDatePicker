package me.jfenn.timedatepickers.utils;

import android.graphics.Paint;
import android.graphics.Rect;

public class StringUtils {

    public static int getStringWidth(String str, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    public static int getMaxStringWidth(Object[] strs, Paint paint) {
        int max = 0;
        for (Object str : strs) {
            int width = str instanceof Object[] ? getMaxStringWidth((Object[]) str, paint) : getStringWidth(str.toString(), paint);
            if (width > max)
                max = width;
        }

        return max;
    }
}
