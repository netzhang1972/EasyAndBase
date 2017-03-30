package com.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.easy.activity.BaseTitleBarActivity;

/**
 * Created by Administrator on 2017/3/30.
 */

public abstract class BaseActivity extends BaseTitleBarActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitleBar().setLeftText(ContextCompat.getDrawable(this, R.drawable.btn_return),
                "返回", null,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
//        getTitleBar().setLeftIcon(
//                ContextCompat.getDrawable(this,R.drawable.btn_return),
//                ContextCompat.getDrawable(this,R.drawable.titlebar_right_icon_background),
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        finish();
//                    }
//                });
    }
}
