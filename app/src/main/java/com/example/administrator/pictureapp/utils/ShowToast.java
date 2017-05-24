package com.example.administrator.pictureapp.utils;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.administrator.pictureapp.MyApplication;

/**
 * Created by fanson on 2016/8/25.
 * Toast类
 */
public class ShowToast {

    public static void Short(@NonNull CharSequence sequence){
        Toast.makeText(MyApplication.getContext(),sequence, Toast.LENGTH_SHORT).show();
    }

    public static void Long(@NonNull CharSequence sequence){
        Toast.makeText(MyApplication.getContext(), sequence, Toast.LENGTH_LONG).show();
    }

}
