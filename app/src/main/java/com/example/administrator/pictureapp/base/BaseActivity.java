package com.example.administrator.pictureapp.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.greenrobot.event.EventBus;

/**
 * Created by chenjianrun on 2017/6/1.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getContentView());
        initParms(savedInstanceState);
        EventBus.getDefault().register(this);

        this.initView();
        this.initData();
        this.initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected abstract int getContentView();

    /**
     * [初始化参数] 在initView 之前调用
     * @param parms
     */
    public abstract void initParms(Bundle parms);

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    /**
     * 跳转到指定的Activity
     *
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivity(@NonNull Class<?> targetActivity) {
        startActivity(new Intent(this, targetActivity));
    }


    /**
     * [携带数据的页面跳转]
     *
     * @param targetActivity
     * @param bundle
     */
    public void startActivity(Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param targetActivity
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> targetActivity, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * [绑定控件]
     * @param resId
     * @return
     */
    protected <T extends View> T getView(int resId) {
        return (T) super.findViewById(resId);
    }
}
