package com.example.administrator.pictureapp.utils;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/5/24.
 */

public class MyOkHttp {
    private volatile static MyOkHttp myOkHttp;
    private OkHttpClient okHttpClient;

    private MyOkHttp(){
        okHttpClient = new OkHttpClient();
    }

    /**
     * 单例--双重检查锁定
     * @return
     */
    public static MyOkHttp getInstance(){
        if (myOkHttp == null) {
            synchronized (MyOkHttp.myOkHttp){
                if (myOkHttp == null){
                    myOkHttp = new MyOkHttp();
                }
            }
        }
        return myOkHttp;
    }
}
