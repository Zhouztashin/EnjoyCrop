package com.zhouztashin.android.enjoycrop.core.image;

import android.view.MotionEvent;

/**
 * 手势移动接口
 * 这里定义了手势移动的两种基本方式：拖动与滑动
 * @author Zhouztashin
 * @version 1.0
 * @created 2016/03/21
 */
public interface ITouch {
    /**
     * 拖动实现方式
     * @param motionX 当前的X坐标
     * @param motionY 当前的Y坐标
     */
    void drag(float motionX,float motionY);

    /**
     * 滑动实现方式
     * @param motionX 当前的X 坐标
     * @param motionY 当前的Y 坐标
     */
    void fling(float motionX,float motionY);


    /**
     * 拖动与滑动的具体实现方式，应该在这里被处理
     * @param event 触发的触摸事件
     * @return
     */
    boolean touch(MotionEvent event);
}
