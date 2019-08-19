package io.github.tlaabs.ctt.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.github.tlaabs.ctt.R;

public class SaveManager {
    private static final String KEY = "repo";

    private static SaveManager instance;
    private Context mContext;

    private SaveManager(Context context) {
        this.mContext = context;
    }

    private static SaveManager getInstance(Context context) {
        if (instance == null) return instance = new SaveManager(context.getApplicationContext());
        return instance;
    }

    public String get() {
        SharedPreferences repo = PreferenceManager.getDefaultSharedPreferences(mContext);
        String data = repo.getString(KEY, "");
        return data;
    }

    public void save(String data) {
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(KEY,data);
        editor.apply();
    }
}