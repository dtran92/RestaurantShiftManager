
package com.f21ritchie.shiftscheduler.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.f21ritchie.shiftscheduler.Employee;
import com.example.shiftscheduler.R;

import java.util.List;

public class RecyclerAdapterAvaiWeekend extends RecyclerView.Adapter<RecyclerAdapterAvaiWeekend.ViewHolder> {
    private List<Employee> empList;
    private OnEmployeeListener onEmployeeListener;
    CardView cv_emp;

    public RecyclerAdapterAvaiWeekend(List<Employee> empList, OnEmployeeListener onEmployeeListener) {
        this.empList = empList;
        this.onEmployeeListener = onEmployeeListener;
    }

    @NonNull
    @Override
    public RecyclerAdapterAvaiWeekend.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, onEmployeeListener);
        cv_emp = view.findViewById(R.id.cv_emp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterAvaiWeekend.ViewHolder holder, int position) {
        String text = empList.get(position).getName() + "\n" + empList.get(position).getEmail();
        holder.textView.setText(text);
        if (empList.get(position).getTrained_am().equals("Y") && empList.get(position).getTrained_pm().equals("Y")) {
            cv_emp.setCardBackgroundColor(Color.parseColor("#FFB5B5"));
        }
        else {
            cv_emp.setCardBackgroundColor(Color.parseColor("#A2D1E6"));
        }
    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        OnEmployeeListener onEmployeeListener;

        public ViewHolder(@NonNull View itemView, OnEmployeeListener onEmployeeListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_custom);
            this.onEmployeeListener = onEmployeeListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onEmployeeListener != null) onEmployeeListener.onEmployeeClickAvai(getAdapterPosition());
        }
    }

    public void setEmpList(List<Employee> empList) {
        this.empList = empList;
    }

    public interface OnEmployeeListener{
        void onEmployeeClickAvai(int position);
    }
}