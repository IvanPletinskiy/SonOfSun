package com.handen.sonofsun;

import android.content.Context;

import java.util.Date;

/**
 * Created by Vanya on 26.12.2017.
 */

public class SharedPreferences {
    private static final String APP_NAME = "Son of Sun";

    private static final String DELAY = "DELAY";
    private static final String IS_AUTO = "IS_AUTO";
    private static final String IS_WEEKEND = "IS_WEEKEND";
    private static final String MAX_POWER = "MAX_POWER";
    private static final String ILLUMINATION_TIME = "IT";
    private static final String SUNRISE_BEGIN = "SRB";
    private static final String SUNRISE_END = "SRE";
    private static final String SUNSET_BEGIN = "SSB";
    private static final String SUNSET_END = "SSE";

    private static android.content.SharedPreferences sharedPreferences;


    public static SharedPreferences getInstance(Context context) {
        if(sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return new SharedPreferences();
    }
    private SharedPreferences() {

    }

    public void load() throws Exception{
        MainActivity.delay = sharedPreferences.getInt(DELAY, 0);
        MainActivity.isAuto = sharedPreferences.getBoolean(IS_AUTO, true);
        MainActivity.maxPower = sharedPreferences.getInt(MAX_POWER, 1000);
        MainActivity.illuminationTime = sharedPreferences.getInt(ILLUMINATION_TIME, 0);
        MainActivity.isWeekend = sharedPreferences.getBoolean(IS_WEEKEND, true);
        MainActivity.sunriseBegin = new Date(sharedPreferences.getLong(SUNRISE_BEGIN, MainActivity.dateFormat.parse("6.00").getTime()));
        MainActivity.sunriseEnd = new Date(sharedPreferences.getLong(SUNRISE_END, MainActivity.dateFormat.parse("7.00").getTime()));
        MainActivity.sunsetBegin = new Date(sharedPreferences.getLong(SUNSET_BEGIN, MainActivity.dateFormat.parse("21.00").getTime()));
        MainActivity.sunsetEnd = new Date(sharedPreferences.getLong(SUNSET_END, MainActivity.dateFormat.parse("22.00").getTime()));
    }
    public void save() {
        android.content.SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt(DELAY, MainActivity.delay);
        prefsEditor.putBoolean(IS_AUTO, MainActivity.isAuto);
        prefsEditor.putInt(MAX_POWER, MainActivity.maxPower);
        prefsEditor.putInt(ILLUMINATION_TIME, MainActivity.illuminationTime);
        prefsEditor.putBoolean(IS_WEEKEND, MainActivity.isWeekend);
        prefsEditor.putLong(SUNRISE_BEGIN, MainActivity.sunriseBegin.getTime());
        prefsEditor.putLong(SUNRISE_END, MainActivity.sunriseEnd.getTime());
        prefsEditor.putLong(SUNSET_BEGIN, MainActivity.sunsetBegin.getTime());
        prefsEditor.putLong(SUNSET_END, MainActivity.sunsetEnd.getTime());

        prefsEditor.commit();
    }



}
