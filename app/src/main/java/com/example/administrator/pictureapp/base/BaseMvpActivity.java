package com.example.administrator.pictureapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by funnyrun on 2017/6/3.
 * MVP 中的 V 层，基于 Activity 的 view 层
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView{
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = attachPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    protected abstract P attachPresenter();
}
