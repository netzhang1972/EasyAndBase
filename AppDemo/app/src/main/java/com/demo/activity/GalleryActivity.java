package com.demo.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import butterknife.Bind;

/**
 * Created by Administrator on 2017/1/24.
 */

public class GalleryActivity extends BaseActivity {
    private static final String TAG = "TestGallery";
    @Bind(R.id.mFrameLayout)
    FrameLayout mFrameLayout;
    @Bind(R.id.id_gallery)
    ViewPager mViewPager;
    PagerAdapter mAdapter;
    int[] imgRes = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
            R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarContentView(R.layout.activity_gallery);
        getTitleBar().setTitle("ViewPager实现画廊");
        //页与页之间的间距
        mViewPager.setPageMargin(40);
        //设置缓存数目
        mViewPager.setOffscreenPageLimit(3);
        //将父类的touch时间分发至ViewPage,否则只能滑动中间的一个View对象
        mFrameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });
        mViewPager.setAdapter(mAdapter = new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                ImageView view = new ImageView(container.getContext());
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                view.setImageResource(imgRes[position]);
                //http://wiki.jikexueyuan.com/project/fresco/using-drawees-code.html
               // view.setImageResource(imgRes[position]);
                container.addView(view);
                return view;
            }
            @Override
            public int getCount() {
                return imgRes.length;
            }
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        mViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                float mMin_Sclae = 0.70f;
                float DEFAULT_CENTER = 0.5f;
                float rotate=10 * Math.abs(position);
                int pageWidth = view.getWidth();
                if(position < -1){//左边
                    view.setScaleX(mMin_Sclae);
                    view.setScaleY(mMin_Sclae);
                    view.setPivotX(pageWidth);
                    view.setRotationY(rotate);
                }else if (position <= 1){
                    if (position < 0){//从左向右滚动
                        float scaleFactor = (1 + position) * (1 - mMin_Sclae) + mMin_Sclae;
                        view.setScaleX(scaleFactor);
                        view.setScaleY(scaleFactor);
                        view.setPivotX(pageWidth * (DEFAULT_CENTER + (DEFAULT_CENTER * -position)));
                        view.setRotationY(rotate);
                    }else {//从右向左滚动
                        float scaleFactor = (1 - position) * (1 - mMin_Sclae) + mMin_Sclae;
                        view.setScaleX(scaleFactor);
                        view.setScaleY(scaleFactor);
                        view.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
                        view.setRotationY(-rotate);
                    }
                }else {//右边
                    view.setPivotX(0);
                    view.setScaleX(mMin_Sclae);
                    view.setScaleY(mMin_Sclae);
                    view.setRotationY(-rotate);
                }
            }
        });
        //注意要在setAdapter才起作用
        mViewPager.setCurrentItem(1);
//        mViewPager.setPageTransformer(true,new ScaleInTransformer());
    }
}
