package me.jfenn.timedatepickers.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import me.jfenn.timedatepickers.R;

public abstract class PickerDialog<T extends View> extends AppCompatDialog {

    private T view;
    private int layoutRes;
    private OnSelectedListener<T> listener;

    public PickerDialog(Context context, T view, int layoutRes) {
        super(context, R.style.TimeDatePickers_Dialog_BottomSheet);
        this.view = view;
        this.layoutRes = layoutRes;
    }

    public PickerDialog<T> setListener(OnSelectedListener<T> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes);

        LinearLayout layout = findViewById(R.id.layout);

        if (view.getParent() != null && view.getParent() instanceof ViewGroup)
            ((ViewGroup) view.getParent()).removeView(view);

        layout.addView(view, 0);

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onSelect(PickerDialog.this, view);

                dismiss();
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onCancel(PickerDialog.this);

                dismiss();
            }
        });
    }

    public T getView() {
        return view;
    }

    private interface OnSelectedListener<T extends View> {
        void onSelect(PickerDialog<T> dialog, T view);
        void onCancel(PickerDialog<T> dialog);
    }
}
