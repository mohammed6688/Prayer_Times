package com.mocomp.prayeralert;


import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private static SharedPreferences _preferences;
    private static Application _instance;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }



    public static SharedPreferences getSharedPreferences() {
        if (_preferences == null)
            _preferences = PreferenceManager.getDefaultSharedPreferences(_instance);
        return _preferences;
    }

    public static void setPreferences(String key, String value) {
        getSharedPreferences().edit().putString(key, value).commit();
    }

    public static void setPreferences(String key, long value) {
        getSharedPreferences().edit().putLong(key, value).commit();
    }

    public static void setPreferences(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static void setPreferencesBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).commit();
    }


    //get methods
    public static String getPrefranceData(String key) {
        return getSharedPreferences().getString(key, "");
    }

    public static int getPrefranceDataInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    public static boolean getPrefranceDataBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    public static long getPrefranceDataLong(String interval) {
        return getSharedPreferences().getLong(interval, 0);
    }
}
