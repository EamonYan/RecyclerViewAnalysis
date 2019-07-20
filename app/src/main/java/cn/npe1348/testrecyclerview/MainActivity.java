package cn.npe1348.testrecyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import cn.npe1348.testrecyclerview.basicUse.BasicUseActivity;
import cn.npe1348.testrecyclerview.commonAdapterUse.CommonAdapterActivity;
import cn.npe1348.testrecyclerview.dragItemAnimatorUse.DragItemAnimatorActivity;
import cn.npe1348.testrecyclerview.headerFooterUse.HeaderFooterActivity;
import cn.npe1348.testrecyclerview.refreshLoadUse.RefreshLoadActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void basicUse(View view) {
        Intent intent = new Intent(this, BasicUseActivity.class);
        startActivity(intent);
    }

    public void commonAdapter(View view) {
        Intent intent = new Intent(this, CommonAdapterActivity.class);
        startActivity(intent);
    }

    public void dragItemAnimator(View view) {
        Intent intent = new Intent(this, DragItemAnimatorActivity.class);
        startActivity(intent);
    }

    public void headerFooter(View view) {
        Intent intent = new Intent(this, HeaderFooterActivity.class);
        startActivity(intent);
    }

    public void refreshLoad(View view){
        Intent intent = new Intent(this, RefreshLoadActivity.class);
        startActivity(intent);
    }
}
