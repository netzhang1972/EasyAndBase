package com.easy.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import com.easy.toolbox.Tools;
import com.easy.view.TitleBarView;

/**
 * Created by Administrator on 2017/3/27.
 */

public class BackgroundHelper {
    private Context mContext;
    private GradientDrawable gradientDrawable;
    public BackgroundHelper(Context context,int shape){
        mContext = context;
        gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(shape);
        gradientDrawable.setUseLevel(false);
    }
    //设置宽高
    public BackgroundHelper setSize(int width, int height){
        gradientDrawable.setSize(width,height);
        return this;
    }
    //背景颜色
    public BackgroundHelper setSolidColor(int solidColor){
        gradientDrawable.setColor(solidColor); //背景色
        return this;
    }
    //边框
    public BackgroundHelper setStroke(int strokeWidth, int strokeColor){
        gradientDrawable.setStroke(strokeWidth,strokeColor); //边框宽度，边框颜色
        return this;
    }
    //圆角
    public BackgroundHelper setCornersRadius(float f){
        gradientDrawable.setCornerRadius(f);
        return this;
    }
    //圆角
    public BackgroundHelper setCornersRadius(float[] radii){
        gradientDrawable.setCornerRadii(radii);
        return this;
    }
    public GradientDrawable create(){
        return gradientDrawable;
    }

    /**
     * 圆角背景
     * @param context
     * @param color
     * @param f
     * @return
     */
    public static Drawable RectBackground(Context context,int color,int size,float f){
        Drawable normalDraw = new BackgroundHelper(context, GradientDrawable.RECTANGLE)
                .create();
        Drawable pressedDraw = new BackgroundHelper(context, GradientDrawable.RECTANGLE)
                .setSolidColor(color)
                .setSize(size,size)
                .setCornersRadius(f)
                .create();
        pressedDraw.setAlpha(50);
        return getSelector(normalDraw,pressedDraw);
    }
    /**
     * 圆形背景
     * @param context
     * @param size
     * @param color
     * @return
     */
    public static Drawable RoundBackground(Context context,int size,int color){
        Drawable normalDraw = new BackgroundHelper(context, GradientDrawable.OVAL)
                .create();
        Drawable pressedDraw = new BackgroundHelper(context, GradientDrawable.OVAL)
                .setSize(size,size)
                .setSolidColor(color)
                .create();
        pressedDraw.setAlpha(50);
        return getSelector(normalDraw,pressedDraw);
    }
    /**
     * 获取Selector
     * @param normalDraw
     * @param pressedDraw
     * @return
     */
    private static StateListDrawable getSelector(Drawable normalDraw, Drawable pressedDraw) {
        StateListDrawable stateListDrawable  = new StateListDrawable();
        stateListDrawable.addState(new int[]{ android.R.attr.state_pressed }, pressedDraw);
        stateListDrawable.addState(new int[]{ }, normalDraw);
        return stateListDrawable ;
    }
}
