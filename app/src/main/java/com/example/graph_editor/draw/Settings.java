package com.example.graph_editor.draw;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

public class Settings {

    public static void setStylus(Context context, boolean checked) {
        SharedPreferences sharedPref = context.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("settingsStylus", checked);
        editor.apply();
    }
//    public static void setDarkTheme(Context context, boolean checked) {
//        SharedPreferences sharedPref = context.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putBoolean("settingsDarkTheme", checked);
//        editor.apply();
//    }

    public static void setFixedWidth(Context context, boolean checked) {
        SharedPreferences sharedPref = context.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("settingsFixedWidth", checked);
        editor.apply();
    }


    public static boolean getStylus(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("settingsStylus", false);
    }
//    public static boolean getDarkTheme(Context context) {
//        SharedPreferences sharedPref = context.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
//        return sharedPref.getBoolean("settingsDarkTheme", false);
//    }
    public static boolean getFixedWidth(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("settingsFixedWidth", false);
    }
}
