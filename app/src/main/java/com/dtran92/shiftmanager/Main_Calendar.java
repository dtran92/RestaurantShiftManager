package com.dtran92.shiftmanager;

import static com.dtran92.shiftmanager.CalendarUtils.daysInMonthArray;
import static com.dtran92.shiftmanager.CalendarUtils.monthYearFromDate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.dtran92.shiftmanager.Adapters.CalendarAdapter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main_Calendar extends Fragment implements CalendarAdapter.OnItemListener, View.OnClickListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    Context thiscontext;
    static CalendarAdapter calendarAdapter;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ArrayList<LocalDate> daysInMonth;
    Button bt_backward, bt_forward;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    calendarAdapter.notifyDataSetChanged();
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

        // weekend
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            activityResultLauncher.launch(new Intent(getActivity(), ShiftViewActivityWeekend.class));
        }
        //weekday
        else {
            activityResultLauncher.launch(new Intent(getActivity(), ShiftViewActivity.class));
        }}
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
