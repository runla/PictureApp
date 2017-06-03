package com.example.administrator.pictureapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by funnyrun on 2017/6/3.
 * 描述： MVP 中的 V 层，这是基于 fragment 的 view
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements BaseView {
    protected P mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter = attachPresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    /**
     * 实例化Presenter
     * @return
     */
    protected abstract P attachPresenter();
}
