package com.f21ritchie.shiftscheduler;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shiftscheduler.R;

public class EmployeeAddEditActivity extends AppCompatActivity {
    TextView tv_fullName;
    EditText et_email;
    Button bt_save, bt_cancel;
    Employee employee;
    Switch mon_AM, mon_PM, tue_AM, tue_PM, wed_AM, wed_PM, thu_AM, thu_PM, fri_AM, fri_PM, sat_AM, sat_PM, sun_AM, sun_PM;
    ToggleButton trained_am, trained_pm;
    Database database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability_edit);

        String fname = getIntent().getExtras().getString("fname");
        String lname = getIntent().getExtras().getString("lname");
        String email = getIntent().getExtras().getString("email");
        int id = getIntent().getExtras().getInt("id");

        String currMonAM = getIntent().getExtras().getString("currMonAM");
        String currMonPM = getIntent().getExtras().getString("currMonPM");
        String currTueAM = getIntent().getExtras().getString("currTueAM");
        String currTuePM = getIntent().getExtras().getString("currTuePM");
        String currWedAM = getIntent().getExtras().getString("currWedAM");
        String currWedPM = getIntent().getExtras().getString("currWedPM");
        String currThuAM = getIntent().getExtras().getString("currThuAM");
        String currThuPM = getIntent().getExtras().getString("currThuPM");
        String currFriAM = getIntent().getExtras().getString("currFriAM");
        String currFriPM = getIntent().getExtras().getString("currFriPM");
        String currSatAM = getIntent().getExtras().getString("currSatAM");
        String currSatPM = getIntent().getExtras().getString("currSatPM");
        String currSunAM = getIntent().getExtras().getString("currSunAM");
        String currSunPM = getIntent().getExtras().getString("currSunPM");
        String currTrainedAM = getIntent().getExtras().getString("currTrainedAM");
        String currTrainedPM = getIntent().getExtras().getString("currTrainedPM");

        database = new Database(this);

        trained_am = findViewById(R.id.trained_amv2);
        trained_pm = findViewById(R.id.trained_pmv2);

        mon_AM = (Switch) findViewById(R.id.mon_AMv2);
        mon_PM = (Switch) findViewById(R.id.mon_PMv2);
        tue_AM = (Switch) findViewById(R.id.tue_AMv2);
        tue_PM = (Switch) findViewById(R.id.tue_PMv2);
        wed_AM = (Switch) findViewById(R.id.wed_AMv2);
        wed_PM = (Switch) findViewById(R.id.wed_PMv2);
        thu_AM = (Switch) findViewById(R.id.thu_AMv2);
        thu_PM = (Switch) findViewById(R.id.thu_PMv2);
        fri_AM = (Switch) findViewById(R.id.fri_AMv2);
        fri_PM = (Switch) findViewById(R.id.fri_PMv2);
        sat_AM = (Switch) findViewById(R.id.sat_AMv2);
        sat_PM = (Switch) findViewById(R.id.sat_PMv2);
        sun_AM = (Switch) findViewById(R.id.sun_AMv2);
        sun_PM = (Switch) findViewById(R.id.sun_PMv2);

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
        setSwitch(sat_AM, currSatAM);
        setSwitch(sat_PM, currSatPM);
        setSwitch(sun_AM, currSunAM);
        setSwitch(sun_PM, currSunPM);
        setToggle(trained_am, currTrainedAM);
        setToggle(trained_pm, currTrainedPM);

        tv_fullName = findViewById(R.id.tv_namev2);
        tv_fullName.setText(fname + " " + lname);

        et_email = findViewById(R.id.et_newemailv2);
        et_email.setText(email);

        bt_save = findViewById(R.id.bt_savev2);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedEmail = et_email.getText().toString();
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
                String satAM = checkSwitch(sat_AM);
                String satPM = checkSwitch(sat_PM);
                String sunAM = checkSwitch(sun_AM);
                String sunPM = checkSwitch(sun_PM);

                employee = new Employee(id, fname, lname, updatedEmail, trained_AM, trained_PM,
                        monAM, monPM, tueAM, tuePM, wedAM, wedPM, thuAM, thuPM, friAM, friPM, satAM, satPM, sunAM, sunPM);

                // id = -1 meaning adding new employee
                if (id == -1) {
                    database.addOneEmployee(employee);
                    backToEmp();
                }

                // id != -1 meaning editing current employee
                else {
                    database.updateOneEmployee(employee);
                    backToEmp();
                }
            }
        });

        bt_cancel = findViewById(R.id.bt_cancelv2);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToEmp();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backToEmp();
    }

    public String checkSwitch(Switch switchCheck) {
        String value;
        if (switchCheck.isChecked()) {
            value = "Y";
        }
        else {
            value = "N";
        }
        return value;
    }

    public String checkToggle(ToggleButton toggleCheck) {
        String value;
        if (toggleCheck.isChecked()) {
            value = "Y";
        }
        else {
            value = "N";
        }
        return value;
    }

    public void setSwitch(Switch switchCheck, String YN) {
        if (YN == null) switchCheck.setChecked(false);
        else {if (YN.equals("Y")) switchCheck.setChecked(true);
        else switchCheck.setChecked(false);}
    }

    public void setToggle(ToggleButton toggleCheck, String YN) {
        if (YN == null) toggleCheck.setChecked(false);
        else {if (YN.equals("Y")) toggleCheck.setChecked(true);
        else toggleCheck.setChecked(false);}
    }

    public void backToEmp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("position", 1);
        startActivity(intent);
        finish();
    }
}
