package com.dtran92.shiftmanager.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dtran92.shiftmanager.Main_Calendar;
import com.dtran92.shiftmanager.Main_Employee;

public class Main_ViewPagerAdapter extends FragmentStateAdapter {
    public Main_ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new Main_Employee();
            default:
                return new Main_Calendar();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
