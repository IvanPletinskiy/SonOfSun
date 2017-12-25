package com.handen.sonofsun;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ControlFragment controlFragment;
    private SettingsFragment settingsFragment;
    static Date sunrise_begin = new Date (0, 0, 0, 6, 0);
    static Date sunrise_end = new Date (0, 0, 0, 7, 0);
    static Date sunset_begin = new Date (0, 0, 0, 21, 0);
    static Date sun_set_end = new Date (0, 0, 0, 2, 0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controlFragment = ControlFragment.newInstance();
        settingsFragment = SettingsFragment.newInstance();
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    default:
                        return controlFragment;
                    case 1:
                        return settingsFragment;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();

                try {
                    sleep(10000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
}
