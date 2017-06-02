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
        initParms(savedInstanceState);
        EventBus.getDefault().register(this);

        this.initView();
        this.initData();
        this.initEvent();
    }

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


    protected void replaceFragment(int id_content, Fragment fragment, int enter, int exit) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.setCustomAnimations(enter, exit);
        transaction.replace(id_content, fragment);
        transaction.commit();
    }

    protected void replaceFragment(int id_content, Fragment fragment) {
        this.replaceFragmentWithTag(id_content, fragment, (String)null);
    }

    protected void replaceFragmentWithTag(int id_content, Fragment fragment, String tag) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.replace(id_content, fragment, tag);
        transaction.commit();
    }

    protected void replaceFragmentToStack(int id_content, Fragment fragment, String tag) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.replace(id_content, fragment, tag).addToBackStack((String)null);
        transaction.commit();
    }

    protected void addFragmentWithTagAnim(int id_content, Fragment fragment, int enter, int exit, String tag) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.setCustomAnimations(enter, exit);
        transaction.add(id_content, fragment, tag);
        transaction.commit();
    }

    protected void addFragmentWithTag(int id_content, Fragment fragment, String tag) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.add(id_content, fragment, tag);
        transaction.commit();
    }

    /**
     * 切换 fragment
     * @param id_content
     * @param fromFragment
     * @param toFragment
     */
    protected void switchFragment(int id_content, Fragment fromFragment, Fragment toFragment) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        if(toFragment.isAdded()) {
            transaction.hide(fromFragment).show(toFragment).commit();
        } else {
            transaction.hide(fromFragment).add(id_content, toFragment).commit();
        }
    }


    /**
     * 切换 fragment，添加 tag
     * @param id_content
     * @param fromFragment
     * @param toFragment
     * @param tagOfTo
     */
    protected void switchFragmentWithTag(int id_content, Fragment fromFragment, Fragment toFragment, String tagOfTo) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        if(toFragment.isAdded()) {
            transaction.hide(fromFragment).show(toFragment).commit();
        } else {
            transaction.hide(fromFragment).add(id_content, toFragment, tagOfTo).commit();
        }
    }

    /**
     * 切换 fragment，有动画的
     * @param id_content
     * @param fromFragment
     * @param toFragment
     * @param enter
     * @param eixt
     */
    protected void switchFragmentWithAnim(int id_content, Fragment fromFragment, Fragment toFragment, int enter, int eixt) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        if(toFragment.isAdded()) {
            transaction.hide(fromFragment).setCustomAnimations(enter, eixt).show(toFragment).commit();
        } else {
            transaction.hide(fromFragment).setCustomAnimations(enter, eixt).add(id_content, toFragment).commit();
        }
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
}
