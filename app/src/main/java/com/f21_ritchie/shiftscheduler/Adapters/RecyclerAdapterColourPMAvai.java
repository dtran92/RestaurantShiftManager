package com.f21_ritchie.shiftscheduler.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.f21_ritchie.shiftscheduler.R;
import com.f21_ritchie.shiftscheduler.Employee;

import java.util.List;

public class RecyclerAdapterColourPMAvai extends RecyclerView.Adapter<RecyclerAdapterColourPMAvai.ViewHolder> {
    private List<Employee> empList;
    private OnEmployeeListener onEmployeeListener;
    CardView cv_emp;

    public RecyclerAdapterColourPMAvai(List<Employee> empList, OnEmployeeListener onEmployeeListener) {
        this.empList = empList;
        this.onEmployeeListener = onEmployeeListener;
    }

    @NonNull
    @Override
    public RecyclerAdapterColourPMAvai.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, onEmployeeListener);
        cv_emp = view.findViewById(R.id.cv_emp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterColourPMAvai.ViewHolder holder, int position) {
        String text = empList.get(position).getName() + "\n" + empList.get(position).getEmail();
        holder.textView.setText(text);
        if (empList.get(position).getTrained_pm().equals("Y")) {
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

    public interface OnEmployeeListener{
        void onEmployeeClickAvai(int position);
    }
}
