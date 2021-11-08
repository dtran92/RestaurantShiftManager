package com.f21ritchie.shiftscheduler;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shiftscheduler.R;
import com.f21ritchie.shiftscheduler.Adapters.RecyclerAdapterColourAMAssigned;
import com.f21ritchie.shiftscheduler.Adapters.RecyclerAdapterColourAMAvai;

import java.util.List;

public class ShiftEditActivityAM extends AppCompatActivity implements RecyclerAdapterColourAMAvai.OnEmployeeListener,
        RecyclerAdapterColourAMAssigned.OnEmployeeListener, View.OnClickListener {
    String dayOfWeek, selectedDate;
    RecyclerView rv_avai, rv_assigned;
    Button bt_saveShift;
    RecyclerAdapterColourAMAvai recyclerAdapter_avai;
    RecyclerAdapterColourAMAssigned recyclerAdapter_assigned;
    Database database;
    List<Employee> list_avai, list_assigned;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_edit);

        database = new Database(this);

        dayOfWeek = getIntent().getExtras().getString("DayOfWeek");
        selectedDate = getIntent().getExtras().getString("SelectedDate");

        rv_avai = findViewById(R.id.rv_avai);
        rv_assigned = findViewById(R.id.rv_assigned);
        setUpRecyclerView();

        bt_saveShift = findViewById(R.id.bt_saveShift);
        bt_saveShift.setOnClickListener(this);
    }

    void setUpRecyclerView() {
        list_avai = database.getAvaiEmp(selectedDate, dayOfWeek, "AM");
        rv_avai.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter_avai = new RecyclerAdapterColourAMAvai(list_avai, this);
        rv_avai.setAdapter(recyclerAdapter_avai);

        list_assigned = database.getEmpForSelectShift(selectedDate, "AM");
        rv_assigned.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter_assigned = new RecyclerAdapterColourAMAssigned(list_assigned, this);
        rv_assigned.setAdapter(recyclerAdapter_assigned);
    }

    @Override
    public void onEmployeeClickAvai(int position) {
        Employee selectedEmp = list_avai.get(position);
        new AlertDialog.Builder(this)
                .setMessage("Schedule this employee for the shift?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.addOneEmpToShift(selectedEmp, selectedDate, "AM");
                        setUpRecyclerView();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onEmployeeClickAssigned(int position) {
        Employee selectedEmp = list_assigned.get(position);
        new AlertDialog.Builder(this)
                .setMessage("Remove this employee for the shift?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.deleteOneEmpFromShift(selectedEmp, selectedDate, "AM");
                        setUpRecyclerView();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_saveShift:
                Intent intent = new Intent(this, ShiftViewActivity.class);
                intent.putExtra("DayOfWeek", dayOfWeek);
                intent.putExtra("SelectedDate", selectedDate);
                startActivity(intent);
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ShiftViewActivity.class);
        intent.putExtra("DayOfWeek", dayOfWeek);
        intent.putExtra("SelectedDate", selectedDate);
        startActivity(intent);
        finish();
    }
}
