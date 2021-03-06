package com.easy.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easy.R;
import com.easy.helper.BackgroundHelper;
import com.easy.toolbox.Tools;
import com.easy.view.drawable.ReturnArrowDrawable;
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
    //固定左边图标大小
    private int mIconLeftSize;
    //固定右边图标大小
    private int mIconRightSize;
    //描述文字大小
    private int mDescriptionTextSize;
    //图标按下时的背景颜色
    private int mIconPressedBackgroundColor ;
    //文字颜色
    private int mTextColor;
    //标题文字大小
    private int mTitleTextSize;
    //副标题色
    private int mSubtitleTextSize;
    //标题栏高度
    private int titleBarHeight;
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
        mTextColor = a.getColor(R.styleable.TitleBarView_android_textColor,mTextColor);
        mTitleTextSize = a.getDimensionPixelSize(R.styleable.TitleBarView_titleTextSize,mTitleTextSize);
        mSubtitleTextSize = a.getDimensionPixelSize(R.styleable.TitleBarView_subtitleTextSize,mSubtitleTextSize);
        mIconLeftSize = a.getDimensionPixelSize(R.styleable.TitleBarView_iconLeftSize,mIconLeftSize);
        mIconRightSize = a.getDimensionPixelSize(R.styleable.TitleBarView_iconRightSize,mIconRightSize);
        mDescriptionTextSize = a.getDimensionPixelSize(R.styleable.TitleBarView_descriptionTextSize,mDescriptionTextSize);
        mIconPressedBackgroundColor = a.getColor(R.styleable.TitleBarView_iconPressedBackgroundColor,mIconPressedBackgroundColor);

        titleBarHeight = a.getLayoutDimension(R.styleable.TitleBarView_android_layout_height,0);
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
        addReturnButton();
    }
    private void initLayout(){
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
    private void addReturnButton(){
        ReturnArrowDrawable arrow = new ReturnArrowDrawable(getContext());
        arrow.setColor(mTextColor);
        setLeftIcon(arrow);
        //setLeftIconBackground(BackgroundHelper.RoundBackground(getContext(),mIconLeftSize,mIconPressedBackgroundColor));
        setLeftIconOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        setLeftIconPadding(15,15,15,15);
    }
    private RelativeLayout.LayoutParams getTitleBarLayoutParams(){
        return new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }
    public void removeRightRegion(){
        mRightRegion.removeAllViews();
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
            mTitle.setTextColor(mTextColor);
            mTitle.setText(text);
            mCoreRegion.addView(mTitle,getTitleBarLayoutParams());
        }
        if(!TextUtils.isEmpty(SubtitleText)){
            mSubtitle = new TextView(getContext());
            mSubtitle.setTextSize(mSubtitleTextSize);
            mSubtitle.setTextColor(mTextColor);
            mSubtitle.setText(SubtitleText);
            mCoreRegion.addView(mSubtitle,getTitleBarLayoutParams());
        }
    }
    private ImageView mLeftIcon;

    /**
     * 设置左边图标
     * @param icon
     */
    public void setLeftIcon(Drawable icon){
        mLeftRegion.removeAllViews();
        if (icon == null)return;
        if (mLeftIcon == null){
            mLeftIcon = new ImageView(getContext());
        }
        mLeftIcon.setImageDrawable(icon);
        mLeftIcon.setLayoutParams(new LayoutParams(
                mIconLeftSize,
                mIconLeftSize
        ));
        mLeftIcon.setBackground(BackgroundHelper.RoundBackground(getContext(),mIconLeftSize,mIconPressedBackgroundColor));
        mLeftRegion.addView(mLeftIcon);
    }

    /**
     * 设置左边图标填充
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setLeftIconPadding(int left, int top, int right, int bottom){
        mLeftIcon.setPadding(left,top,right,bottom);
    }
    /**
     * 设置左边图标背景
     * @param background
     */
    public void setLeftIconBackground(Drawable background){
        if(mLeftIcon != null){
            mLeftIcon.setBackground(background);
        }
    }
    /**
     * 设置左边图标事件
     * @param listener
     */
    public void setLeftIconOnClickListener(OnClickListener listener){
        if(mLeftIcon != null){
            mLeftIcon.setOnClickListener(listener);
        }
    }
    public void setLeftIconLayoutParams(int width,int height){
        if(mLeftIcon != null){
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mLeftIcon.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mLeftIcon.setLayoutParams(lp);
        }
    }
    /**
     * 设置左边文字
     * @param description 描述
     * @param background 背景
     * @param listener 事件
     */
    public void setLeftText(Drawable icon,CharSequence description,Drawable background,OnClickListener listener){
        mLeftRegion.removeAllViews();
        if(!TextUtils.isEmpty(description)){
            TextView mDescription = new TextView(getContext());
            mDescription.setGravity(Gravity.CENTER_VERTICAL);
            if(icon != null) {
                icon.setBounds(0, 0, mIconLeftSize, mIconLeftSize);
                mDescription.setCompoundDrawables(icon, null, null, null);
            }
            mDescription.setTextSize(mDescriptionTextSize);
            mDescription.setTextColor(mTextColor);
            mDescription.setText(description);
            mDescription.setOnClickListener(listener);
            mDescription.setBackground(background);
            mLeftRegion.addView(mDescription);
        }
    }
    private ImageView mRightIcon;

    /**
     * 设置右边图标
     * @param icon
     */
    public void setRightIcon(Drawable icon){
        mRightRegion.removeAllViews();
        if (icon == null)return;
        if(mRightIcon == null){
            mRightIcon = new ImageView(getContext());
            mRightIcon.setPadding(5,5,5,5);
        }
        mRightIcon.setImageDrawable(icon);
        mRightIcon.setLayoutParams(new LayoutParams(
                mIconRightSize,mIconRightSize
        ));
        mRightIcon.setBackground(BackgroundHelper.RoundBackground(getContext(),mIconRightSize,mIconPressedBackgroundColor));
        mRightRegion.addView(mRightIcon);
    }

    /**
     * 设置右边图标背景
     * @param background
     */
    public void setRightIconBackground(Drawable background){
        if(mRightIcon != null){
            mRightIcon.setBackground(background);
        }
    }

    /**
     * 设置右边图标事件
     * @param listener
     */
    public void setRightIconOnClickListener(OnClickListener listener){
        if(mRightIcon != null){
            mRightIcon.setOnClickListener(listener);
        }
    }

    /**
     * 设置右边图大小
     * @param width
     * @param height
     */
    public void setRightIconLayoutParams(int width,int height){
        if(mRightIcon != null){
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mRightIcon.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mRightIcon.setLayoutParams(lp);
        }
    }
    /**
     * 设置右边文字
     * @param description 描述
     * @param background 背景
     * @param listener 事件
     */
    public void setRightText(CharSequence description,Drawable background,OnClickListener listener){
        setRightText(null,description,background,listener);
    }

    /**
     * 设置右边文字
     * @param icon 图标
     * @param description 描述
     * @param background 背景
     * @param listener 事件
     */
    public void setRightText(Drawable icon,CharSequence description,Drawable background,OnClickListener listener){
        mRightRegion.removeAllViews();
        if(!TextUtils.isEmpty(description)){
            TextView mDescription = new TextView(getContext());
            mDescription.setGravity(Gravity.CENTER_VERTICAL);
            if(icon != null) {
                icon.setBounds(0, 0, mIconLeftSize, mIconLeftSize);
                mDescription.setCompoundDrawables(null,null,icon,null);
            }
            mDescription.setPadding(30,15,30,15);
            mDescription.setTextSize(mDescriptionTextSize);
            mDescription.setTextColor(mTextColor);
            mDescription.setText(description);
            mDescription.setOnClickListener(listener);
            mRightRegion.addView(mDescription);
        }
    }
    public void setCoreRegionViews(setViewLayout child){
        if(child == null)return;
        mCoreRegion.removeAllViews();
        mCoreRegion.addView(child.setViews());
    }
    public void setLeftRegionViews(setViewLayout child){
        if(child == null)return;
        mLeftRegion.removeAllViews();
        mLeftRegion.addView(child.setViews());
    }
    public void setRightRegionViews(setViewLayout child){
        if(child == null)return;
        mRightRegion.removeAllViews();
        mRightRegion.addView(child.setViews());
    }
    public int getIconPressedBackgroundColor(){
        return mIconPressedBackgroundColor;
    }
    public int getIconLeftSize(){
        return mIconLeftSize;
    }
    public int getTextColor(){
        return mTextColor;
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
    public interface setViewLayout{
        public View setViews();
    }
}
