package com.demo.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarContentView(R.layout.activity_main);
        getTitleBar().setTitle("首页");
        getTitleBar().setRightIcon(ContextCompat.getDrawable(this, R.drawable.toolbar_saysomething_icon_nor),Send);
    }
    View.OnClickListener Send = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "发送", Toast.LENGTH_SHORT)
                        .show();
        }
    };
}
