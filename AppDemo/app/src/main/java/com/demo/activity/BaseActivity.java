package com.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.easy.activity.BaseTitleBarActivity;
import com.easy.view.drawable.ReturnArrowDrawable;

/**
 * Activity基类此类中主要定义了标题栏的返回按钮
 * Created by Administrator on 2017/3/30.
 */
public abstract class BaseActivity extends BaseTitleBarActivity {
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ReturnArrowDrawable d= new ReturnArrowDrawable(this);
//        d.setColor(ContextCompat.getColor(this,R.color.easy_white));
//        getTitleBar().setLeftIcon(new ReturnArrowDrawable(this),
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        finish();
//                    }
//                });
////        getTitleBar().setLeftIcon(ContextCompat.getDrawable(this, R.drawable.btn_return_normal),
////                new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        finish();
////                    }
////                });
//    }
}
