package cn.npe1348.recyclerview.comadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class RecyclerCommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private List<T> mDatas;
    private int mLayoutId;

    private LayoutInflater mLayoutInflater;

    public RecyclerCommonAdapter(Context mContext, List<T> mDatas, int mLayoutId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mLayoutId = mLayoutId;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView = mLayoutInflater.inflate(mLayoutId,parent,false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        bindView(holder,mDatas.get(position),position);

        if (mOnItemClickListener != null){
            holder.setOnItemClickListener(mOnItemClickListener,position);
        }

        if (mOnItemLongClickListener != null){
            holder.setOnItemLongClickListener(mOnItemLongClickListener,position);
        }
    }

    public abstract void bindView(ViewHolder holder,T item,int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private ViewHolder.OnItemClickListener mOnItemClickListener;

    private ViewHolder.OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(ViewHolder.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(ViewHolder.OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener = onItemLongClickListener;
    }
}
