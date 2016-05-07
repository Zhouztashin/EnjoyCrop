package com.zhouztashin.android.enjoycrop.core.porterduff;

import android.graphics.Color;
import android.graphics.Paint;

import com.zhouztashin.android.enjoycrop.core.ILayer;

/**
 * 采用PorterDuffXfermode实现的正方形
 * @author Zhouztashin
 * @version 1.0
 * @created 2016/3/15
 */
public class PorterDuffSquare implements IPorterDuffShape{
    private final int BORDER_WIDTH_DEFAULT = 6;
    private int mWidth = 0;
    private final Paint mPaint;
    private final Paint mBorderPaint;

    /**
     *
     * @param width 宽度
     */
    public PorterDuffSquare(int width){
        mWidth = width;
        mPaint = new Paint();
        mPaint.setColor(Color.TRANSPARENT);
        mBorderPaint = new Paint();
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(BORDER_WIDTH_DEFAULT);
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
        int left = layer.width()/2- mWidth /2;
        int top = layer.height()/2-mWidth /2;
        //createShapePath transparent
        c.drawRect(left, top, left + mWidth, top + mWidth, mPaint);
        //createShapePath border
        c.drawRect(left,top,left+mWidth,top+mWidth,mBorderPaint);
    }

    @Override
    public int width() {
        return mWidth;
    }

    @Override
    public int height() {
        return mWidth;
    }

}
