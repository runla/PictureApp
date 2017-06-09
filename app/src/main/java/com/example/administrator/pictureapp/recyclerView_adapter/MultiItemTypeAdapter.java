package com.example.administrator.pictureapp.recyclerView_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.pictureapp.recyclerView_adapter.base.BaseViewHolder;
import com.example.administrator.pictureapp.recyclerView_adapter.base.ItemViewDelegate;
import com.example.administrator.pictureapp.recyclerView_adapter.base.ItemViewDelegateManager;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public class MultiItemTypeAdapter<M> extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context mContext;
    protected List<M> dataList;

    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;


    public MultiItemTypeAdapter(Context context, List<M> datas) {
        mContext = context;
        dataList = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(dataList.get(position), position);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        BaseViewHolder holder = BaseViewHolder.createViewHolder(mContext, parent, layoutId);
        setListener(parent, holder, viewType);
        return holder;
    }


    public void bindData(BaseViewHolder holder, M t) {
        mItemViewDelegateManager.bindData(holder, t, holder.getAdapterPosition());
    }

    /**
     * 设置 rootview 是否可以点击
     * @param viewType
     * @return
     */
    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected void setListener(final ViewGroup parent, final BaseViewHolder baseViewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        baseViewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = baseViewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, baseViewHolder, position);
                }
            }
        });

        baseViewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = baseViewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, baseViewHolder, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, dataList.get(position));
    }

    @Override
    public int getItemCount() {
        int itemCount = dataList.size();
        return itemCount;
    }


    public List<M> getDatas() {
        return dataList;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<M> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<M> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public interface OnItemClickListener {
        /**
         * 监听根布局监听事件
         * @param view
         * @param holder
         * @param position
         */
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        /**
         * 监听根部局长按监听事件
         * @param view
         * @param holder
         * @param position
         * @return
         */
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }



    //=================辅助方法========================

}
