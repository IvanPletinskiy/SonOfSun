package com.handen.sonofsun;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    public static int delay;
    public static boolean isAuto = false;
    static Date  sunriseBegin;
    static Date sunriseEnd;
    static Date sunsetBegin;
    static Date sunsetEnd;
    static int illuminationTime;
    static boolean isWeekend = true;
    static int maxPower;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("H.mm");
    private ViewPager mViewPager;
    private ControlFragment controlFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controlFragment = ControlFragment.newInstance();
        settingsFragment = SettingsFragment.newInstance();
        try {
            SharedPreferences.getInstance(this).load();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
