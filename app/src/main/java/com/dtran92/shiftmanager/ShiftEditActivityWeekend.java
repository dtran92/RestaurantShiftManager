package com.dtran92.shiftmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtran92.shiftmanager.Adapters.RecyclerAdapterAssignedWeekend;
import com.dtran92.shiftmanager.Adapters.RecyclerAdapterAvaiWeekend;

import java.util.List;

public class ShiftEditActivityWeekend extends AppCompatActivity implements RecyclerAdapterAvaiWeekend.OnEmployeeListener,
        RecyclerAdapterAssignedWeekend.OnEmployeeListener {
    RecyclerView rv_avai, rv_assigned;
    Button bt_saveShift;
    RecyclerAdapterAvaiWeekend recyclerAdapter_avai;
    RecyclerAdapterAssignedWeekend recyclerAdapter_assigned;
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

//        bt_saveShift = findViewById(R.id.bt_saveShift);
//        bt_saveShift.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                setResult(Activity.RESULT_OK, intent);
//                ShiftEditActivityWeekend.super.onBackPressed();
//                finish();
//            }
//        });
    }

    void setUpRecyclerView() {
        list_avai = database.getAvaiEmp(CalendarUtils.selectedDate, "F");
        rv_avai.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter_avai = new RecyclerAdapterAvaiWeekend(list_avai, this);
        rv_avai.setAdapter(recyclerAdapter_avai);

        list_assigned = database.getEmpForSelectShift(CalendarUtils.selectedDate, "F");
        rv_assigned.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter_assigned = new RecyclerAdapterAssignedWeekend(list_assigned, this);
        rv_assigned.setAdapter(recyclerAdapter_assigned);
    }

    @Override
    public void onEmployeeClickAvai(int position) {
        Employee selectedEmp = list_avai.get(position);
        database.addOneEmpToShift(selectedEmp, CalendarUtils.selectedDate, "F");
        list_avai.remove(selectedEmp);
        recyclerAdapter_avai.notifyItemRemoved(position);
        list_assigned.add(selectedEmp);
        recyclerAdapter_assigned.notifyItemInserted(list_assigned.size() - 1);
    }

    @Override
    public void onEmployeeClickAssigned(int position) {
        Employee selectedEmp = list_assigned.get(position);
        database.deleteOneEmpFromShift(selectedEmp, CalendarUtils.selectedDate, "F");
        list_assigned.remove(selectedEmp);
        recyclerAdapter_assigned.notifyItemRemoved(position);
        list_avai.add(selectedEmp);
        recyclerAdapter_avai.notifyItemInserted(list_avai.size() - 1);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
        finish();
    }
}
