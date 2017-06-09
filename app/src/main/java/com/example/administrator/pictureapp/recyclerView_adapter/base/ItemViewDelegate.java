package com.example.administrator.pictureapp.recyclerView_adapter.base;


/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>
{

    /**
     * 获取布局 id
     * @return
     */
    int getItemViewLayoutId();

    /**
     * 多种类型的布局时，根据 item 进行类型判断
     * @param item
     * @param position
     * @return
     */
    boolean isForViewType(T item, int position);

    /**
     * 控件的数据显示、时间监听都在这里做
     * @param holder
     * @param t
     * @param position
     */
    void bindData(BaseViewHolder holder, T t, int position);

}
