package com.yk.androidassistant;

import android.os.Bundle;

import com.yk.androidassistant.Base.BaseQMUIActivity;

public class MainActivity extends BaseQMUIActivity {

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
    }
}
