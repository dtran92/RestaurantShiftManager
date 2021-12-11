package com.dtran92.shiftmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EmployeeAddEditActivity extends AppCompatActivity {
    EditText et_fname, et_lname, et_email;
    Button bt_save, bt_cancel;
    Switch mon_AM, mon_PM, tue_AM, tue_PM, wed_AM, wed_PM, thu_AM, thu_PM, fri_AM, fri_PM, sat, sun;
    ToggleButton trained_am, trained_pm;
    Database database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability_edit);
        database = Database.getInstance(this);

        int id = getIntent().getExtras().getInt("id");

        Employee selectedEmp = database.getOneEmployee(id);

        String fname = selectedEmp.getFirstName();
        String lname = selectedEmp.getLastName();
        String email = selectedEmp.getEmail();
        String currMonAM = selectedEmp.getMon_AM();
        String currMonPM = selectedEmp.getMon_PM();
        String currTueAM = selectedEmp.getTue_AM();
        String currTuePM = selectedEmp.getTue_PM();
        String currWedAM = selectedEmp.getWed_AM();
        String currWedPM = selectedEmp.getWed_PM();
        String currThuAM = selectedEmp.getThu_AM();
        String currThuPM = selectedEmp.getThu_PM();
        String currFriAM = selectedEmp.getFri_AM();
        String currFriPM = selectedEmp.getFri_PM();
        String currSat = selectedEmp.getSat();
        String currSun = selectedEmp.getSun();
        String currTrainedAM = selectedEmp.getTrained_am();
        String currTrainedPM = selectedEmp.getTrained_pm();

        trained_am = findViewById(R.id.trained_amv2new);
        trained_pm = findViewById(R.id.trained_pmv2new);
        mon_AM = (Switch) findViewById(R.id.mon_AMv2new);
        mon_PM = (Switch) findViewById(R.id.mon_PMv2new);
        tue_AM = (Switch) findViewById(R.id.tue_AMv2new);
        tue_PM = (Switch) findViewById(R.id.tue_PMv2new);
        wed_AM = (Switch) findViewById(R.id.wed_AMv2new);
        wed_PM = (Switch) findViewById(R.id.wed_PMv2new);
        thu_AM = (Switch) findViewById(R.id.thu_AMv2new);
        thu_PM = (Switch) findViewById(R.id.thu_PMv2new);
        fri_AM = (Switch) findViewById(R.id.fri_AMv2new);
        fri_PM = (Switch) findViewById(R.id.fri_PMv2new);
        sat = (Switch) findViewById(R.id.sat_v2new);
        sun = (Switch) findViewById(R.id.sun_v2new);

        setSwitch(mon_AM, currMonAM);
        setSwitch(mon_PM, currMonPM);
        setSwitch(tue_AM, currTueAM);
        setSwitch(tue_PM, currTuePM);
        setSwitch(wed_AM, currWedAM);
        setSwitch(wed_PM, currWedPM);
        setSwitch(thu_AM, currThuAM);
        setSwitch(thu_PM, currThuPM);
        setSwitch(fri_AM, currFriAM);
        setSwitch(fri_PM, currFriPM);
        setSwitch(sat, currSat);
        setSwitch(sun, currSun);
        setToggle(trained_am, currTrainedAM);
        setToggle(trained_pm, currTrainedPM);

        et_lname = findViewById(R.id.et_lnamev2new);
        et_fname = findViewById(R.id.et_fnamev2new);
        et_email = findViewById(R.id.et_emailv2new);

        et_lname.setText(lname);
        et_fname.setText(fname);
        et_email.setText(email);

        bt_save = findViewById(R.id.bt_savev2new);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedEmail = et_email.getText().toString();
                String updatedLname = et_lname.getText().toString();
                String updatedFname = et_fname.getText().toString();
                String trained_AM = checkToggle(trained_am);
                String trained_PM = checkToggle(trained_pm);
                String monAM = checkSwitch(mon_AM);
                String monPM = checkSwitch(mon_PM);
                String tueAM = checkSwitch(tue_AM);
                String tuePM = checkSwitch(tue_PM);
                String wedAM = checkSwitch(wed_AM);
                String wedPM = checkSwitch(wed_PM);
                String thuAM = checkSwitch(thu_AM);
                String thuPM = checkSwitch(thu_PM);
                String friAM = checkSwitch(fri_AM);
                String friPM = checkSwitch(fri_PM);
                String satAv = checkSwitch(sat);
                String sunAv = checkSwitch(sun);

                // id = -1 meaning adding new employee
                if (id == -1) {
                    if (checkMissing(updatedFname, updatedLname, updatedEmail) || checkDup(updatedFname, updatedLname, updatedEmail)) {
                    } else {
                        database.addOneEmployee(new Employee(id, updatedFname, updatedLname, updatedEmail, trained_AM, trained_PM,
                                monAM, monPM, tueAM, tuePM, wedAM, wedPM, thuAM, thuPM, friAM, friPM, satAv, sunAv));
                        backToEmp();
                    }
                }

                // id != -1 meaning editing current employee
                else {
                    if (checkMissing(updatedFname, updatedLname, updatedEmail)) {
                    } else {
                        database.updateOneEmployee(new Employee(id, updatedFname, updatedLname, updatedEmail, trained_AM, trained_PM,
                                monAM, monPM, tueAM, tuePM, wedAM, wedPM, thuAM, thuPM, friAM, friPM, satAv, sunAv));
                        Main_Calendar.calendarAdapter.notifyDataSetChanged();
                        backToEmp();
                    }
                }
            }
        });

        bt_cancel = findViewById(R.id.bt_cancelv2new);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToEmp();
            }
        });
    }

    public String checkSwitch(Switch switchCheck) {
        String value;
        if (switchCheck.isChecked()) {
            value = "Y";
        } else {
            value = "N";
        }
        return value;
    }

    public String checkToggle(ToggleButton toggleCheck) {
        String value;
        if (toggleCheck.isChecked()) {
            value = "Y";
        } else {
            value = "N";
        }
        return value;
    }

    public void setSwitch(Switch switchCheck, String YN) {
        if (YN == null) switchCheck.setChecked(false);
        else {
            switchCheck.setChecked(YN.equals("Y"));
        }
    }

    public void setToggle(ToggleButton toggleCheck, String YN) {
        if (YN == null) toggleCheck.setChecked(false);
        else {
            toggleCheck.setChecked(YN.equals("Y"));
        }
    }

    public void backToEmp() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        EmployeeAddEditActivity.super.onBackPressed();
        finish();
    }

    public boolean checkDup(String updatedFname, String updatedLname, String updatedEmail) {
        if (database.checkExist(updatedFname, updatedLname, updatedEmail)) {
            new AlertDialog.Builder(this)
                    .setMessage("Employee already exist")
                    .setNegativeButton("OK", null).show();
            return true;
        }
        return false;
    }

    public boolean checkMissing(String fname, String lname, String email) {
        if (fname.isEmpty() || lname.isEmpty() || email.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setMessage("Missing input for Name and/or Email")
                    .setNegativeButton("OK", null).show();
            return true;
        }
        return false;
    }

}
