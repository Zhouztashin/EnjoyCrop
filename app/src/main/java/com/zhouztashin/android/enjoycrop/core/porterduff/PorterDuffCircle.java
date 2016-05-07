package com.zhouztashin.android.enjoycrop.core.porterduff;

import android.graphics.Color;
import android.graphics.Paint;

import com.zhouztashin.android.enjoycrop.core.ILayer;

/**
 * 采用PorterDuffXfermode实现的正方形
 * @author Zhouztashin
 * @version 1.0
 * @created 2016/3/16
 */
public class PorterDuffCircle implements IPorterDuffShape {
    private final int BORDER_WIDTH＿DEFAULT = 6;
    private int mRadius = 0;
    private final Paint mPaint;
    private final Paint mBorderPaint;

    /**
     *
     * @param radius 半径
     */
    public PorterDuffCircle(int radius){
        mRadius = radius;
        mPaint = new Paint();
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mBorderPaint = new Paint();
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(BORDER_WIDTH＿DEFAULT);
        mBorderPaint.setAntiAlias(true);
    }
    /**
     * 设置边框宽度 单位Px
     * @param borderWidth
     */
    public void setBorderWidth(int borderWidth){
        mBorderPaint.setStrokeWidth(borderWidth);
    }

    /**
     * 设置边框颜色
     * @param colorResId
     */
    public void setBorderColor(int colorResId){
        mBorderPaint.setColor(colorResId);
    }

    @Override
    public void draw(ILayer layer, CanvasWrapper c) {
        int left = layer.width()/2;
        int top = layer.height()/2;
        c.drawCircle(left, top, mRadius, mPaint);
        c.drawCircle(left, top, mRadius, mBorderPaint);
    }

    @Override
    public int width() {
        return mRadius*2;
    }

    @Override
    public int height() {
        return mRadius*2;
    }


}
