package com.f21ritchie.shiftscheduler;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shiftscheduler.R;
import com.f21ritchie.shiftscheduler.Adapters.RecyclerAdapterColourAMAssigned;
import com.f21ritchie.shiftscheduler.Adapters.RecyclerAdapterColourPMAssigned;

import java.util.List;

public class ShiftViewActivity extends AppCompatActivity implements View.OnClickListener{
    String dayOfWeek, selectedDate;
    RecyclerView rv_AM, rv_PM;
    Button bt_editAM, bt_editPM;
    RecyclerAdapterColourAMAssigned recyclerAdapter_AMassigned;
    RecyclerAdapterColourPMAssigned recyclerAdapter_PMassigned;
    List<Employee> list_assignedAM, list_assignedPM;
    Database database;
    TextView tv_selectedDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_view);
        database = new Database(this);

        dayOfWeek = getIntent().getExtras().getString("DayOfWeek");
        selectedDate = getIntent().getExtras().getString("SelectedDate");

        tv_selectedDate = findViewById(R.id.tv_selectedDate);
        String text = dayOfWeek + ", " + selectedDate;
        tv_selectedDate.setText(text);

        rv_AM = findViewById(R.id.rv_shiftAM);
        rv_PM = findViewById(R.id.rv_shiftPM);

        bt_editAM = findViewById(R.id.bt_editAM);
        bt_editAM.setOnClickListener(this);
        bt_editPM = findViewById(R.id.bt_editPM);
        bt_editPM.setOnClickListener(this);
        setUpRecyclerView();
    }

    void setUpRecyclerView() {
        list_assignedAM = database.getEmpForSelectShift(selectedDate, "AM");
        rv_AM.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter_AMassigned = new RecyclerAdapterColourAMAssigned(list_assignedAM);
        rv_AM.setAdapter(recyclerAdapter_AMassigned);

        list_assignedPM = database.getEmpForSelectShift(selectedDate, "PM");
        rv_PM.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter_PMassigned = new RecyclerAdapterColourPMAssigned(list_assignedPM);
        rv_PM.setAdapter(recyclerAdapter_PMassigned);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_editAM:
                Intent intent = new Intent(this, ShiftEditActivityAM.class);
                intent.putExtra("SelectedDate", selectedDate);
                intent.putExtra("DayOfWeek", dayOfWeek);
                startActivity(intent);
                finish();
        }
        switch (view.getId()) {
            case R.id.bt_editPM:
                Intent intent = new Intent(this, ShiftEditActivityPM.class);
                intent.putExtra("SelectedDate", selectedDate);
                intent.putExtra("DayOfWeek", dayOfWeek);
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
}
