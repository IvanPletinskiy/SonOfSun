package com.handen.sonofsun;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    public static int delay;
    public static boolean isAuto = false;
    static Date  sunriseBegin;
    static Date sunriseEnd;
    static Date sunsetBegin;
    static Date sunsetEnd;
    static int illuminationTime;
    static boolean isWeekend = false;
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

        Thread scheduler = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Date currentDate = new Date();

                    Calendar calendar = new GregorianCalendar();
                    int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
                    if(currentDay == 1 || currentDay == 7){
                        sleep(1000 * 60 * 30);
                        return;
                    }

                    if (currentDate.after(sunriseBegin) && currentDate.before(sunriseEnd)) {
                        //sendCommand
                        int seconds = (int)(sunriseEnd.getTime() / 60 - sunriseBegin.getTime() / 60);
                        controlFragment.sendCommand(0, 0, MainActivity.maxPower, seconds);
                        sleep(sunriseEnd.getTime() - sunriseBegin.getTime());
         //               sleep();
                    }
                    if (currentDate.after(sunsetBegin) && currentDate.before(sunsetEnd)) {
                        //sendCommand
                        int seconds = (int)(sunsetEnd.getTime() / 60 - sunsetBegin.getTime() / 60);
                        controlFragment.sendCommand(0, 0, MainActivity.maxPower, seconds);
                        sleep(sunsetEnd.getTime() - sunsetBegin.getTime());
                    }

                    sleep(30000);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        scheduler.start();

    }
}
