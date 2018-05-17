package me.jfenn.timedatepickers.utils;

public class NumberUtils {
    public static Integer[] fillArray(int start, int end) {
        Integer[] arr = new Integer[end - start + 1];
        for (int i = 0; i < arr.length; i++)
            arr[i] = i + start;

        return arr;
    }
}
