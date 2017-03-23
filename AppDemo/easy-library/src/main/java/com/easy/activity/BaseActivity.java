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
        getTitleBar().setIcon(ContextCompat.getDrawable(this, R.drawable.btn_player_back_nor));
        getTitleBar().setIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
