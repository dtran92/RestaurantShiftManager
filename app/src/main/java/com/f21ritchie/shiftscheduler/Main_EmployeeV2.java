package com.f21ritchie.shiftscheduler;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shiftscheduler.R;
import com.f21ritchie.shiftscheduler.Adapters.RecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Main_EmployeeV2 extends Fragment implements View.OnClickListener, RecyclerAdapter.OnEmployeeListener{
    RecyclerView rv_emp;
    RecyclerAdapter recyclerAdapter;
    FloatingActionButton fb_add;
    List<Employee> list;
    Database database;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_employee_v2, container, false);
        database = new Database(getContext());
        list = database.getAllEmployees();
        rv_emp = (RecyclerView) view.findViewById(R.id.rv_empV2);
        rv_emp.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new RecyclerAdapter(list);
        recyclerAdapter.setOnEmployeeListener(this);
        rv_emp.setAdapter(recyclerAdapter);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rv_emp);
        fb_add = view.findViewById(R.id.fb_add);
        fb_add.setOnClickListener(this);

        return view;
    }

    public void refreshRecyclerView() {
        list = database.getAllEmployees();
        rv_emp.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new RecyclerAdapter(list);
        recyclerAdapter.setOnEmployeeListener(this);
        rv_emp.setAdapter(recyclerAdapter);
    }

    // delete by swiping left or right
    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Employee selectedEmp = database.getAllEmployees().get(viewHolder.getAdapterPosition());
            new AlertDialog.Builder(getContext())
                    .setMessage("Delete this employee?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            database.deleteOneEmployee(selectedEmp);
                            refreshRecyclerView();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            recyclerAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_add:
                Intent intent = new Intent(getActivity(), EmployeeAddEditActivityV2.class);
                intent.putExtra("id", -1);
                startActivity(intent);
                getActivity().finish();
        }
    }

    // Clicking on an employee to update email and/or availability
    @Override
    public void onEmployeeClick(int position) {
        Employee selectedEmployee = database.getAllEmployees().get(position);
        Intent intent = new Intent(getActivity(), EmployeeAddEditActivityV2.class);
        intent.putExtra("id", selectedEmployee.getId());
        startActivity(intent);
        getActivity().finish();
    }
}