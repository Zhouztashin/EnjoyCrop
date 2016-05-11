package com.zhouztashin.android.enjoycrop.core.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.zhouztashin.android.enjoycrop.core.debug.L;

/**
 * ImageView手势触摸实现，通过Matrix来实现手势触摸，包括移动和放大缩小。
 * 需要特别注意的一点是，这里通过比较图片原中心点与当前的图片中心点来获取相应的滑动距离
 * @author Zhozutashin
 * @version 1.0
 * @created 2016/3/21
 * 使用指引：
 * 1、获取ImageMatrixTouchImpl实例
 * 2、在View构造函数中调用init()方法
 * 3、重写onTouchEvent事件，并调用touch方法处理手势触摸
 * 具体实现可以参照{@link EnjoyImageView}


 */
public class ImageMatrixTouchImpl extends com.zhouztashin.android.enjoycrop.core.image.BaseTouchImpl<ImageView> implements ScaleGestureDetector.OnScaleGestureListener {
    private final String TAG = ImageMatrixTouchImpl.class.getSimpleName();
    private ScaleGestureDetector mGestrueDetector;
    private boolean isScale;
    private double mScale;
    /**
     * 限制图片的范围
     */
    private RectF mRestrictRect;
    public ImageMatrixTouchImpl(ImageView v) {
        super(v);
        mGestrueDetector = new ScaleGestureDetector(mView.getContext(),this);
        mScale = 1;
    }



    public static ImageMatrixTouchImpl newInstance(ImageView v){
        return new ImageMatrixTouchImpl(v);
    }
    @Override
    public  void init(){
        mView.setScaleType(ImageView.ScaleType.MATRIX);
        postCenter();
    }

    public RectF getRestrictRect() {
        if(mRestrictRect ==null){
            return null;
        }
        return  new RectF(mRestrictRect);
    }

    public void setRestrictRect(RectF restrictRect) {
        this.mRestrictRect = restrictRect;
    }

    /**
     * 在屏幕中心显示,这里来自于ImageView的源码
     */
    private void postCenter(){
        mView.post(new Runnable() {
            @Override
            public void run() {
                if (mView.getDrawable() == null) {
                    return;
                }
                final int dwidth = mView.getDrawable().getIntrinsicWidth();
                final int dheight = mView.getDrawable().getIntrinsicHeight();

                final int vwidth = mView.getWidth() - mView.getPaddingLeft() - mView.getPaddingRight();
                final int vheight = mView.getHeight() - mView.getPaddingTop() - mView.getPaddingBottom();
                float scale;
                float dx = 0, dy = 0;

                if (dwidth * vheight > vwidth * dheight) {
                    scale = (float) vheight / (float) dheight;
                    dx = (vwidth - dwidth * scale) * 0.5f;
                } else {
                    scale = (float) vwidth / (float) dwidth;
                    dy = (vheight - dheight * scale) * 0.5f;
                }
                Matrix matrix= new Matrix();
                matrix.setScale(scale, scale);
                matrix.postTranslate(Math.round(dx), Math.round(dy));
                mView.setImageMatrix(matrix);
                mScale = scale;
            }
        });

    }


    private RectF getCurrentRectF(){
        BitmapDrawable drawable = (BitmapDrawable) mView.getDrawable();
        Bitmap bitmap  = drawable.getBitmap();
        RectF rectF = new RectF(0,0,bitmap.getWidth(),bitmap.getHeight());
        mView.getImageMatrix().mapRect(rectF);
        return rectF;
    }

    public double getScale() {
        return mScale;
    }


    @Override
    public void drag(float motionX, float motionY) {




        float moveX = motionX -mLastX;
        float moveY = motionY - mLastY;
        //边界限制
        if(mRestrictRect!=null){
            RectF rectF = getCurrentRectF();
            if(moveX>0){
                if(rectF.left+moveX>mRestrictRect.left){
                    moveX = mRestrictRect.left-rectF.left;
                }
            }else {
                if(rectF.right+moveX<mRestrictRect.right){
                    moveX = mRestrictRect.right- rectF.right;
                }
            }
            if(moveY>0){
                if(rectF.top+moveY>mRestrictRect.top){
                    moveY = mRestrictRect.top-rectF.top;
                }
            }else {
                if(rectF.bottom+moveY<mRestrictRect.bottom){
                    moveY = mRestrictRect.bottom- rectF.bottom;
                }
            }
        }
        mView.getImageMatrix().postTranslate(moveX,moveY);
      //  Log.i(TAG, "Matrix Move: motionX" + motionX + " motionY" + motionY);
      //  Log.i(TAG, "Matrix Move: mLastX" + mLastX+ " mLastY" + mLastY);
            Log.i(TAG,"moveX"+moveX+" moveY"+moveY);
        mView.invalidate();
    }






    public float getScrollY() {


        double sourceCenterY = (mView.getTop()+mView.getBottom())*1.0/2;
        double afterCenterY = getCurrentRectF().centerY();
        return (float) (afterCenterY-sourceCenterY);
    }




    public float getScrollX() {

        double sourceCenterX = (mView.getLeft()+mView.getRight())*1.0/2;
        int afterCenterX = (int) getCurrentRectF().centerX();
        return (float) (afterCenterX-sourceCenterX);
    }

    @Override
    public void fling(float motionX, float motionY) {

    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        //初始化缩放值
        float px = detector.getFocusX();
        float py = detector.getFocusY();
        float scaleFactor= detector.getScaleFactor();

        //如果存在边界限制
        if(mRestrictRect!=null){
            Matrix matrixAfter = new Matrix(mView.getImageMatrix());
            matrixAfter.postScale(detector.getScaleFactor(),detector.getScaleFactor(),detector.getFocusX(),detector.getFocusY());
            final BitmapDrawable drawable = (BitmapDrawable) mView.getDrawable();
            final Bitmap bitmap  = drawable.getBitmap();
            RectF rectF = new RectF(0,0,bitmap.getWidth(),bitmap.getHeight());
            matrixAfter.mapRect(rectF);

            boolean isLeftLimit = false ,isRightLimit = false ,isTopLimit = false ,isBottomLimit =false;
            //重新计算缩放中心点
            if(rectF.left>mRestrictRect.left){
                px = mRestrictRect.left;
                isLeftLimit = true;
            }
            if(rectF.right<mRestrictRect.right){
                px = mRestrictRect.right;
                isRightLimit = true;
            }
            if(rectF.top>mRestrictRect.top){
              py = mRestrictRect.top;
                isTopLimit = true;
            }
            if(rectF.bottom<mRestrictRect.bottom){
                py = mRestrictRect.bottom;
                isBottomLimit = true;
            }
            //左右两边或者上下两边都无法缩放,就直接返回
            if((isRightLimit&&isLeftLimit)||(isTopLimit&&isBottomLimit)){
                return true;
            }
            //重新计算允许的最小缩放倍数
            //计算公式是: 结果坐标(ResultX) = 缩放前坐标(Before X)*缩放倍数(scale)+中心点坐标(center X)*(1-缩放倍数(scale))

            float maxScaleLeft = (mRestrictRect.left-px)/(getCurrentRectF().left-px);
            if(scaleFactor<maxScaleLeft){
                scaleFactor = maxScaleLeft;
            }
            float maxScaleRight = (mRestrictRect.right-px)/(getCurrentRectF().right-px);
            if(scaleFactor<maxScaleRight){
                scaleFactor = maxScaleRight;
            }
            float maxScaleTop = (mRestrictRect.top-py)/(getCurrentRectF().top-py);
            if(scaleFactor<maxScaleTop){
                scaleFactor = maxScaleTop;
            }
            float maxSacleBottom = (mRestrictRect.bottom-py)/(getCurrentRectF().bottom-py);
            if(scaleFactor<maxSacleBottom){
                scaleFactor = maxSacleBottom;
            }
        }
        mScale=mScale*scaleFactor;
        mView.getImageMatrix().postScale(scaleFactor,scaleFactor,
                px,py);
        L.error("scale:"+scaleFactor+" ("+px+","+py+")");
        mView.invalidate();
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        isScale = true;
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        isScale = false;
        //重新计算移动
    }
    @Override
    public boolean touch(MotionEvent event){
        mGestrueDetector.onTouchEvent(event);
        if(!isScale){
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
                 //   Log.i(TAG,"Drag("+x+","+y+")");

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
        }
        return false;

    }


}
