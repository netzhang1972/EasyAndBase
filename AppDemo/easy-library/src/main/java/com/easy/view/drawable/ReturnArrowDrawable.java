package com.easy.view.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;

import com.easy.R;

/**
 * Created by Administrator on 2017/3/31.
 */

public class ReturnArrowDrawable extends Drawable{
    //画笔
    private final Paint mPaint = new Paint();
    private final Path mPath = new Path();
    public ReturnArrowDrawable(Context context){
        //初始化画笔
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

        final TypedArray a = context.getTheme().obtainStyledAttributes(null,R.styleable.ReturnArrowDrawable,
                R.attr.returnArrowDrawableAttr,R.style.Easy_ReturnArrowDrawableStyle);
        setColor(a.getColor(R.styleable.ReturnArrowDrawable_lineColor,0));
        setWidth(a.getDimension(R.styleable.ReturnArrowDrawable_lineWidth,0));
        a.recycle();

    }

    /**
     * 设置画笔线条宽度
     * @param width
     */
    public void setWidth(float width){
        if (mPaint.getStrokeWidth() != width) {
            mPaint.setStrokeWidth(width);
            invalidateSelf();
        }
    }
    /**
     * 设置画笔颜色
     * @param color
     */
    public void setColor(@ColorInt int color) {
//        if (color != mPaint.getColor()) {
            mPaint.setColor(color);

            invalidateSelf();
//        }
    }
    @Override
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();

        //条线的起点位置
        int oneStartX = bounds.width()/6;
        int oneStartY = bounds.height()/2;
        //条线的终点位置
        int oneEndX = bounds.width() /2;
        int oneEndY = bounds.height()/2-5;

        mPath.moveTo(oneStartX, oneStartY);
        mPath.rLineTo(oneEndX, oneEndY);

        mPath.moveTo(oneStartX, oneStartY);
        mPath.rLineTo(oneEndX, -oneEndY);

        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        if (alpha != mPaint.getAlpha()) {
            mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
