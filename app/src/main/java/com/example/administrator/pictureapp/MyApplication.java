package com.example.administrator.pictureapp;

import android.app.Application;
import android.content.Context;

import com.example.administrator.pictureapp.common.ConstantsBmob;

import cn.bmob.v3.Bmob;

/**
 * Created by funnyrun on 2017/5/24.
 */

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        //Bmob 初始化
//        Bmob.initialize(this, "Your Application ID");
        Bmob.initialize(this, ConstantsBmob.APP_ID,"Bmob");
    }

    public static Context getContext(){
        return context;
    }
}
