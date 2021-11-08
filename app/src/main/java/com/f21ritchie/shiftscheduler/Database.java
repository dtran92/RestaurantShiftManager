package com.f21ritchie.shiftscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static SQLiteDatabase database;

    // Employee Table
    public static final String EMPLOYEE_TABLE = "EMPLOYEE_TABLE";
    public static final String EMP_ID = "ID";
    public static final String EMP_FIRSTNAME = "FIRSTNAME";
    public static final String EMP_LASTNAME = "LASTNAME";
    public static final String EMP_EMAIL = "EMAIL";
    public static final String EMP_TRAINED_AM = "TrainedAM";
    public static final String EMP_TRAINED_PM = "TrainedPM";
    public static final String EMP_MON_AM = "MonAM";
    public static final String EMP_MON_PM = "MonPM";
    public static final String EMP_TUE_AM = "TueAM";
    public static final String EMP_TUE_PM = "TuePM";
    public static final String EMP_WED_AM = "WedAM";
    public static final String EMP_WED_PM = "WedPM";
    public static final String EMP_THU_AM = "ThuAM";
    public static final String EMP_THU_PM = "ThuPM";
    public static final String EMP_FRI_AM = "FriAM";
    public static final String EMP_FRI_PM = "FriPM";
    public static final String EMP_SAT_AM = "SatAM";
    public static final String EMP_SAT_PM = "SatPM";
    public static final String EMP_SUN_AM = "SunAM";
    public static final String EMP_SUN_PM = "SunPM";

    // Date Table
    public static final String DATE_TABLE = "DATE_TABLE";
    public static final String DATE_ID = "DATE";
    public static final String DATE_OF_WEEK = "DateOfWeek";

    // Shift Table
    public static final String SHIFT_TABLE = "SHIFT_TABLE";
    public static final String SHIFT_DATE = "DATE";
    public static final String SHIFT_TYPE = "TYPE";
    public static final String SHIFT_EMPID = "EMP_ID";

    public Database(@Nullable Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create Employee table
        String employeeTable = "CREATE TABLE "
                + EMPLOYEE_TABLE + "(" + EMP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMP_FIRSTNAME + " TEXT, "
                + EMP_LASTNAME + " TEXT, "
                + EMP_EMAIL + " TEXT, "
                + EMP_TRAINED_AM + " TEXT,"
                + EMP_TRAINED_PM + " TEXT, "
                + EMP_MON_AM + " TEXT, "
                + EMP_MON_PM + " TEXT, "
                + EMP_TUE_AM + " TEXT, "
                + EMP_TUE_PM + " TEXT, "
                + EMP_WED_AM + " TEXT, "
                + EMP_WED_PM + " TEXT, "
                + EMP_THU_AM + " TEXT, "
                + EMP_THU_PM + " TEXT, "
                + EMP_FRI_AM + " TEXT, "
                + EMP_FRI_PM + " TEXT, "
                + EMP_SAT_AM + " TEXT, "
                + EMP_SAT_PM + " TEXT, "
                + EMP_SUN_AM + " TEXT, "
                + EMP_SUN_PM + " TEXT)";
        sqLiteDatabase.execSQL(employeeTable);

        //create Date table
        String DateTable = "CREATE TABLE "
                + DATE_TABLE + "(" + DATE_ID + " TEXT PRIMARY KEY, "
                + DATE_OF_WEEK + " TEXT)";
        sqLiteDatabase.execSQL(DateTable);

        //create Shift Table
        String ShiftTable = "CREATE TABLE "
                + SHIFT_TABLE + "("
                + SHIFT_DATE + " TEXT, "
                + SHIFT_TYPE + " TEXT, "
                + SHIFT_EMPID + " INTEGER, "
                + "PRIMARY KEY (" + SHIFT_DATE + ", " + SHIFT_TYPE + ", " +  SHIFT_EMPID + "))";
        sqLiteDatabase.execSQL(ShiftTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public List<Employee> getAllEmployees() {
        List<Employee> empList = new ArrayList<>();
        database = this.getReadableDatabase();

        // query to get data from Employee table
        String query = "SELECT * FROM " + EMPLOYEE_TABLE;

        // cursor = result set
        Cursor result = database.rawQuery(query, null);

        // moveToFirst() returns true if there are values selected
        if (result.moveToFirst()) {
            // loop through result, create new employee object and add to the return list
            do {
                int empID = result.getInt(0);
                String empFName = result.getString(1);
                String empLName = result.getString(2);
                String empEmail = result.getString(3);
                String empTrainedAM = result.getString(4);
                String empTrainedPM = result.getString(5);
                String empmonAM = result.getString(6);
                String empmonPM = result.getString(7);
                String emptueAM = result.getString(8);
                String emptuePM = result.getString(9);
                String empwedAM = result.getString(10);
                String empwedPM = result.getString(11);
                String empthuAM = result.getString(12);
                String empthuPM = result.getString(13);
                String empfriAM = result.getString(14);
                String empfriPM = result.getString(15);
                String empsatAM = result.getString(16);
                String empsatPM = result.getString(17);
                String empsunAM = result.getString(18);
                String empsunPM = result.getString(19);
                Employee employee = new Employee(empID, empFName, empLName, empEmail,
                        empTrainedAM, empTrainedPM, empmonAM, empmonPM, emptueAM, emptuePM,
                        empwedAM, empwedPM, empthuAM, empthuPM, empfriAM, empfriPM,
                        empsatAM, empsatPM, empsunAM, empsunPM);
                empList.add(employee);
            }
            while (result.moveToNext());
        } else {
            // nothing added to the return list
        }
        result.close();
        return empList;
    }

    public void addOneEmployee(Employee employee) {
        database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EMP_FIRSTNAME, employee.getFirstName());
        cv.put(EMP_LASTNAME, employee.getLastName());
        cv.put(EMP_EMAIL, employee.getEmail());
        cv.put(EMP_TRAINED_AM, employee.getTrained_am());
        cv.put(EMP_TRAINED_PM, employee.getTrained_pm());
        cv.put(EMP_MON_AM, employee.getMon_AM());
        cv.put(EMP_MON_PM, employee.getMon_PM());
        cv.put(EMP_TUE_AM, employee.getTue_AM());
        cv.put(EMP_TUE_PM, employee.getTue_PM());
        cv.put(EMP_WED_AM, employee.getWed_AM());
        cv.put(EMP_WED_PM, employee.getWed_PM());
        cv.put(EMP_THU_AM, employee.getThu_AM());
        cv.put(EMP_THU_PM, employee.getThu_PM());
        cv.put(EMP_FRI_AM, employee.getFri_AM());
        cv.put(EMP_FRI_PM, employee.getFri_PM());
        cv.put(EMP_SAT_AM, employee.getSat_AM());
        cv.put(EMP_SAT_PM, employee.getSat_PM());
        cv.put(EMP_SUN_AM, employee.getSun_AM());
        cv.put(EMP_SUN_PM, employee.getSun_PM());
        database.insert(EMPLOYEE_TABLE, null, cv);
    }

    public void updateOneEmployee(Employee employee) {
        database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EMP_EMAIL, employee.getEmail());
        cv.put(EMP_TRAINED_AM, employee.getTrained_am());
        cv.put(EMP_TRAINED_PM, employee.getTrained_pm());
        cv.put(EMP_MON_AM, employee.getMon_AM());
        cv.put(EMP_MON_PM, employee.getMon_PM());
        cv.put(EMP_TUE_AM, employee.getTue_AM());
        cv.put(EMP_TUE_PM, employee.getTue_PM());
        cv.put(EMP_WED_AM, employee.getWed_AM());
        cv.put(EMP_WED_PM, employee.getWed_PM());
        cv.put(EMP_THU_AM, employee.getThu_AM());
        cv.put(EMP_THU_PM, employee.getThu_PM());
        cv.put(EMP_FRI_AM, employee.getFri_AM());
        cv.put(EMP_FRI_PM, employee.getFri_PM());
        cv.put(EMP_SAT_AM, employee.getSat_AM());
        cv.put(EMP_SAT_PM, employee.getSat_PM());
        cv.put(EMP_SUN_AM, employee.getSun_AM());
        cv.put(EMP_SUN_PM, employee.getSun_PM());
        database.update(Database.EMPLOYEE_TABLE, cv, Database.EMP_ID + " = ?", new String[]{String.valueOf(employee.getId())});
    }

    public List<Employee> getAvaiEmp(String date, String dayOfWeek, String AMPM) {
        database = this.getReadableDatabase();

        int col_day = 0;
        String check;

        if (dayOfWeek.equals("Mon") && AMPM.equals("AM")) col_day = 6;
        if (dayOfWeek.equals("Mon") && AMPM.equals("PM")) col_day = 7;
        if (dayOfWeek.equals("Tue") && AMPM.equals("AM")) col_day = 8;
        if (dayOfWeek.equals("Tue") && AMPM.equals("PM")) col_day = 9;
        if (dayOfWeek.equals("Wed") && AMPM.equals("AM")) col_day = 10;
        if (dayOfWeek.equals("Wed") && AMPM.equals("PM")) col_day = 11;
        if (dayOfWeek.equals("Thu") && AMPM.equals("AM")) col_day = 12;
        if (dayOfWeek.equals("Thu") && AMPM.equals("PM")) col_day = 13;
        if (dayOfWeek.equals("Fri") && AMPM.equals("AM")) col_day = 14;
        if (dayOfWeek.equals("Fri") && AMPM.equals("PM")) col_day = 15;
        if (dayOfWeek.equals("Sat") && AMPM.equals("AM")) col_day = 16;
        if (dayOfWeek.equals("Sat") && AMPM.equals("PM")) col_day = 17;
        if (dayOfWeek.equals("Sun") && AMPM.equals("AM")) col_day = 18;
        if (dayOfWeek.equals("Sun") && AMPM.equals("PM")) col_day = 19;

        List<Employee> employeesList = new ArrayList<>();

        String query = "SELECT * FROM " + EMPLOYEE_TABLE + " EXCEPT "
                + "SELECT ID, FIRSTNAME, LASTNAME, EMAIL, TrainedAM, TrainedPM "
                + ", MonAM, MonPM, TueAM, TuePM, WedAM, WedPM, ThuAM, ThuPM, FriAM, FriPM, SatAM, SatPM, SunAM, SunPM "
                + "FROM EMPLOYEE_TABLE JOIN SHIFT_TABLE ON "
                + "EMPLOYEE_TABLE.ID = SHIFT_TABLE.EMP_ID WHERE SHIFT_TABLE.DATE = " + "'" + date + "'"
                + " AND " + "SHIFT_TABLE.TYPE = " + "'" + AMPM + "'";
        Cursor result = database.rawQuery(query, null);

            if (result.moveToFirst()) {
            do {
                check = result.getString(col_day);
                if (!check.equals("Y")) {continue;};
                int empID = result.getInt(0);
                String empFName = result.getString(1);
                String empLName = result.getString(2);
                String empEmail = result.getString(3);
                Employee employee = new Employee(empID, empFName, empLName, empEmail);
                employee.setTrained_am(result.getString(4));
                employee.setTrained_pm(result.getString(5));
                employeesList.add(employee);
            }
            while (result.moveToNext());
        } else {
            // nothing added to the return list
        }
        result.close();
        return employeesList;
    }

    public void deleteOneEmployee(Employee employee) {
        database = this.getWritableDatabase();
        String query = "DELETE FROM " +  EMPLOYEE_TABLE + " WHERE " + EMP_ID + " = " + employee.getId();
        System.out.println(query);
        Cursor result = database.rawQuery(query, null);
        result.moveToFirst();
    }

    //SHIFT
    public void addOneEmpToShift(Employee employee, String date, String AMPM) {
        database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SHIFT_DATE, date);
        cv.put(SHIFT_TYPE, AMPM);
        cv.put(SHIFT_EMPID, employee.getId());
        database.insert(SHIFT_TABLE, null, cv);
    }

    public void deleteOneEmpFromShift(Employee employee, String date, String AMPM) {
        database = this.getWritableDatabase();
        String query = "DELETE FROM " +  SHIFT_TABLE + " WHERE " + SHIFT_EMPID + " = " + employee.getId() +
                " AND " + SHIFT_DATE + " = " + "'" + date + "'" +
                " AND " + SHIFT_TYPE + " = " + "'" + AMPM + "'";
        Cursor result = database.rawQuery(query, null);
        result.moveToFirst();
    }

    public List<Employee> getEmpForSelectShift(String date, String AMPM) {
        database = this.getReadableDatabase();
        List<Employee> empList = new ArrayList<>();

        String query = "SELECT ID, FIRSTNAME, LASTNAME, EMAIL, TrainedAM, TrainedPM FROM EMPLOYEE_TABLE JOIN SHIFT_TABLE ON "
                + "EMPLOYEE_TABLE.ID = SHIFT_TABLE.EMP_ID WHERE SHIFT_TABLE.DATE = " + "'" + date + "'"
                + " AND " + "SHIFT_TABLE.TYPE = " + "'" + AMPM + "'";
        Cursor result = database.rawQuery(query, null);

        if (result.moveToFirst()) {
            do {
                int empID = result.getInt(0);
                String empFName = result.getString(1);
                String empLName = result.getString(2);
                String empEmail = result.getString(3);
                Employee employee = new Employee(empID, empFName, empLName, empEmail);
                employee.setTrained_am(result.getString(4));
                employee.setTrained_pm(result.getString(5));
                empList.add(employee);
            }
            while (result.moveToNext());
        }
        result.close();
        return empList;
    }


    //DATE
    public List<Date> getAllDate() {
        database = this.getReadableDatabase();
        List<Date> dateList = new ArrayList<>();
        String query = "SELECT * FROM " + DATE_TABLE;
        Cursor result = database.rawQuery(query, null);

        // moveToFirst() returns true if there are values selected
        if (result.moveToFirst()) {
            // loop through result, create new employee object and add to the return list
            do {
                String dmy = result.getString(0);
                String dow = result.getString(1);
                Date date = new Date(dmy, dow);
                dateList.add(date);
            }
            while (result.moveToNext());
        }
        result.close();
        return dateList;
    }

    public void addOneDate(Date date) {
        database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DATE_ID, date.getDateMonthYear());
        cv.put(DATE_OF_WEEK, date.getDayOfWeek());
        database.insert(DATE_TABLE, null, cv);
    }


}
