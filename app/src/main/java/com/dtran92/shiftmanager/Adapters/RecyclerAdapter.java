package com.dtran92.shiftmanager.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtran92.shiftmanager.R;
import com.dtran92.shiftmanager.Employee;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Employee> empList;
    private OnEmployeeListener onEmployeeListener;

    public RecyclerAdapter(List<Employee> empList) {
        this.empList = empList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        String text = empList.get(position).getName();
        String email = empList.get(position).getEmail(); //  + "\n" + empList.get(position).getEmail()
        ImageView trained_AM = holder.trained_AM;
        ImageView trained_PM = holder.trained_PM;
        holder.textView.setText(text);
        holder.emailTV.setText(email);
        if(empList.get(position).getTrained_am().equals("Y")) {
            trained_AM.setVisibility(View.VISIBLE);
        }
        if(empList.get(position).getTrained_pm().equals("Y")) {
            trained_PM.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        TextView emailTV;
        ImageView trained_AM;
        ImageView trained_PM;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_custom);
            emailTV = itemView.findViewById(R.id.tv_emp_email);
            trained_AM = itemView.findViewById(R.id.am_train_img);
            trained_PM = itemView.findViewById(R.id.pm_train_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onEmployeeListener.onEmployeeClick(getAdapterPosition());
        }
    }

    public void setOnEmployeeListener(OnEmployeeListener onEmployeeListener) {
        this.onEmployeeListener = onEmployeeListener;
    }

    public void setEmpList(List<Employee> empList) {
        this.empList = empList;
    }

    public interface OnEmployeeListener{
        void onEmployeeClick(int position);
    }
}
