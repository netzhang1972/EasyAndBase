package com.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.easy.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarContentView(R.layout.activity_main);
        getTitleBar().setTitle("首页");
//        getTitleBar().setIcon(null);
//        setContentView(R.layout.activity_main);
    }
}
