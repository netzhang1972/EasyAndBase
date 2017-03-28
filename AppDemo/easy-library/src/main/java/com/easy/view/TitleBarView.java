package com.easy.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easy.R;
import com.easy.helper.BackgroundHelper;
import com.easy.toolbox.Tools;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Administrator on 2017/3/27.
 */

public class TitleBarView extends RelativeLayout {
    private AppCompatActivity mActivity;
    //左边区域
    private LinearLayout mLeftRegion;
    //右边区域
    private LinearLayout mRightRegion;
    //中间区域
    private LinearLayout mCoreRegion;
    //固定图标大小
    private int mIconSize;
    //描述文字大小
    private int mDescriptionTextSize;
    //描述文字颜色
    private int mDescriptionTextColor;
    //图标按下时的背景颜色
    private int mIconPressedBackgroundColor ;
    //标题颜色
    private int mTitleTextColor;
    //标题文字大小
    private int mTitleTextSize;
    //副标题色
    private int mSubtitleTextSize;
    //副标题文字大小
    private int mSubtitleTextColor;

    public TitleBarView(Context context) {
        this(context,null);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.titleBarAttr);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode())return;
        if(context instanceof AppCompatActivity){
            mActivity = (AppCompatActivity)context;
        }

        initWindow();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView, defStyleAttr, R.style.Easy_TitleBarStyle);
        mTitleTextColor = a.getColor(R.styleable.TitleBarView_titleTextColor,mTitleTextColor);
        mTitleTextSize = a.getDimensionPixelSize(R.styleable.TitleBarView_titleTextSize,mTitleTextSize);
        mSubtitleTextColor = a.getColor(R.styleable.TitleBarView_subtitleTextColor,mSubtitleTextColor);
        mSubtitleTextSize = a.getDimensionPixelSize(R.styleable.TitleBarView_subtitleTextSize,mSubtitleTextSize);
        mIconSize = a.getDimensionPixelSize(R.styleable.TitleBarView_iconSize,mIconSize);
        mDescriptionTextSize = a.getDimensionPixelSize(R.styleable.TitleBarView_descriptionTextSize,mDescriptionTextSize);
        mDescriptionTextColor = a.getColor(R.styleable.TitleBarView_descriptionTextColor,mDescriptionTextColor);
        mIconPressedBackgroundColor = a.getColor(R.styleable.TitleBarView_iconPressedBackgroundColor,mIconPressedBackgroundColor);
        int titleBarHeight = a.getDimensionPixelSize(R.styleable.TitleBarView_titleBarHeight,0);
        if(titleBarHeight != 0){
            this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,titleBarHeight));
        }

        int statusBarColor = a.getColor(R.styleable.TitleBarView_statusBarColor,0);
        if(statusBarColor != 0){
            setStatusBarTintColor(statusBarColor);
        }
        int functionBarColor = a.getColor(R.styleable.TitleBarView_functionBarColor,0);
        if(functionBarColor != 0){
            setNavigationBarTintColor(functionBarColor);
        }
        a.recycle();
        initLayout();
    }
    private void initLayout(){
        setPadding(getPaddingLeft(),getPaddingTop(),getPaddingRight(),getPaddingBottom());
        mCoreRegion = new LinearLayout(getContext());
        mCoreRegion.setGravity(Gravity.CENTER);
        mCoreRegion.setOrientation(LinearLayout.VERTICAL);
        LayoutParams mCoreLayoutParams = getTitleBarLayoutParams();
        mCoreLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addView(mCoreRegion,mCoreLayoutParams);

        mLeftRegion = new LinearLayout(getContext());
        mLeftRegion.setOrientation(LinearLayout.HORIZONTAL);

        mLeftRegion.setGravity(Gravity.CENTER);
        LayoutParams mLeftLayoutParams = getTitleBarLayoutParams();
        mLeftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mLeftLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        this.addView(mLeftRegion,mLeftLayoutParams);

        mRightRegion = new LinearLayout(getContext());
        mRightRegion.setOrientation(LinearLayout.HORIZONTAL);
        mRightRegion.setGravity(Gravity.CENTER);

        LayoutParams mRightLayoutParams = getTitleBarLayoutParams();
        mRightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mRightLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        this.addView(mRightRegion,mRightLayoutParams);
    }
    private RelativeLayout.LayoutParams getTitleBarLayoutParams(){
        return new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }
    /**
     * 标题
     */
    public void setTitle(CharSequence text){
        setTitle(text,null);
    }

    /**
     * 标题
     * @param text 标题
     * @param SubtitleText 副标题
     */
    public void setTitle(CharSequence text,CharSequence SubtitleText) {
        mCoreRegion.removeAllViews();
        TextView mTitle = null;
        TextView mSubtitle = null;
        if(!TextUtils.isEmpty(text)){
            mTitle = new TextView(getContext());
            mTitle.setTextSize(mTitleTextSize);
            mTitle.setTextColor(mTitleTextColor);
            mTitle.setText(text);
            mCoreRegion.addView(mTitle,getTitleBarLayoutParams());
        }
        if(!TextUtils.isEmpty(SubtitleText)){
            mSubtitle = new TextView(getContext());
            mSubtitle.setTextSize(mSubtitleTextSize);
            mSubtitle.setTextColor(mSubtitleTextColor);
            mSubtitle.setText(SubtitleText);
            mCoreRegion.addView(mSubtitle,getTitleBarLayoutParams());
        }
    }

    //设置图标
    public void setLeftIcon(Drawable drawable,OnClickListener listener){
        setLeftIcon(drawable,null,listener);
    }
    //设置图标
    public void setLeftText(CharSequence description,OnClickListener listener){
        setLeftIcon(null,description,listener);
    }
    //设置图标
    public void setLeftIcon(Drawable drawable,CharSequence description,OnClickListener listener){
        mLeftRegion.removeAllViews();
        ImageView mIconView = null;
        TextView mDescription = null;
        if(drawable != null){
            mIconView = new ImageView(getContext());
            mIconView.setImageDrawable(drawable);
            mIconView.setLayoutParams(new LayoutParams(
                    mIconSize,mIconSize
            ));
            mLeftRegion.addView(mIconView);
        }
        if (!TextUtils.isEmpty(description)){
            mDescription = new TextView(getContext());
            mDescription.setTextSize(mDescriptionTextSize);
            mDescription.setTextColor(mDescriptionTextColor);
            mDescription.setText(description);
            mLeftRegion.addView(mDescription,getTitleBarLayoutParams());
        }
        if(mIconView != null && mDescription != null){
            mLeftRegion.setOnClickListener(listener);
            mLeftRegion.setPadding(0,10,30,10);
            mLeftRegion.setBackground(BackgroundHelper.RectBackground(
                    getContext(), mIconPressedBackgroundColor,
                  Tools.Int2px(getContext(),mIconSize),20f));
        }else if (mIconView != null){
            mIconView.setOnClickListener(listener);
            mIconView.setBackground(BackgroundHelper.RoundBackground(
                    getContext(),Tools.Int2px(getContext(),mIconSize),
                    mIconPressedBackgroundColor));
        }else if (mDescription != null){
            mDescription.setOnClickListener(listener);
            mDescription.setPadding(30,15,30,15);
            mDescription.setBackground(BackgroundHelper.RectBackground(
                    getContext(), mIconPressedBackgroundColor,
                    Tools.Int2px(getContext(),mIconSize),20f));
        }
    }
    public void setRightIcon(Drawable drawable,OnClickListener listener){
        mRightRegion.removeAllViews();
        if(drawable != null){
            ImageView mIconView = new ImageView(getContext());
            mIconView.setImageDrawable(drawable);
            mIconView.setVisibility(VISIBLE);
            mIconView.setLayoutParams(new LayoutParams(
                    mIconSize,mIconSize
            ));
            mIconView.setBackground(BackgroundHelper.RoundBackground(
                    getContext(),Tools.Int2px(getContext(),mIconSize),
                    mIconPressedBackgroundColor));
            mIconView.setOnClickListener(listener);
            mRightRegion.addView(mIconView);
        }
    }
    public void setRightText(CharSequence description,OnClickListener listener){
        mRightRegion.removeAllViews();
        if(!TextUtils.isEmpty(description)){
            TextView mDescription = new TextView(getContext());
            mDescription.setPadding(30,15,30,15);
            mDescription.setTextSize(mDescriptionTextSize);
            mDescription.setTextColor(mDescriptionTextColor);
            mDescription.setText(description);
            mDescription.setOnClickListener(listener);
            mDescription.setBackground(BackgroundHelper.RectBackground(
                    getContext(), mIconPressedBackgroundColor,
                    Tools.Int2px(getContext(),mIconSize),20f));
            mRightRegion.addView(mDescription);
        }
    }
    public void setCoreRegionViews(View child){
        mCoreRegion.removeAllViews();
        mCoreRegion.addView(child);
    }
    public void setLeftRegionViews(View child){
        mLeftRegion.removeAllViews();
        mLeftRegion.addView(child);
    }
    public void setRightRegionViews(View child){
        mRightRegion.removeAllViews();
        mRightRegion.addView(child);
    }
    //-------------------------------------------------------------
    //处理状态背景
    private SystemBarTintManager mTintManager;
    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((Activity) getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ((Activity) getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            mTintManager = new SystemBarTintManager(mActivity);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(true);
        }
    }
    //状态栏和功能键颜色
    public void setTintColor(int color){
        mTintManager.setTintColor(color);
    }
    //状态栏颜色
    public void setStatusBarTintColor(int color){
        mTintManager.setStatusBarTintColor(color);
    }
    //功能键颜色
    public void setNavigationBarTintColor(int color){
        mTintManager.setNavigationBarTintColor(color);
    }
    //-------------------------------------------------------------
}
