package com.easy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.easy.R;

/**
 * Created by Administrator on 2017/3/14.
 */

public class BaseActivity extends BaseTitleBarActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitleBar().setLeftIcon(ContextCompat.getDrawable(this, R.drawable.btn_player_back_nor),ActivityFinish);
//        getTitleBar().setLeftText("返回",ActivityFinish);
//        getTitleBar().setLeftIcon(ContextCompat.getDrawable(this, R.drawable.btn_player_back_nor),"返回",ActivityFinish);
//        getTitleBar().setLIcon(ContextCompat.getDrawable(this, R.drawable.btn_player_back_nor));
//        getTitleBar().setIconDescription("返回");
//        getTitleBar().setIconOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
    View.OnClickListener ActivityFinish = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            finish();
        }
    };
}
