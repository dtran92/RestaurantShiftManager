package com.f21ritchie.shiftscheduler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.f21ritchie.shiftscheduler.CalendarUtils.*;

import com.example.shiftscheduler.R;
import com.f21ritchie.shiftscheduler.Adapters.CalendarAdapter;

public class Main_Calendar extends Fragment implements CalendarAdapter.OnItemListener, View.OnClickListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    Context thiscontext;
    Button bt_backward, bt_forward, bt_viewV2;
    Database database;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ArrayList<LocalDate> daysInMonth;
    CalendarAdapter calendarAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_calendar, container, false);
        calendarRecyclerView = (RecyclerView) view.findViewById(R.id.rv_calendarCal);
        monthYearText = (TextView) view.findViewById(R.id.tv_monthyear);
        bt_backward = (Button) view.findViewById(R.id.bt_backCal);
        bt_forward = (Button) view.findViewById(R.id.bt_forCal);
        bt_backward.setOnClickListener(this);
        bt_forward.setOnClickListener(this);

        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK)
                {
                    calendarRecyclerView.setAdapter(calendarAdapter);
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_backCal:
                previousMonthAction();
                break;
            case R.id.bt_forCal:
                nextMonthAction();
                break;
        }
    }

    // Click to open new activity to view shifts for selected date
    @Override
    public void onItemClick(int position, LocalDate date) {

        if (date == null) {}
        else {
        CalendarUtils.selectedDate = date;
        Intent intent = new Intent(getActivity(), ShiftViewActivity.class);
        activityResultLauncher.launch(intent);}
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thiscontext = context;
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);
        calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(thiscontext, 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }
    public void previousMonthAction() {
        try {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();}
        catch (NullPointerException exception) {}
    }

    public void nextMonthAction() {
        try {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();}
        catch (NullPointerException exception) {}
    }

}
