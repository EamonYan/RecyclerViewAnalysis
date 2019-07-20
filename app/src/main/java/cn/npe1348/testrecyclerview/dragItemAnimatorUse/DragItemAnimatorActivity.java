package cn.npe1348.testrecyclerview.dragItemAnimatorUse;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.npe1348.recyclerview.comadapter.ViewHolder;
import cn.npe1348.testrecyclerview.R;
import cn.npe1348.testrecyclerview.commonAdapterUse.RecyclerAdapter;
import cn.npe1348.recyclerview.addheaderfooter.WrapRecyclerView;
import cn.npe1348.testrecyclerview.http.bean.Data;
import cn.npe1348.testrecyclerview.http.bean.DataRootBean;
import cn.npe1348.testrecyclerview.http.model.DataModel;
import cn.npe1348.testrecyclerview.http.model.IDataModel;

public class DragItemAnimatorActivity extends AppCompatActivity {
    private WrapRecyclerView mRecyclerView;
    private List<Data> mDatas = new ArrayList<>();
    private RecyclerAdapter mRecyclerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrap_recycler_view);

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
                Toast.makeText(DragItemAnimatorActivity.this, "按下-"+position, Toast.LENGTH_SHORT).show();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                // 获取触摸响应的方向   包含两个 1.拖动dragFlags 2.侧滑删除swipeFlags
                // 代表只能是向左侧滑删除，当前可以是这样ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
                int swipeFlags = ItemTouchHelper.LEFT;


                // 拖动
                int dragFlags = 0;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    // GridView 样式四个方向都可以
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.LEFT |
                            ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT;
                } else {
                    // ListView 样式不支持左右
                    dragFlags = ItemTouchHelper.UP |
                            ItemTouchHelper.DOWN;
                }

                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // 获取原来的位置
                int fromPosition = viewHolder.getAdapterPosition();
                // 得到目标的位置
                int targetPosition = target.getAdapterPosition();
                if (fromPosition > targetPosition) {
                    for (int i = fromPosition; i < targetPosition; i++) {
                        Collections.swap(mDatas, i, i + 1);// 改变实际的数据集
                    }
                } else {
                    for (int i = fromPosition; i > targetPosition; i--) {
                        Collections.swap(mDatas, i, i - 1);// 改变实际的数据集
                    }
                }
                mRecyclerAdapter.notifyItemMoved(fromPosition, targetPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // 获取当前删除的位置
                int position = viewHolder.getAdapterPosition();
                mDatas.remove(position);
                // adapter 更新notify当前位置删除
                mRecyclerAdapter.notifyItemRemoved(position);
            }
        });

        // 这个就不多解释了，就这么attach
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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
