package com.yk.androidassistant.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yk.androidassistant.Adapter.Model.MainModel;
import com.yk.androidassistant.R;

import org.jetbrains.annotations.NotNull;

public class MainAdapter extends BaseQuickAdapter<MainModel, BaseViewHolder> {

    public MainAdapter() {
        super(R.layout.main_item);
        for (int i = 0; i < 5; i++) {
            MainModel model = new MainModel();
            model.setId(i);
            switch (i) {
                case 0:
                    model.setName("短视频解析");
                    model.setIco(R.mipmap.ico_main_video_jx);
                    break;
                default:
                    model.setName("尽请期待");
                    break;
            }
        }
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MainModel mainModel) {

    }
}
