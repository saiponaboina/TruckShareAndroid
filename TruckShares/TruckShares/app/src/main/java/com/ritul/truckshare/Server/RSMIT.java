package com.ritul.truckshare.Server;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.ritul.truckshare.Pojo.DriverInformation;
import com.ritul.truckshare.Pojo.Insurence;
import com.ritul.truckshare.Pojo.TruckDetail;
import com.ritul.truckshare.Pojo.TruckImages;
import com.ritul.truckshare.Pojo.UserInformation;
import java.lang.reflect.Type;

/**
 * Created by vipulm on 07-Dec-15.
 */
public class RSMIT extends Application {
    public static RSMIT mInstance;
    private String USER = "USER";
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MultiDex.install(this);
    }
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized RSMIT getInstance() {
        return mInstance;
    }

}