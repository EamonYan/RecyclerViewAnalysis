package cn.npe1348.testrecyclerview.headerFooterUse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import cn.npe1348.recyclerview.addheaderfooter.WrapRecyclerView;
import cn.npe1348.recyclerview.comadapter.ViewHolder;
import cn.npe1348.testrecyclerview.R;
import cn.npe1348.testrecyclerview.commonAdapterUse.RecyclerAdapter;
import cn.npe1348.testrecyclerview.http.bean.Data;
import cn.npe1348.testrecyclerview.http.bean.DataRootBean;
import cn.npe1348.testrecyclerview.http.model.DataModel;
import cn.npe1348.testrecyclerview.http.model.IDataModel;

public class HeaderFooterActivity extends AppCompatActivity {
    private WrapRecyclerView mRecyclerView;
    private List<Data> mDatas = new ArrayList<>();
    private RecyclerAdapter mRecyclerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrap_recycler_view);
        initData();
        mRecyclerView = findViewById(R.id.recycler_view);
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerAdapter = new RecyclerAdapter(this,mDatas,mRecyclerView);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerAdapter.setOnItemClickListener(new ViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(HeaderFooterActivity.this, "按下-"+position, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerAdapter.setOnItemLongClickListener(new ViewHolder.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                Toast.makeText(HeaderFooterActivity.this, "长按-"+position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mRecyclerView.addEmptyView(findViewById(R.id.empty_view));
        //mRecyclerView.addLoadingView(findViewById(R.id.loading_view));
        mRecyclerView.addLoadingView(findViewById(R.id.loading_view_rotate));
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_header_view,mRecyclerView,false);
        mRecyclerView.addHeaderView(headerView);
    }

    private void initData() {
        new DataModel().loadData(1, new IDataModel.ILoadDataListener() {
            @Override
            public void onSuccess(DataRootBean dataRootBean) {
                Log.e("CommonAdapterActivity","onSuccess-"+dataRootBean.toString());
                mDatas.clear();
                mDatas.addAll(dataRootBean.getData());
                mRecyclerAdapter.notifyDataSetChanged();
                mRecyclerView.hideLoadView();
            }

            @Override
            public void onFailure(String error) {
                Log.e("CommonAdapterActivity","onFailure-"+error);
            }
        });
    }
}
