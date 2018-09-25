package com.madelene.projectkamus.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.madelene.projectkamus.Entity.Kamus;

import java.util.ArrayList;
import java.util.Arrays;

public final class KamusPreference {


    private SharedPreferences preferences;
    private String PRELOAD_KEY_AVAILABLE = "KEY_PRELOAD";
    public KamusPreference(Context context) {
        String PREFS_NAME = "KamusPref";
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public void setKamus(String kataCari, ArrayList<Kamus> katass) {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        editor.putString(kataCari, gson.toJson(katass));
        editor.apply();
    }

    public ArrayList<Kamus> getKamus(String kataCari) {

        Gson gson = new Gson();
        String data = preferences.getString(kataCari, null);
        ArrayList<Kamus> katas = new ArrayList<>(Arrays.asList(
                gson.fromJson(data, Kamus[].class)));
        return katas;

    }

    public void setPreloadDataSuccess() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PRELOAD_KEY_AVAILABLE, true);
        editor.apply();
    }

    public boolean isPreloadDataAvailable() {
        return preferences.getBoolean(PRELOAD_KEY_AVAILABLE, false);
    }


}