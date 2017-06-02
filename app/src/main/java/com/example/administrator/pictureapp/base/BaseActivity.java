package com.example.administrator.pictureapp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by chenjianrun on 2017/6/1.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    public static List<BaseActivity> activitieList = new ArrayList<>();

    private FragmentManager fragmentManager = this.getSupportFragmentManager();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getContentView());
        activitieList.add(this);
        EventBus.getDefault().register(this);
        this.initParms(savedInstanceState);
        this.initView();
        this.initData();
        this.initEvent();
    }

    protected abstract int getContentView();

    //布局中Fragment的ID
    protected abstract int getFragmentContentId();
    /**
     * [初始化参数] 在initView 之前调用
     * @param parms
     */
    public abstract void initParms(Bundle parms);

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    /**
     * 关闭所有的 activity
     */
    protected void finishAll(){
        for (BaseActivity activity : activitieList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


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


    protected void replaceFragmentAnim(Fragment fragment, int enter, int exit) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.setCustomAnimations(enter, exit);
        transaction.replace(getFragmentContentId(), fragment);
        transaction.commit();
    }

    protected void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName());
        transaction.commit();
    }

    protected void replaceFragmentToStack(Fragment fragment) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    protected void addFragmentWithAnim(Fragment fragment, int enter, int exit) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.setCustomAnimations(enter, exit);
        transaction.add(getFragmentContentId(), fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    protected void addFragmentWith(Fragment fragment) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.add(getFragmentContentId(), fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }



    /**
     * 如果 fragment 已经添加到 activity 中，则将其显示
     * @param fragment
     */
    protected void showFragment(Fragment fragment) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        if(fragment.isAdded()) {
            transaction.show(fragment).commit();
        }
    }

    /**
     * 移除一个 fragment
     * @param fragment
     */
    protected void removeFragment(Fragment fragment) {
        if(this.fragmentManager != null && fragment != null) {
            this.fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * 根据 tag 查找一个 fragment
     * @param tag
     * @return
     */
    public Fragment findFragmentByTag(String tag) {
        return this.fragmentManager != null?this.fragmentManager.findFragmentByTag(tag):null;
    }


    /**
     * 根据 tag 移除 fragment
     * @param tag
     */
    protected void removeFragment(String tag) {
        if(this.fragmentManager != null) {
            this.removeFragment(this.fragmentManager.findFragmentByTag(tag));
            this.fragmentManager.popBackStackImmediate(tag, 1);
        }

    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

}
