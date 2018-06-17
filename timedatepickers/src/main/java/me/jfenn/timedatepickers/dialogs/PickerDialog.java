package me.jfenn.timedatepickers.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import me.jfenn.timedatepickers.R;
import me.jfenn.timedatepickers.interfaces.Themable;

public abstract class PickerDialog<T extends View & Themable> extends AppCompatDialog implements Themable, View.OnClickListener {

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

        findViewById(R.id.ok).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.root).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            if (v.getId() == R.id.ok)
                listener.onSelect(this, view);
            else listener.onCancel(this);
        }

        dismiss();
    }

    public T getView() {
        return view;
    }

    @Override
    public void setSelectionColor(int color) {
        view.setSelectionColor(color);
    }

    @Override
    public int getSelectionColor() {
        return view.getSelectionColor();
    }

    @Override
    public void setSelectionTextColor(int color) {
        view.setSelectionTextColor(color);
    }

    @Override
    public int getSelectionTextColor() {
        return view.getSelectionTextColor();
    }

    @Override
    public void setPrimaryTextColor(int color) {
        view.setPrimaryTextColor(color);
    }

    @Override
    public int getPrimaryTextColor() {
        return view.getPrimaryTextColor();
    }

    @Override
    public void setSecondaryTextColor(int color) {
        view.setSecondaryTextColor(color);
    }

    @Override
    public int getSecondaryTextColor() {
        return view.getSecondaryTextColor();
    }

    @Override
    public void setBackgroundColor(int color) {
        view.setBackgroundColor(color);
    }

    @Override
    public int getBackgroundColor() {
        return view.getBackgroundColor();
    }

    @Override
    public void setPrimaryBackgroundColor(int color) {
        view.setPrimaryBackgroundColor(color);
    }

    @Override
    public int getPrimaryBackgroundColor() {
        return view.getPrimaryBackgroundColor();
    }

    @Override
    public void setSecondaryBackgroundColor(int color) {
        view.setSecondaryBackgroundColor(color);
    }

    @Override
    public int getSecondaryBackgroundColor() {
        return view.getSecondaryBackgroundColor();
    }

    public interface OnSelectedListener<T extends View & Themable> {
        void onSelect(PickerDialog<T> dialog, T view);
        void onCancel(PickerDialog<T> dialog);
    }
}
