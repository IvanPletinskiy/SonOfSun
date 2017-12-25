package com.handen.sonofsun;

import android.content.Context;

/**
 * Created by Vanya on 26.12.2017.
 */

public class SharedPreferences {

    private static final String APP_NAME = "Son of Sun";

    private static android.content.SharedPreferences sharedPreferences;

    public static SharedPreferences getInstance(Context context) {
        if(sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return new SharedPreferences();
    }
    private SharedPreferences() {

    }





}
