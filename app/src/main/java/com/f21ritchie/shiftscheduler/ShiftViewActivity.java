package com.f21ritchie.shiftscheduler;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shiftscheduler.R;
import com.f21ritchie.shiftscheduler.Adapters.RecyclerAdapterColourAMAssigned;
import com.f21ritchie.shiftscheduler.Adapters.RecyclerAdapterColourPMAssigned;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class ShiftViewActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView rv_AM, rv_PM;
    ImageButton imageButton_editAM, imageButton_editPM;
    RecyclerAdapterColourAMAssigned recyclerAdapter_AMassigned;
    RecyclerAdapterColourPMAssigned recyclerAdapter_PMassigned;
    List<Employee> list_assignedAM, list_assignedPM;
    Database database;
    TextView tv_selectedDate;
    CheckBox cb_busy;
    Button bt_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_view);
        database = new Database(this);

        tv_selectedDate = findViewById(R.id.tv_selectedDate);
        String text = CalendarUtils.selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) + ", "
                + CalendarUtils.formattedDate(CalendarUtils.selectedDate);
        tv_selectedDate.setText(text);

        rv_AM = findViewById(R.id.rv_shiftAM);
        rv_PM = findViewById(R.id.rv_shiftPM);

        imageButton_editAM = findViewById(R.id.imageButton_editAM);
        imageButton_editAM.setOnClickListener(this);
        imageButton_editPM = findViewById(R.id.imageButton_editPM);
        imageButton_editPM.setOnClickListener(this);

        cb_busy = findViewById(R.id.cb_busy);
        cb_busy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_busy.isChecked()) {
                    database.updateDate(CalendarUtils.selectedDate, "Y");
                }
                else {
                    database.updateDate(CalendarUtils.selectedDate, "N");
                }
            }
        });
        setCheckBox();
        setUpRecyclerView();

        bt_save = findViewById(R.id.bt_saveFinal);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShiftViewActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    void setUpRecyclerView() {
        list_assignedAM = database.getEmpForSelectShift(CalendarUtils.selectedDate, "AM");
        rv_AM.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter_AMassigned = new RecyclerAdapterColourAMAssigned(list_assignedAM);
        rv_AM.setAdapter(recyclerAdapter_AMassigned);

        list_assignedPM = database.getEmpForSelectShift(CalendarUtils.selectedDate, "PM");
        rv_PM.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter_PMassigned = new RecyclerAdapterColourPMAssigned(list_assignedPM);
        rv_PM.setAdapter(recyclerAdapter_PMassigned);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton_editAM:
                Intent intent = new Intent(this, ShiftEditActivityAM.class);
                startActivity(intent);
                finish();
        }
        switch (view.getId()) {
            case R.id.imageButton_editPM:
                Intent intent = new Intent(this, ShiftEditActivityPM.class);
                startActivity(intent);
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void setCheckBox() {
        if (database.isBusy(CalendarUtils.selectedDate)) {
            cb_busy.setChecked(true);
        }
        else {
            cb_busy.setChecked(false);
        }
    }
}
