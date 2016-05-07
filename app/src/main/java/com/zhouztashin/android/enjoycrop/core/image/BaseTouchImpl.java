package com.zhouztashin.android.enjoycrop.core.image;

import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * 触摸事件基础类，这里只记录了不同触摸手势基类的点信息.继承该类需要根据提供的点信息完成拖动与移动的实现。
 * 注意，这里对多手势触摸的支持是，以最后一个触摸屏幕的手指为基准，记录该手指的触摸轨迹。
 * @author Zhozutashin
 * @version 1.0
 * @created  2016/3/21.
 */
public abstract class BaseTouchImpl<T extends View> implements ITouch {

    private final String TAG = BaseTouchImpl.class.getSimpleName();
    /**
     * 触摸事件作用对象
     */
    protected T mView;
    /**
     * 最近一次更新的X坐标点
     */
    protected float mLastX ;
    /**
     * 最近一次更新的Y坐标点
     */
    protected float mLastY;
    /**
     * 初始X坐标点
     */
    protected float mInitX;
    /**
     * 初始Y坐标点
     */
    protected float mInitY;
    /**
     * 多点触摸有效手指
     */
    protected int mActivePointerId;

    public BaseTouchImpl(T v){
        mView = v;
    }


    public void setInitY(float initY) {
        this.mLastY = initY;
        this.mInitY = initY;
    }


    public void setInitX(float initX) {
        this.mLastX = initX;
        this.mInitX = initX;
    }


    public void setLastX(float lastX) {
        this.mLastX = lastX;
    }

    public void setLastY(float lastY) {
        this.mLastY = lastY;
    }

    /**
     * 获取当前X坐标与最后一次点击Y坐标的差值.
     * @param x 当前X坐标
     * @return 返回最后一次点击坐标的差值-当前X坐标
     */
    public float distanceOfCurrentAndLastX(float x){
        return mLastX - x;
    }

    /**
     * 获取当前Y坐标与最后一次点击的Y坐标差值
     * @param y 当前Y坐标
     * @return 返回最后一次点击的Y坐标差值-当前Y坐标
     */
    public float distanceOfCurrentAndLastY(float  y) {
        return mLastY - y;
    }

    /**
     * 获取当前有效的手指点ID
     * @return
     */
    public int getActivePointerId() {
        return mActivePointerId;
    }

    /**
     * 设置当前有效的手指点ID
     * @param activePointerId
     */
    public void setActivePointerId(int activePointerId) {
        this.mActivePointerId = activePointerId;
    }

    /**
     * 重置所有参数
     */
    public void clear(){
        mInitX = 0;
        mLastX = 0;
        mLastY =0 ;
        mInitY = 0;
        mActivePointerId = -1;
    }
    public void init(){}
    @Override
    public boolean touch(MotionEvent event){
        int action = MotionEventCompat.getActionMasked(event);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                setActivePointerId(MotionEventCompat.getPointerId(event, 0));
                setInitX(event.getX());
                setInitY(event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                //剔除无效点
                final int activePointerIndex = MotionEventCompat.findPointerIndex(event, getActivePointerId());
                if(activePointerIndex ==-1){
                    return true;
                }
                final float x = MotionEventCompat.getX(event, activePointerIndex);
                final  float y = MotionEventCompat.getY(event, activePointerIndex);
                //小于最小距离则不做任何操作
              /*  if(distanceOfCurrentAndLastX(x)<mTouchSlop&&
                        distanceOfCurrentAndLastY(y)<mTouchSlop){
                    return true;
                }*/
                drag(x, y);
                setLastX(x);
                setLastY(y);
                break;
            case MotionEvent.ACTION_UP:
                final int i = MotionEventCompat.getActionIndex(event);
                fling(MotionEventCompat.getX(event, i),MotionEventCompat.getY(event, i));
                clear();
                break;
            case MotionEvent.ACTION_CANCEL:
                clear();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                final int index = MotionEventCompat.getActionIndex(event);
                clear();
                setActivePointerId(MotionEventCompat.getPointerId(event, index));
                setInitX(MotionEventCompat.getX(event, index));
                setInitY(MotionEventCompat.getY(event, index));
                break;
            case MotionEvent.ACTION_POINTER_UP:
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    clear();
                    setInitX(MotionEventCompat.getX(event, newPointerIndex));
                    setInitY(MotionEventCompat.getY(event, newPointerIndex));
                    setActivePointerId(MotionEventCompat.getPointerId(event, newPointerIndex));
                break;
        }
        return false;
    }



}
