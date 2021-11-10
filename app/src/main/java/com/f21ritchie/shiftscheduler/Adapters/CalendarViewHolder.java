package com.f21ritchie.shiftscheduler.Adapters;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shiftscheduler.R;
import com.f21ritchie.shiftscheduler.Adapters.CalendarAdapter;
import com.f21ritchie.shiftscheduler.Database;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView dayOfMonth;
    private final CalendarAdapter.OnItemListener onItemListener;
    Database database;
    ImageView imageView;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days)
    {
        super(itemView);
        parentView = itemView.findViewById(R.id.cl_parentView);
        dayOfMonth = itemView.findViewById(R.id.tv_cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
        database = new Database(itemView.getContext());
        imageView = itemView.findViewById(R.id.imageView_check);
    }

    @Override
    public void onClick(View view)
    {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}
