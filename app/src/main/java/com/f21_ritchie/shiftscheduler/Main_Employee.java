package com.f21_ritchie.shiftscheduler;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.f21_ritchie.shiftscheduler.Adapters.RecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Main_Employee extends Fragment implements View.OnClickListener, RecyclerAdapter.OnEmployeeListener{
    RecyclerView rv_emp;
    RecyclerAdapter recyclerAdapter;
    FloatingActionButton fb_add;
    List<Employee> list;
    Database database;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // delete by swiping left or right
    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Employee selectedEmp = database.getAllEmployees().get(position);
            new AlertDialog.Builder(getContext())
                    .setMessage("Delete this employee?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            database.deleteOneEmployee(selectedEmp);
                            list.remove(selectedEmp);
                            recyclerAdapter.notifyItemRemoved(position);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            recyclerAdapter.notifyItemChanged(position);
                        }
                    })
                    .show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_employee, container, false);
        database = Database.getInstance(this.getContext());
        list = database.getAllEmployees();
        rv_emp = view.findViewById(R.id.rv_empV2);
        rv_emp.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new RecyclerAdapter(list);
        recyclerAdapter.setOnEmployeeListener(this);
        rv_emp.setAdapter(recyclerAdapter);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rv_emp);
        fb_add = view.findViewById(R.id.fb_add);
        fb_add.setOnClickListener(this);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    list = database.getAllEmployees();
                    recyclerAdapter.setEmpList(list);
                    rv_emp.setAdapter(recyclerAdapter);
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_add:
                Intent intent = new Intent(getActivity(), EmployeeAddEditActivity.class);
                intent.putExtra("id", -1);
                activityResultLauncher.launch(intent);
        }
    }

    // Clicking on an employee to update email and/or availability
    @Override
    public void onEmployeeClick(int position) {
        Employee selectedEmployee = database.getAllEmployees().get(position);
        Intent intent = new Intent(getActivity(), EmployeeAddEditActivity.class);
        intent.putExtra("id", selectedEmployee.getId());
        activityResultLauncher.launch(intent);
    }
}