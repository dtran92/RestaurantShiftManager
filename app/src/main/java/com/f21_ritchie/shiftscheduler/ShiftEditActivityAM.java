package com.f21_ritchie.shiftscheduler;

import android.app.Activity;
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

import com.f21_ritchie.shiftscheduler.Adapters.RecyclerAdapterColourAMAssigned;
import com.f21_ritchie.shiftscheduler.Adapters.RecyclerAdapterColourAMAvai;

import java.util.List;

public class ShiftEditActivityAM extends AppCompatActivity implements RecyclerAdapterColourAMAvai.OnEmployeeListener,
        RecyclerAdapterColourAMAssigned.OnEmployeeListener {
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

        database = Database.getInstance(this);

        rv_avai = findViewById(R.id.rv_avai);
        rv_assigned = findViewById(R.id.rv_assigned);
        setUpRecyclerView();

        bt_saveShift = findViewById(R.id.bt_saveShift);
        bt_saveShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                ShiftEditActivityAM.super.onBackPressed();
                finish();
            }
        });
    }

    void setUpRecyclerView() {
        list_avai = database.getAvaiEmp(CalendarUtils.selectedDate, "AM");
        rv_avai.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter_avai = new RecyclerAdapterColourAMAvai(list_avai, this);
        rv_avai.setAdapter(recyclerAdapter_avai);

        list_assigned = database.getEmpForSelectShift(CalendarUtils.selectedDate, "AM");
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
                        database.addOneEmpToShift(selectedEmp, CalendarUtils.selectedDate, "AM");
                        list_avai.remove(selectedEmp);
                        recyclerAdapter_avai.notifyItemRemoved(position);

                        list_assigned.add(selectedEmp);
                        recyclerAdapter_assigned.notifyItemInserted(list_assigned.size() - 1);
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
                        database.deleteOneEmpFromShift(selectedEmp, CalendarUtils.selectedDate, "AM");
                        list_assigned.remove(selectedEmp);
                        recyclerAdapter_assigned.notifyItemRemoved(position);

                        list_avai.add(selectedEmp);
                        recyclerAdapter_avai.notifyItemInserted(list_avai.size() - 1);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}

