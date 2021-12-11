package com.dtran92.shiftmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtran92.shiftmanager.Adapters.RecyclerAdapterAssignedWeekend;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class ShiftViewActivityWeekend extends AppCompatActivity {
    RecyclerView rv_Weekend;
    List<Employee> list_assigned;
    Database database;
    TextView tv_selectedDate;
    CheckBox cb_busy;
    Button bt_save;
    String isBusy;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageButton imageButton_editWeekend;
    RecyclerAdapterAssignedWeekend recyclerAdapterAssignedWeekend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_view_weekend);

        database = Database.getInstance(this);

        tv_selectedDate = findViewById(R.id.tv_selectedDateWeekend);
        String text = CalendarUtils.selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) + ", "
                + CalendarUtils.formattedDate(CalendarUtils.selectedDate);
        tv_selectedDate.setText(text);

        rv_Weekend = findViewById(R.id.rv_shiftWeekend);
        imageButton_editWeekend = findViewById(R.id.imageButton_editWeekend);
        imageButton_editWeekend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShiftViewActivityWeekend.this, ShiftEditActivityWeekend.class);
                activityResultLauncher.launch(intent);
            }
        });


        cb_busy = findViewById(R.id.cb_busyWeekend);
        cb_busy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_busy.isChecked()) {
                    isBusy = "Y";
                } else {
                    isBusy = "N";
                }
            }
        });

        setCheckBox();
        setUpRecyclerView();

//        bt_save = findViewById(R.id.bt_saveFinalWeekend);
//        bt_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                database.updateIfExistElseAdd(CalendarUtils.selectedDate, isBusy);
//                Intent intent = new Intent();
//                setResult(Activity.RESULT_OK, intent);
//                ShiftViewActivityWeekend.super.onBackPressed();
//                finish();
//            }
//        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                list_assigned = database.getEmpForSelectShift(CalendarUtils.selectedDate, "F");
                recyclerAdapterAssignedWeekend.setEmpList(list_assigned);
                rv_Weekend.setAdapter(recyclerAdapterAssignedWeekend);
            }
        });
    }

        public void setUpRecyclerView() {
            list_assigned = database.getEmpForSelectShift(CalendarUtils.selectedDate, "F");
            rv_Weekend.setLayoutManager(new LinearLayoutManager(this));
            recyclerAdapterAssignedWeekend = new RecyclerAdapterAssignedWeekend(list_assigned);
            rv_Weekend.setAdapter(recyclerAdapterAssignedWeekend);
        }

    public void setCheckBox() {
        if (database.isBusy(CalendarUtils.selectedDate)) {
            cb_busy.setChecked(true);
            isBusy = "Y";
        }
        else {
            cb_busy.setChecked(false);
            isBusy = "N";
        }
    }

    @Override
    public void onBackPressed() {
        database.updateIfExistElseAdd(CalendarUtils.selectedDate, isBusy);
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        ShiftViewActivityWeekend.super.onBackPressed();
        finish();
    }
}
