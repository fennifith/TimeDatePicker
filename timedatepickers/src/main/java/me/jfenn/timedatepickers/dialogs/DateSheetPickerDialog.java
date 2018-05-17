package me.jfenn.timedatepickers.dialogs;

import android.content.Context;

import me.jfenn.timedatepickers.views.LinearDatePickerView;

public class DateSheetPickerDialog extends SheetPickerDialog<LinearDatePickerView> {

    public DateSheetPickerDialog(Context context) {
        super(context, new LinearDatePickerView(context));
    }

    public DateSheetPickerDialog(Context context, int year, int month, int day) {
        super(context, new LinearDatePickerView(context));
        getView().setDate(year, month, day);
    }
    
}
