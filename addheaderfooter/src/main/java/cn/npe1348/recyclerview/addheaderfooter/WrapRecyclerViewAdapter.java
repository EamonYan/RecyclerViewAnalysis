package cn.npe1348.recyclerview.addheaderfooter;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.npe1348.recyclerview.comadapter.ViewHolder;

public class WrapRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private RecyclerView.Adapter mAdapter;
    private SparseArray<View> mHeaderViews;
    private SparseArray<View> mFooterViews;

    /**
     * 基本的头部类型开始位置  用于viewType
     */
    private static int HEADER_VIEW_KEY_START = 100000;
    /**
     * 基本的底部类型开始位置  用于viewType
     */
    private static int FOOTER_VIEW_KEY_START = 200000;

    public WrapRecyclerViewAdapter(RecyclerView.Adapter adapter){
        this.mAdapter = adapter;
        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
    }

    public void addHeaderView(View headerView){
        int position = mHeaderViews.indexOfValue(headerView);
        if (position < 0) {
            mHeaderViews.put(HEADER_VIEW_KEY_START++, headerView);
        }
        notifyDataSetChanged();
    }

    public void addFooterView(View footerView){
        int position = mFooterViews.indexOfValue(footerView);
        if (position < 0) {
            mFooterViews.put(FOOTER_VIEW_KEY_START++, footerView);
        }
        notifyDataSetChanged();
    }

    public void removeHeaderView(View headerView){
        int index = mHeaderViews.indexOfValue(headerView);
        if (index>=0){
            mHeaderViews.removeAt(index);
            notifyDataSetChanged();
        }
    }

    public void removeFooterView(View footerView){
        int index = mFooterViews.indexOfValue(footerView);
        if (index>=0){
            mFooterViews.removeAt(index);
            notifyDataSetChanged();
        }
    }

    public int getHeaderViewCount(){
        return mHeaderViews.size();
    }

    public int getFooterViewCount(){
        return mFooterViews.size();
    }

    public boolean isHeaderViewPosition(int position){
        return position>=0&&position<getHeaderViewCount();
    }

    public boolean isFooterViewPosition(int position){
        int footerViewStart = getHeaderViewCount()+mAdapter.getItemCount();
        return position>=footerViewStart&&position<(footerViewStart+getFooterViewCount());
    }

    /**
     * 是不是头部类型
     */
    private boolean isHeaderViewType(int viewType) {
        int position = mHeaderViews.indexOfKey(viewType);
        return position >= 0;
    }

    /**
     * 是不是底部类型
     */
    private boolean isFooterViewType(int viewType) {
        int position = mFooterViews.indexOfKey(viewType);
        return position >= 0;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isHeaderViewType(viewType)){
            View headerView = mHeaderViews.get(viewType);
            return new ViewHolder(headerView);
        }
        if (isFooterViewType(viewType)) {
            View footerView = mFooterViews.get(viewType);
            return new ViewHolder(footerView);
        }
        return (ViewHolder) mAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (isHeaderViewPosition(position) || isFooterViewPosition(position)){
            return ;
        }

        int adjPosition = position - getHeaderViewCount();
        mAdapter.onBindViewHolder(holder,adjPosition);

        if (mItemClickListener != null){
            holder.setOnItemClickListener(mItemClickListener,adjPosition);
        }

        if (mItemLongClickListener != null){
            holder.setOnItemLongClickListener(mItemLongClickListener,adjPosition);
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount()+getHeaderViewCount()+getFooterViewCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPosition(position)){
            return mHeaderViews.keyAt(position);
        }
        if (isFooterViewPosition(position)) {
            return mFooterViews.keyAt(position-mHeaderViews.size()-mAdapter.getItemCount());
        }
        return mAdapter.getItemViewType(position-mHeaderViews.size());
    }

    /***************
     * 给条目设置点击和长按事件
     *********************/
    public ViewHolder.OnItemClickListener mItemClickListener;
    public ViewHolder.OnItemLongClickListener mItemLongClickListener;

    public void setOnItemClickListener(ViewHolder.OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(ViewHolder.OnItemLongClickListener itemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener;
    }

    public void adjustSpanSize(RecyclerView recyclerView){
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter =
                            isHeaderViewPosition(position) || isFooterViewPosition(position);
                    return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
                }
            });
        }
    }
}
