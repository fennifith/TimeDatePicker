package me.jfenn.timedatepickersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.jfenn.timedatepickers.dialogs.DateSheetPickerDialog;
import me.jfenn.timedatepickers.dialogs.TimeSheetPickerDialog;
import me.jfenn.timedatepickers.views.LinearDatePickerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimeSheetPickerDialog(MainActivity.this).show();
            }
        });

        findViewById(R.id.date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateSheetPickerDialog dialog = new DateSheetPickerDialog(MainActivity.this);
                dialog.getView().setItemStyle(LinearDatePickerView.STYLE_BOX);
                dialog.show();
            }
        });
    }
}
