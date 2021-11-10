package com.f21ritchie.shiftscheduler.Adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.f21ritchie.shiftscheduler.Employee;
import com.example.shiftscheduler.R;

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
        String text = empList.get(position).getName() + "\n" + empList.get(position).getEmail();
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_custom);
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
