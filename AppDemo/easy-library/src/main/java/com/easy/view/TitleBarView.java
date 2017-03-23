package com.easy.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easy.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 标题栏View
 * Created by Administrator on 2017/2/14.
 */

public class TitleBarView extends RelativeLayout {
    private static final String TAG = "TitleBarView";
    private AppCompatActivity mActivity;
    //处理状态背景
    private SystemBarTintManager mTintManager;
    //左边区域
    private LinearLayout mLeftRegion;
    //右边区域
    private LinearLayout mRightRegion;
    //中间区域
    private LinearLayout mCoreRegion;
    //标题
    private TextView mTitle;
    //标题颜色
    private int mTitleTextColor;
    //标题文字大小
    private int mTitleTextSize;
    //副标题
    private TextView mSubtitle;
    //副标题色
    private int mSubtitleTextSize;
    //副标题文字大小
    private int mSubtitleTextColor;
    //左边Logo
    private ImageView mIconView;
    //Logo描述
    private TextView mLogoDescription;
    //Logo按下时的背景颜色
    private int mIconPressedBackgroundColor ;
    //固定图标大小
    private int mIconSize;
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
        mIconPressedBackgroundColor = ContextCompat.getColor(context, R.color.easy_black);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView, defStyleAttr, R.style.Easy_TitleBarStyle);
        mTitleTextColor = a.getColor(R.styleable.TitleBarView_titleTextColor,mTitleTextColor);
        mTitleTextSize = a.getDimensionPixelSize(R.styleable.TitleBarView_titleTextSize,mTitleTextSize);
        mSubtitleTextColor = a.getColor(R.styleable.TitleBarView_subtitleTextColor,mSubtitleTextColor);
        mSubtitleTextSize = a.getDimensionPixelSize(R.styleable.TitleBarView_subtitleTextSize,mSubtitleTextSize);
        mIconSize = a.getDimensionPixelSize(R.styleable.TitleBarView_iconSize,mIconSize);
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
        initWindow();
        initLayout();
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
    private void initLayout(){
        mCoreRegion = new LinearLayout(getContext());
        mCoreRegion.setGravity(Gravity.CENTER);
        mCoreRegion.setOrientation(LinearLayout.VERTICAL);
        LayoutParams mCoreLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        mCoreLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addView(mCoreRegion,mCoreLayoutParams);

        mLeftRegion = new LinearLayout(getContext());
        mLeftRegion.setPadding(30,0,0,0);
        mLeftRegion.setGravity(Gravity.CENTER);
        LayoutParams mLeftLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        mLeftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        this.addView(mLeftRegion,mLeftLayoutParams);

        mRightRegion = new LinearLayout(getContext());
        mRightRegion.setGravity(Gravity.CENTER);
        mRightRegion.setPadding(0,0,30,0);
        LayoutParams mRightLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        mRightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        this.addView(mRightRegion,mRightLayoutParams);
    }
    //副标题颜色
    public void setSubtitleColor(int color) {
        mSubtitle.setTextColor(color);
    }

    //副标题字体大小
    public void setSubtitleSize(float size) {
        mSubtitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    }
    //标题颜色
    public void setTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    //标题字体大小
    public void setTitleSize(float size) {
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    }
    //标题
    public void setTitle(CharSequence text) {
        if(!TextUtils.isEmpty(text)){
            ensureTitleView();
            mTitle.setText(text);
//            mTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            mTitle.setVisibility(VISIBLE);
        }
    }
    //副标题
    public void setSubtitle(CharSequence text) {
        if(!TextUtils.isEmpty(text)){
            ensureTitleView();
            mSubtitle.setText(text);
//            mTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            mSubtitle.setVisibility(VISIBLE);
        }
    }
    //确保标题被实例化
    private void ensureTitleView(){
        mCoreRegion.removeAllViews();
        mCoreRegion.setOrientation(LinearLayout.VERTICAL);
        if(mTitle == null){
            mTitle = new TextView(getContext());
            mTitle.setVisibility(GONE);
            mTitle.setTextSize(mTitleTextSize);
            mTitle.setTextColor(mTitleTextColor);
        }
        if(mSubtitle == null){
            mSubtitle = new TextView(getContext());
            mSubtitle.setVisibility(GONE);
            mSubtitle.setTextSize(mSubtitleTextSize);
            mSubtitle.setTextColor(mSubtitleTextColor);
        }
        mCoreRegion.addView(mTitle, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mCoreRegion.addView(mSubtitle, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    //确保左边LogoView被实例化
    private void ensureIconView() {
        mLeftRegion.removeAllViews();
        mLeftRegion.setOnClickListener(null);
        if (mIconView == null) {
            mIconView = new ImageView(getContext());
            mIconView.setLayoutParams(new LayoutParams(
                    mIconSize,mIconSize
            ));
            mIconView.setBackground(getLogoBackground());
            mIconView.setVisibility(GONE);
        }
        if(mLogoDescription == null){
            mLogoDescription = new TextView(getContext());
            mLogoDescription.setVisibility(GONE);
            mLogoDescription.setTextSize(mSubtitleTextSize);
            mLogoDescription.setTextColor(mSubtitleTextColor);
        }else {
            mLogoDescription.setText(null);
        }
        mIconView.setOnClickListener(null);
        mLeftRegion.addView(mIconView);
        mLeftRegion.addView(mLogoDescription);
    }
    //设置图标
    public void setIcon(int resId) {
        setIcon(ContextCompat.getDrawable(getContext(), resId));
    }
    //设置图标
    public void setIcon(Drawable drawable){
        if(drawable != null){
            ensureIconView();
            mIconView.setImageDrawable(drawable);
            mIconView.setVisibility(VISIBLE);
        }else {
            mLeftRegion.removeAllViews();
            mLeftRegion.setOnClickListener(null);
            mIconView = null;
            mLogoDescription = null;
        }
    }
    //设置图标点击事件
    public void setIconOnClickListener(OnClickListener listener){
        mIconView.setOnClickListener(listener);
    }
    //设置图标描述
    public void setLogoDescription(int resId) {
        setLogoDescription(getContext().getText(resId));
    }
    //设置图标描述
    public void setLogoDescription(CharSequence description) {
        if (!TextUtils.isEmpty(description)) {
            ensureIconView();
            mLogoDescription.setText(description);
            mLogoDescription.setVisibility(VISIBLE);
        }
    }
    //设置透明度
    public void setAlpha(int alpha){
        this.getBackground().setAlpha(alpha);
        if(mIconView != null){
            mIconView.getBackground().setAlpha(alpha);
        }
    }
    private Drawable getLogoBackground(){
        Drawable normalDraw = new RoundBackground(getContext())
                .create();
        Drawable pressedDraw = new RoundBackground(getContext())
                .setSize(mIconSize,mIconSize)
                .setSolidColor(mIconPressedBackgroundColor)
                .create();
        pressedDraw.setAlpha(50);
        return getSelector(normalDraw,pressedDraw);
    }

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
    /**
     * 获取Selector
     * @param normalDraw
     * @param pressedDraw
     * @return
     */
    private StateListDrawable getSelector(Drawable normalDraw, Drawable pressedDraw) {
        StateListDrawable stateListDrawable  = new StateListDrawable();
        stateListDrawable.addState(new int[]{ android.R.attr.state_pressed }, pressedDraw);
        stateListDrawable.addState(new int[]{ }, normalDraw);
        return stateListDrawable ;
    }
    //圆形背景
    public class RoundBackground{
        private Context mContext;
        private GradientDrawable gradientDrawable;
        public RoundBackground(Context context){
            mContext = context;
            gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.OVAL);
            gradientDrawable.setUseLevel(false);
        }
        //设置宽高
        public RoundBackground setSize(int width, int height){
            gradientDrawable.setSize(width,height);
            return this;
        }
        //背景颜色
        public RoundBackground setSolidColor(int solidColor){
            gradientDrawable.setColor(solidColor); //背景色
            return this;
        }
        //边框
        public RoundBackground setStroke(int strokeWidth, int strokeColor){
            gradientDrawable.setStroke(strokeWidth,strokeColor); //边框宽度，边框颜色
            return this;
        }
        public GradientDrawable create(){
            return gradientDrawable;
        }
    }

}
