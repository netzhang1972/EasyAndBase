package com.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageButton;

import com.easy.view.drawable.ReturnArrowDrawable;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/31.
 */

public class Test01 extends BaseActivity {
    @Bind(R.id.xxxx)
    ImageButton imageButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarContentView(R.layout.test01);
        imageButton.setImageDrawable(new ReturnArrowDrawable(this));
//        imageButton.setBackground(new ReturnArrowDrawable(this));
    }
}
