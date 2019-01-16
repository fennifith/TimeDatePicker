package me.jfenn.timedatepickersample;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import me.jfenn.timedatepickers.dialogs.DateSheetPickerDialog;
import me.jfenn.timedatepickers.dialogs.PickerDialog;
import me.jfenn.timedatepickers.dialogs.TimeSheetPickerDialog;
import me.jfenn.timedatepickers.views.LinearDatePickerView;
import me.jfenn.timedatepickers.views.LinearTimePickerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimeSheetPickerDialog(MainActivity.this)
                        .setListener(new PickerDialog.OnSelectedListener<LinearTimePickerView>() {
                            @Override
                            public void onSelect(PickerDialog<LinearTimePickerView> dialog, LinearTimePickerView view) {
                                Toast.makeText(MainActivity.this, view.getHourOfDay() + ":" + view.getMinute(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel(PickerDialog<LinearTimePickerView> dialog) {
                            }
                        })
                        .show();
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
