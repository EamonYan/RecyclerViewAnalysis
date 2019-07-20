package cn.npe1348.testrecyclerview.commonAdapterUse;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.npe1348.recyclerview.comadapter.RecyclerCommonAdapter;
import cn.npe1348.recyclerview.comadapter.ViewHolder;
import cn.npe1348.testrecyclerview.R;
import cn.npe1348.testrecyclerview.http.bean.Data;

public class RecyclerAdapter extends RecyclerCommonAdapter<Data> {
    private RecyclerView mRecyclerView;

    public RecyclerAdapter(Context mContext, List<Data> mDatas,RecyclerView recyclerView) {
        super(mContext, mDatas, R.layout.item_layout);
        mRecyclerView = recyclerView;

    }

    @Override
    public void bindView(ViewHolder holder, Data item, int position) {
        holder.setText(R.id.text,item.getText()).setText(R.id.text2,"from:"+item.getName()+" "+item.getCreated_at()).setImageByUrl(R.id.image,new GlideImageLoader(item.getProfile_image()));
        if (null != mRecyclerView && mRecyclerView.getLayoutManager() instanceof GridLayoutManager){
            holder.setVisibility(R.id.text, View.GONE).setVisibility(R.id.text2, View.GONE);
        }
    }
}
