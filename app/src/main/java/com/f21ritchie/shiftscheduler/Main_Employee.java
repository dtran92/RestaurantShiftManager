package com.f21ritchie.shiftscheduler;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shiftscheduler.R;
import com.f21ritchie.shiftscheduler.Adapters.RecyclerAdapter;

import java.util.List;

public class Main_Employee extends Fragment implements View.OnClickListener, RecyclerAdapter.OnEmployeeListener {
    Button bt_add;
    EditText et_fname, et_lname, et_email;
    Database database;
    RecyclerView rv_emp;
    RecyclerAdapter recyclerAdapter;
    List<Employee> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        list.add(new Employee(1, "a", "b", "c"));
        database = new Database(getContext());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_employee, container, false);
        bt_add = view.findViewById(R.id.bt_add);
        bt_add.setOnClickListener(this);

        et_fname = view.findViewById(R.id.et_fname);
        et_lname = view.findViewById(R.id.et_lname);
        et_email = view.findViewById(R.id.et_email);

        list = database.getAllEmployees();
        rv_emp = (RecyclerView) view.findViewById(R.id.rv_emp);
        rv_emp.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new RecyclerAdapter(list);
        recyclerAdapter.setOnEmployeeListener(this);
        rv_emp.setAdapter(recyclerAdapter);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rv_emp);

        return view;
    }

    // Clicking Add button: adding an employee
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_add:
                String fname = et_fname.getText().toString();
                String lname = et_lname.getText().toString();
                String email = et_email.getText().toString();
                // don't add employee if any of these field is missing a value
                if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || checkDup(new Employee(-1, fname, lname, email,
                        null, null, null, null, null, null, null, null,
                        null, null, null, null, null, null, null, null))) {}

                else {
                    Intent intent = new Intent(getActivity(), EmployeeAddEditActivity.class);
                    intent.putExtra("fname", fname);
                    intent.putExtra("lname", lname);
                    intent.putExtra("email", email);
                    intent.putExtra("id", -1);
                    startActivity(intent);
                    getActivity().finish();
                }
        }
    }

    public boolean checkDup(Employee employee) {
        List<Employee> empList = database.getAllEmployees();
        if (empList.contains(employee)) {new AlertDialog.Builder(getContext())
                .setMessage("Employee already exist!!!")
                .setNegativeButton("OK", null).show();
            return true;}
        return false;
    }

    // Clicking on an employee to update email and/or availability
    @Override
    public void onEmployeeClick(int position) {
        Employee selectedEmployee = database.getAllEmployees().get(position);
        Intent intent = new Intent(getActivity(), EmployeeAddEditActivity.class);
        String fname = selectedEmployee.getFirstName();
        String lname = selectedEmployee.getLastName();
        String email = selectedEmployee.getEmail();
        int id = selectedEmployee.getId();
        intent.putExtra("id", id);
        intent.putExtra("fname", fname);
        intent.putExtra("lname", lname);
        intent.putExtra("email", email);
        intent.putExtra("currTrainedAM", selectedEmployee.getTrained_am());
        intent.putExtra("currTrainedPM", selectedEmployee.getTrained_pm());
        intent.putExtra("currMonAM", selectedEmployee.getMon_AM());
        intent.putExtra("currMonPM", selectedEmployee.getMon_PM());
        intent.putExtra("currTueAM", selectedEmployee.getTue_AM());
        intent.putExtra("currTuePM", selectedEmployee.getTue_PM());
        intent.putExtra("currWedAM", selectedEmployee.getWed_AM());
        intent.putExtra("currWedPM", selectedEmployee.getWed_PM());
        intent.putExtra("currThuAM", selectedEmployee.getThu_AM());
        intent.putExtra("currThuPM", selectedEmployee.getThu_PM());
        intent.putExtra("currFriAM", selectedEmployee.getFri_AM());
        intent.putExtra("currFriPM", selectedEmployee.getFri_PM());
        intent.putExtra("currSatAM", selectedEmployee.getSat_AM());
        intent.putExtra("currSatPM", selectedEmployee.getSat_PM());
        intent.putExtra("currSunAM", selectedEmployee.getSun_AM());
        intent.putExtra("currSunPM", selectedEmployee.getSun_PM());
        startActivity(intent);
        getActivity().finish();
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

}
