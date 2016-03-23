package com.donneryst.popularmovies.util;

import android.content.Context;

import net.orange_box.storebox.StoreBox;

/**
 * Store/Load SharedPreferences with StoreBox
 *
 * @link https://github.com/martino2k6/StoreBox
 *
 * Author: jhpx
 * Create: 2016/3/23
 */
public class AppPreferencesHelper {

    private static AppPreferencesHelper instance;
    private AppPreferences mAppPrefs;

    public static AppPreferencesHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (AppPreferencesHelper.class) {
                if (instance == null) {
                    instance = new AppPreferencesHelper(context);
                }
            }
        }
        return instance;
    }

    private AppPreferencesHelper(Context context) {
        if (mAppPrefs == null) {
            mAppPrefs = StoreBox.create(context, AppPreferences.class);
        }
    }

    public AppPreferences getAppPreferences(){
        return mAppPrefs;
    }
}
