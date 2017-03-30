package com.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.recycler.RecyclerViewDivider;

import butterknife.Bind;


public class MainActivity extends BaseActivity {
    @Bind(R.id.main_list)
    RecyclerView mRecyclerView;
    MainListAdapter mMainListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarContentView(R.layout.activity_main);
        getTitleBar().setTitle("首页");
        getTitleBar().removeLeftIcon();
        //创建线性LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //创建分割线
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this,LinearLayoutManager.HORIZONTAL));
        //创建适配器
        mRecyclerView.setAdapter(mMainListAdapter = new MainListAdapter(this));
        //添加Item点击事件
        mMainListAdapter.setOnItemClickListener(new MainListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this,DrawerActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,GalleryActivity.class));
                        break;
//                    case 2:
//                        startActivity(new Intent(MainActivity.this,DemoTitleBarActivity.class));
//                        break;
//                    case 3:
//                        startActivity(new Intent(MainActivity.this,DemoAdvertisementActivity.class));
//                        break;
                    default:
                        Toast.makeText(MainActivity.this, "该功能未开通", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

    }
    //RecyclerView适配器类
    static class  MainListAdapter extends RecyclerView.Adapter<MainListAdapter.AdapterItem>{

        String[] strListName={"DrawerLayout抽屉","ViewPager实现画廊","Demo标题栏","滚动广告"};

        LayoutInflater mLayoutInflater;

        public interface OnItemClickListener
        {
            void onItemClick(View view, int position);
        }
        OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        public MainListAdapter(Context context){
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public AdapterItem onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AdapterItem(mLayoutInflater.inflate(R.layout.activity_main_list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(AdapterItem holder, final int position) {
            holder.mTextView.setText(strListName[position]);
            if(mOnItemClickListener != null){
                holder.mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v,position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return strListName.length;
        }

        class AdapterItem extends RecyclerView.ViewHolder{
            private TextView mTextView;
            public AdapterItem(View itemView) {
                super(itemView);
                mTextView = (TextView)itemView.findViewById(R.id.list_name);
            }
        }
    }
    //-----------------------点击两次退出----------------------------
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    //--------------------------------------------------------------
}
