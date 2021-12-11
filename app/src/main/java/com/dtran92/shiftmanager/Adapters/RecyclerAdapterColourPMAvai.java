package com.dtran92.shiftmanager.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.dtran92.shiftmanager.R;
import com.dtran92.shiftmanager.Employee;

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
        String text = empList.get(position).getName(); // + "\n" + empList.get(position).getEmail();
        String email = empList.get(position).getEmail();
        holder.textView.setText(text);
        holder.email.setText(email);
        ImageView trained_PM = holder.trained_PM;
        ImageView trained_AM = holder.trained_AM;
        if (empList.get(position).getTrained_pm().equals("Y")) {
            trained_PM.setVisibility(View.VISIBLE);
            //cv_emp.setCardBackgroundColor(Color.parseColor("#36508A"));
        }
        if(empList.get(position).getTrained_am().equals("Y")) {
            trained_AM.setVisibility(View.VISIBLE);
        }
        //else {
            //cv_emp.setCardBackgroundColor(Color.parseColor("#FA5C53"));
        //}
    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        TextView email;
        ImageView trained_PM;
        ImageView trained_AM;
        OnEmployeeListener onEmployeeListener;

        public ViewHolder(@NonNull View itemView, OnEmployeeListener onEmployeeListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_custom);
            email = itemView.findViewById(R.id.tv_emp_email);
            trained_PM = itemView.findViewById(R.id.pm_train_img);
            trained_AM = itemView.findViewById(R.id.am_train_img);
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
