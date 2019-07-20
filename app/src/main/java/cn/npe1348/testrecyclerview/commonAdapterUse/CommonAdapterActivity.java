package cn.npe1348.testrecyclerview.commonAdapterUse;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.npe1348.recyclerview.comadapter.ViewHolder;
import cn.npe1348.testrecyclerview.R;
import cn.npe1348.testrecyclerview.http.bean.Data;
import cn.npe1348.testrecyclerview.http.bean.DataRootBean;
import cn.npe1348.testrecyclerview.http.model.DataModel;
import cn.npe1348.testrecyclerview.http.model.IDataModel;

public class CommonAdapterActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<Data> mDatas = new ArrayList<>();
    private RecyclerAdapter mRecyclerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initData();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerAdapter = new RecyclerAdapter(this,mDatas,mRecyclerView);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerAdapter.setOnItemClickListener(new ViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(CommonAdapterActivity.this, "按下-"+position, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerAdapter.setOnItemLongClickListener(new ViewHolder.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                Toast.makeText(CommonAdapterActivity.this, "长按-"+position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void initData() {
        new DataModel().loadData(1, new IDataModel.ILoadDataListener() {
            @Override
            public void onSuccess(DataRootBean dataRootBean) {
                Log.e("CommonAdapterActivity","onSuccess-"+dataRootBean.toString());
                mDatas.clear();
                mDatas.addAll(dataRootBean.getData());
                mRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {
                Log.e("CommonAdapterActivity","onFailure-"+error);
            }
        });
    }
}
