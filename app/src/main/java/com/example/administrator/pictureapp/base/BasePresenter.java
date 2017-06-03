package com.example.administrator.pictureapp.base;

/**
 * Created by funnyrun on 2017/6/3.
 * 描述：MVP 中 P 层的基础类
 */

public class BasePresenter<V extends BaseView> {
    protected V mBaseView;

    /**
     *  presenter 关联 View （该方法应该要在子类的构造方法中进行调用
     * @param _baseView
     */
    protected void attachView(V _baseView){
        this.mBaseView = _baseView;
    }

    /**
     * presenter 取消关联 View
     */
    protected void detachView(){
        if (mBaseView != null) {
            mBaseView = null;
        }
    }

    /**
     * 判断 presenter 是否已经关联了 View
     * @return
     */
    public boolean isViewAttached() {
        return mBaseView != null;
    }

}
