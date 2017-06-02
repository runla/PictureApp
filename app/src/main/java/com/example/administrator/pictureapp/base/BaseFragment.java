package com.example.administrator.pictureapp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.greenrobot.event.EventBus;

/**
 * Created by funnyrun on 2017/6/2.
 */

public abstract class BaseFragment extends Fragment{

    protected Activity hostActivity;
    protected FragmentCallback fragmentCallback;
    protected View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.hostActivity = (Activity) context;
        try {
            fragmentCallback = (FragmentCallback) hostActivity;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentCallback");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        initView(rootView, savedInstanceState);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null && !isHidden()) {
            initData();
            initEvent();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        SampleApplicationLike.getRefWatcher(getActivity()).watch(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
        hostActivity = null;
    }

    protected abstract int getLayoutId();
    /**
     * onCreateView中初始化View
     *
     * @param rootView
     * @param savedInstanceState
     */
    protected abstract View initView(View rootView, Bundle savedInstanceState);

    protected abstract void initEvent();

    protected abstract void initData();

    /**
     * fragment 和 Activity 通信的接口
     */
    public interface FragmentCallback{
        void fragmentResponse(int msgCode,Object msg);
    }
}
