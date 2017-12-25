package com.handen.sonofsun;

import android.content.Context;

/**
 * Created by Vanya on 26.12.2017.
 */

public class SharedPreferences {
    private static final String APP_NAME = "Son of Sun";

    private static final String DELAY = "DELAY";
    private static final String IS_AUTO = "IS_AUTO";

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

    public void load(Context context) {
        MainActivity.delay = sharedPreferences.getInt(DELAY, 0);
        MainActivity.isAuto = sharedPreferences.getBoolean(IS_AUTO, true);
        MainActivity.maxPower = sharedPreferences.getInt(MAX_POWER, 1000);
        MainActivity.illuminationTime = sharedPreferences.getInt(ILLUMINATION_TIME, 0);

    }



}
