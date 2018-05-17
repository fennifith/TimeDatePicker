package me.jfenn.timedatepickers.dialogs;

import android.content.Context;

import me.jfenn.timedatepickers.views.LinearTimePickerView;

public class TimeSheetPickerDialog extends SheetPickerDialog<LinearTimePickerView> {

    public TimeSheetPickerDialog(Context context) {
        super(context, new LinearTimePickerView(context));
    }

    public TimeSheetPickerDialog(Context context, int hourOfDay, int minute) {
        super(context, new LinearTimePickerView(context));
        getView().setTime(hourOfDay, minute);
    }

}
