package me.jfenn.timedatepickers.dialogs;

import android.content.Context;
import android.view.View;

import me.jfenn.timedatepickers.R;

public class SheetPickerDialog<T extends View> extends PickerDialog<T> {

    public SheetPickerDialog(Context context, T view) {
        super(context, view, R.layout.timedatepickers_dialog_bottomsheet);
    }
}
