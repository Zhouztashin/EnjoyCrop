package com.zhouztashin.android.enjoycrop.core.image;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * 通用的视图移动实现类,提供拖动与平滑移动效果。
 * @author Zhozutashin
 * @version 1.0
 * @created 2016/3/21
 * 使用指引：
 * 1、创建 SimpleViewTouchImpl{@link SimpleViewTouchImpl}实例
 * 2、重写View.computerScroll()方法，并且在方法里面调用SimpleViewTouchImpl.computerScroll()方法
 * 3、重写View.onTouchEvent(..)事件，并调用 SimpleViewTouchImpl.touch(..)方法。
 * Simple Code:
 * public class CustomView extend View{
 *     private SimpleViewTouchImpl  simpleViewTouchImpl;
 *     public CustomView(Context c){
 *         simpleViewTouchImpl = SimpleViewTouchImpl.newInstance(this);
 *     }
 *     @Override
 *     public void computerScroll(){
 *         simpleViewTouchImpl.computerScroll();
 *     }
 *     @Override
 *   public boolean onTouchEvent(MotionEvent event) {
 *       if((simpleViewTouchImpl.touch(event))){
 *           return true;
 *       }
 *       return super.onTouchEvent(event);
 *   }
 * }
 *
 */
public class SimpleViewTouchImpl extends BaseTouchImpl<View>{


    private final String TAG = SimpleViewTouchImpl.class.getSimpleName();
    /**
     * 平顺滑动
     */
    private Scroller mScroller;
    /**
     * 速度计算
     */
    private VelocityTracker mVelocityTracker;
    /**
     * 触摸事件作用对象
     */
    private View mView;

    /**
     * 获取新的视图移动工具类
     * @param v 触发滑动事件的视图
     */
    public static BaseTouchImpl newInstance(View v){
        return new SimpleViewTouchImpl(v);
    }

    private void addVelocityMotionEvent(MotionEvent motionEvent) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(motionEvent);
    }

    private void clearVelocity() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
        }
        mVelocityTracker = null;
    }

    private Scroller getScroller() {
        return mScroller;
    }

    public SimpleViewTouchImpl(View v) {
        super(v);
        mView = v;
        mScroller = new Scroller(v.getContext());
    }

    @Override
    public void clear() {
        super.clear();
        clearVelocity();
    }

    /**
     * 拖动视图
     *
     * @param motionX
     * @param motionY
     */
    public void drag(float motionX, float motionY) {
        mView.scrollBy((int) distanceOfCurrentAndLastX(motionX),
                (int) distanceOfCurrentAndLastY(motionY));
      //  Log.i(TAG, "Drag x=" + motionX + " y=" + motionY + " scrollX = " + mView.getScrollX() + "  scrollY = " + mView.getScrollY());

    }

    /**
     * 平滑滑动视图
     *
     * @param x
     * @param y
     */
    public void fling(float x, float y) {
        final VelocityTracker velocityTracker = mVelocityTracker;
        final ViewConfiguration configuration = ViewConfiguration.get(mView.getContext());
        velocityTracker.computeCurrentVelocity(1000, configuration.getScaledMaximumFlingVelocity());

        final int xVelocity = (int) VelocityTrackerCompat.getXVelocity(
                velocityTracker, mActivePointerId);
        final int yVelocity = (int) VelocityTrackerCompat.getYVelocity(
                velocityTracker, mActivePointerId);
        mScroller.fling(mView.getScrollX(), mView.getScrollY(), -xVelocity, -yVelocity,
                -1000, 3000, -1000, 3000);
        ViewCompat.postInvalidateOnAnimation(mView);
      //  Log.i(TAG, "Fling x=" + x + " y=" + y + " scrollX = " + mView.getScrollX() + "  scrollY = " + mView.getScrollY());
        // mScroller.startScroll(mView.getScrollX(), mView.getScrollY(), -dx, -dy, 1000);

    }


    /**
     * Scroller 通过该方法实现滑动
     */
    public void computerScroll() {
        final Scroller scroller = mScroller;
        if (!scroller.isFinished() && scroller.computeScrollOffset()) {
            int oldX = mView.getScrollX();
            int oldY = mView.getScrollY();
            int x = scroller.getCurrX();
            int y = scroller.getCurrY();
            if (oldX != x || oldY != y) {
               // Log.i(TAG, "Scroll To ( " + x + "," + y + " )");
                mView.scrollTo(x, y);
            }
            ViewCompat.postInvalidateOnAnimation(mView);
            return;
        }
    }
    @Override
    public boolean touch(MotionEvent event){
        int action = MotionEventCompat.getActionMasked(event);
        addVelocityMotionEvent(event);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                getScroller().abortAnimation();
        }
        if(super.touch(event)){
            return true;
        }
        return false;
    }
}
