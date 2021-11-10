package com.f21ritchie.shiftscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.shiftscheduler.R;
import com.f21ritchie.shiftscheduler.Adapters.Main_ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tl_main);
        ViewPager2 viewPager2 = findViewById(R.id.vp2_main);

        database = new Database(this);

        Main_ViewPagerAdapter adapter = new Main_ViewPagerAdapter(this);

        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Calendar");
                        break;
                    case 1:
                        tab.setText("Employees");
                        break;
                }
            }
        }).attach();
    }
}