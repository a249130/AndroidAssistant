package com.yk.androidassistant;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yk.androidassistant.Adapter.MainAdapter;
import com.yk.androidassistant.Base.BaseQMUIActivity;

public class MainActivity extends BaseQMUIActivity {

    RecyclerView recycler;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setTitle("首页");
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new MainAdapter();
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });
    }
}
