package com.zhouztashin.android.enjoycrop.core.clippath;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.zhouztashin.android.enjoycrop.core.ILayer;

/**
 * Canvas.clipPath(..)方式实现的圆形
 * @author  Zhouztashin
 * @version 1.0
 * @created 2016/4/22.
 */
public class ClipPathCircle implements IClipPathShape {

    /**
     * 默认边框宽度
     */
    private final int BORDER_WIDTH_DEFAULT = 5;
    private int mRadius = 0;
    private final Paint mPaint;

    /**
     *
     * @param radius 半径
     */
    public ClipPathCircle(int radius){
        mRadius = radius;
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(BORDER_WIDTH_DEFAULT);
        mPaint.setAntiAlias(true);
    }

    /**
     * 设置边框宽度 单位Px
     * @param borderWidth
     */
    public void setBorderWidth(int borderWidth){
        mPaint.setStrokeWidth(borderWidth);
    }

    /**
     * 设置边框颜色
     * @param colorResId
     */
    public void setBorderColor(int colorResId){
        mPaint.setColor(colorResId);
    }
    @Override
    public Path createShapePath(ILayer layer) {
        int left = layer.width()/2;
        int top = layer.height()/2;
        Path path = new Path();
        path.addCircle(left, top, mRadius, Path.Direction.CW);
        return path;
    }

    @Override
    public Paint createShapePaint() {
        return mPaint;
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
