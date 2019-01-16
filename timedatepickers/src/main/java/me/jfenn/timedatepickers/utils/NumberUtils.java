package me.jfenn.timedatepickers.utils;

import androidx.annotation.Nullable;

public class NumberUtils {

    public static Integer[] fillArray(int start, int end) {
        Integer[] arr = new Integer[end - start + 1];
        for (int i = 0; i < arr.length; i++)
            arr[i] = i + start;

        return arr;
    }

    public static boolean containsInt(int[] ints, int i) {
        for (int iter : ints) {
            if (iter == i)
                return true;
        }

        return false;
    }

    @Nullable
    public static Float getSafeFloat(float n, int interval, int width) {
        if (n < 0) {
            n %= interval;
            n += interval;
            return n <= width ? n : null;
        } else if (n > width) {
            n %= interval;
            return n <= width ? n : null;
        } else return n;
    }
}
