package cn.npe1348.testrecyclerview.refreshLoadUse;

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

import cn.npe1348.recyclerview.comadapter.ViewHolder;
import cn.npe1348.recyclerview.loadrefresh.LoadRefreshRecyclerView;
import cn.npe1348.recyclerview.loadrefresh.RefreshRecyclerView;
import cn.npe1348.testrecyclerview.R;
import cn.npe1348.testrecyclerview.commonAdapterUse.RecyclerAdapter;
import cn.npe1348.testrecyclerview.http.bean.Data;
import cn.npe1348.testrecyclerview.http.bean.DataRootBean;
import cn.npe1348.testrecyclerview.http.model.DataModel;
import cn.npe1348.testrecyclerview.http.model.IDataModel;
import cn.npe1348.testrecyclerview.refreshLoadUse.tool.DefaultLoadCreator;
import cn.npe1348.testrecyclerview.refreshLoadUse.tool.DefaultRefreshCreator;

public class RefreshLoadActivity extends AppCompatActivity {
    private LoadRefreshRecyclerView mRecyclerView;
    private List<Data> mDatas = new ArrayList<>();
    private RecyclerAdapter mRecyclerAdapter;

    private int page = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_refresh_recycler_view);
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
                Toast.makeText(RefreshLoadActivity.this, "按下-"+position, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerAdapter.setOnItemLongClickListener(new ViewHolder.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                Toast.makeText(RefreshLoadActivity.this, "长按-"+position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mRecyclerView.addEmptyView(findViewById(R.id.empty_view));
        //mRecyclerView.addLoadingView(findViewById(R.id.loading_view));
        mRecyclerView.addLoadingView(findViewById(R.id.loading_view_rotate));
        mRecyclerView.addRefreshViewCreator(new DefaultRefreshCreator());
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_header_view,mRecyclerView,false);
        mRecyclerView.addHeaderView(headerView);
        mRecyclerView.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        mRecyclerView.addLoadViewCreator(new DefaultLoadCreator());
        mRecyclerView.setOnLoadMoreListener(new LoadRefreshRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoad() {
                loadData();
            }
        });
    }

    private void initData() {
        new DataModel().loadData(page, new IDataModel.ILoadDataListener() {
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

    private void refreshData() {
        new DataModel().loadData(1, new IDataModel.ILoadDataListener() {
            @Override
            public void onSuccess(DataRootBean dataRootBean) {
                Log.e("CommonAdapterActivity","onSuccess-"+dataRootBean.toString());
                mDatas.clear();
                mDatas.addAll(dataRootBean.getData());
                mRecyclerView.onStopRefresh();
                mRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {
                Log.e("CommonAdapterActivity","onFailure-"+error);
            }
        });
    }

    private void loadData() {
        new DataModel().loadData(++page, new IDataModel.ILoadDataListener() {
            @Override
            public void onSuccess(DataRootBean dataRootBean) {
                Log.e("CommonAdapterActivity","onSuccess-"+dataRootBean.toString());
                mDatas.addAll(dataRootBean.getData());
                mRecyclerView.onStopLoad();
                mRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {
                Log.e("CommonAdapterActivity","onFailure-"+error);
            }
        });
    }

}
