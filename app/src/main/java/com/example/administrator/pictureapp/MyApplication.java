package com.example.administrator.pictureapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by funnyrun on 2017/5/24.
 */

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
    }

    public static Context getContext(){
        return context;
    }
}
