package cn.npe1348.recyclerview.addheaderfooter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import cn.npe1348.libraryloading.Loading58ProgressBarView;
import cn.npe1348.libraryloading.RotateLoadingProgressBarView;

public class WrapRecyclerView extends RecyclerView {
    private RecyclerView.Adapter mAdapter;
    private WrapRecyclerViewAdapter mWrapRecyclerViewAdapter;

    // 增加一些通用功能
    // 空列表数据应该显示的空View
    // 正在加载数据页面，也就是正在获取后台接口页面
    private View mEmptyView, mLoadingView;

    public WrapRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyDataSetChanged没效果
            if (mWrapRecyclerViewAdapter != mAdapter)
                mWrapRecyclerViewAdapter.notifyDataSetChanged();

            dataChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyDataSetChanged没效果
            if (mWrapRecyclerViewAdapter != mAdapter)
                mWrapRecyclerViewAdapter.notifyItemRemoved(positionStart);
            dataChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemMoved没效果
            if (mWrapRecyclerViewAdapter != mAdapter)
                mWrapRecyclerViewAdapter.notifyItemMoved(fromPosition, toPosition);
            dataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemChanged没效果
            if (mWrapRecyclerViewAdapter != mAdapter)
                mWrapRecyclerViewAdapter.notifyItemChanged(positionStart);
            dataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemChanged没效果
            if (mWrapRecyclerViewAdapter != mAdapter)
                mWrapRecyclerViewAdapter.notifyItemChanged(positionStart, payload);
            dataChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemInserted没效果
            if (mWrapRecyclerViewAdapter != mAdapter)
                mWrapRecyclerViewAdapter.notifyItemInserted(positionStart);
            dataChanged();
        }
    };

    public void addHeaderView(View headerView){
        if (mWrapRecyclerViewAdapter != null){
            mWrapRecyclerViewAdapter.addHeaderView(headerView);
        }
    }

    public void addFooterView(View footerView){
        if (mWrapRecyclerViewAdapter != null){
            mWrapRecyclerViewAdapter.addFooterView(footerView);
        }
    }

    public void removeHeaderView(View headerView){
        if (mWrapRecyclerViewAdapter != null){
            mWrapRecyclerViewAdapter.removeHeaderView(headerView);
        }
    }

    public void removeFooterView(View footerView){
        if (mWrapRecyclerViewAdapter != null){
            mWrapRecyclerViewAdapter.removeFooterView(footerView);
        }
    }

    public void setAdapter(Adapter adapter){
        // 为了防止多次设置Adapter
        if (mAdapter != null){
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
            mAdapter = null;
        }

        mAdapter = adapter;

        if (adapter instanceof WrapRecyclerViewAdapter) {
            mWrapRecyclerViewAdapter = (WrapRecyclerViewAdapter) adapter;
        } else {
            mWrapRecyclerViewAdapter = new WrapRecyclerViewAdapter(mAdapter);
        }
        super.setAdapter(mWrapRecyclerViewAdapter);

        mAdapter.registerAdapterDataObserver(mDataObserver);

        mWrapRecyclerViewAdapter.adjustSpanSize(this);

        if (mItemClickListener != null) {
            mWrapRecyclerViewAdapter.setOnItemClickListener(mItemClickListener);
        }

        if (mItemLongClickListener != null) {
            mWrapRecyclerViewAdapter.setOnItemLongClickListener(mItemLongClickListener);
        }
    }

    /**
     * 添加一个空列表数据页面
     */
    public void addEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
    }
    /**
     * 添加一个正在加载数据的页面
     */
    public void addLoadingView(View loadingView) {
        this.mLoadingView = loadingView;
        mLoadingView.setVisibility(View.VISIBLE);
        if (mLoadingView instanceof Loading58ProgressBarView){
            ((Loading58ProgressBarView)mLoadingView).startLoading();
        }
        if (mLoadingView instanceof RotateLoadingProgressBarView){
            ((RotateLoadingProgressBarView)mLoadingView).startLoading();
        }

    }

    public void hideLoadView(){
        if (mLoadingView != null){
            mLoadingView.setVisibility(View.GONE);
            if (mLoadingView instanceof Loading58ProgressBarView){
                ((Loading58ProgressBarView)mLoadingView).stopLoading();
            }
            if (mLoadingView instanceof RotateLoadingProgressBarView){
                ((RotateLoadingProgressBarView)mLoadingView).stopLoading();
            }
        }
    }

    /**
     * Adapter数据改变的方法
     */
    private void dataChanged() {
        if (mAdapter.getItemCount() == 0) {
            // 没有数据
            if (mEmptyView != null) {
                mEmptyView.setVisibility(VISIBLE);
            }
        }else{
            // 有数据
            if (mEmptyView != null) {
                mEmptyView.setVisibility(GONE);
            }
        }
    }



    /***************
     * 给条目设置点击和长按事件
     *********************/
    public cn.npe1348.recyclerview.comadapter.ViewHolder.OnItemClickListener mItemClickListener;
    public cn.npe1348.recyclerview.comadapter.ViewHolder.OnItemLongClickListener mItemLongClickListener;

    public void setOnItemClickListener(cn.npe1348.recyclerview.comadapter.ViewHolder.OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;

        if (mWrapRecyclerViewAdapter != null) {
            mWrapRecyclerViewAdapter.setOnItemClickListener(mItemClickListener);
        }
    }

    public void setOnLongClickListener(cn.npe1348.recyclerview.comadapter.ViewHolder.OnItemLongClickListener itemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener;

        if (mWrapRecyclerViewAdapter != null) {
            mWrapRecyclerViewAdapter.setOnItemLongClickListener(mItemLongClickListener);
        }
    }
}
