package com.zhouztashin.android.enjoycrop.core.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.zhouztashin.android.enjoycrop.core.debug.L;

/**支持缩放功能、拖动功能的ImageView
 * @author Zhouztashin
 * @version 1.0
 * @created 2016/3/17
 */
public class EnjoyImageView extends ImageView {
    private final String TAG = EnjoyImageView.class.getSimpleName();
    private ImageMatrixTouchImpl mImageToucheHandler;
    public EnjoyImageView(Context context) {
        super(context);
        init();
    }


    public EnjoyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private  void init(){
        mImageToucheHandler = ImageMatrixTouchImpl.newInstance(this);
        mImageToucheHandler.init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }



    public double getScale(){
        return mImageToucheHandler.getScale();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mImageToucheHandler.touch(event);
        if(result){
            return result;
        }else{
            return super.onTouchEvent(event);
        }
    }
    public float getActuallyScrollX(){
        return getScrollX()+mImageToucheHandler.getScrollX();
    }
    public float getActuallyScrollY(){
        return getScrollY()+mImageToucheHandler.getScrollY();
    }

    /**
     * 设置图片移动放大边界
     * @param rectF
     * @return
     */
    public void setRestrictBound(RectF rectF){
        mImageToucheHandler.setRestrictRect(rectF);
    }
}
