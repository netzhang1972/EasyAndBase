package com.demo.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarContentView(R.layout.test01);
        getTitleBar().setTitle("首页");
//        getTitleBar().setRightText(
//                ContextCompat.getDrawable(this, R.drawable.btn_edit),
//                "编辑",null,Send);
        getTitleBar().setRightIcon(
                ContextCompat.getDrawable(this, R.drawable.btn_edit),
                ContextCompat.getDrawable(this, R.drawable.titlebar_right_icon_background)
                ,Send);
    }
    View.OnClickListener Send = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "发送", Toast.LENGTH_SHORT)
                        .show();
        }
    };
}
