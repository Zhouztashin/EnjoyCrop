package com.zhouztashin.android.enjoycrop.core;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Administrator on 2016/4/22.
 * 基础集成层。集成了形状层和遮罩层
 */
public class BaseLayerView  extends View implements  ILayer{
    protected   IShape mShape;
    protected  IMask mMask;


    public void setMask(IMask mMask) {
        this.mMask = mMask;
    }



    public void setShape(IShape mShape) {
        this.mShape = mShape;
    }

    public IMask getMask() {
        return mMask;
    }


    public IShape getShape() {
        return mShape;
    }

    public BaseLayerView(Context context) {
        super(context);
    }

    public BaseLayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    public int width() {
        return  getWidth();
    }

    @Override
    public int height() {
        return   getHeight();
    }

    @Override
    public int left() {
        return getLeft();
    }

    @Override
    public int right() {
        return getRight();
    }

    @Override
    public int top() {
        return getTop();
    }

    @Override
    public int bottom() {
        return getBottom();
    }


}
