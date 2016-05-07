package com.zhouztashin.android.enjoycrop;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zhouztashin.android.enjoycrop.core.BaseLayerView;
import com.zhouztashin.android.enjoycrop.core.ILayer;
import com.zhouztashin.android.enjoycrop.core.IMask;
import com.zhouztashin.android.enjoycrop.core.IShape;
import com.zhouztashin.android.enjoycrop.core.clippath.ClipPathCircle;
import com.zhouztashin.android.enjoycrop.core.clippath.ClipPathLayerView;
import com.zhouztashin.android.enjoycrop.core.clippath.ClipPathSquare;
import com.zhouztashin.android.enjoycrop.core.debug.L;
import com.zhouztashin.android.enjoycrop.core.image.EnjoyImageView;
import com.zhouztashin.android.enjoycrop.core.mask.BitmapMask;
import com.zhouztashin.android.enjoycrop.core.mask.ColorMask;
import com.zhouztashin.android.enjoycrop.core.porterduff.BitmapPorterShape;
import com.zhouztashin.android.enjoycrop.core.porterduff.PorterDuffCircle;
import com.zhouztashin.android.enjoycrop.core.porterduff.PorterDuffLayerView;
import com.zhouztashin.android.enjoycrop.utils.BitmapUtils;
import com.zhouztashin.android.enjoycrop.utils.FileUtils;

import java.io.File;
import java.util.Calendar;

/**裁剪功能主要容器
 * @author Zhozutashin
 * @version 1.0
 * @created 2016/3/15
 */
public class EnjoyCropLayout extends FrameLayout{
    private BaseLayerView mLayerView;
    private EnjoyImageView mImageView;

    /**
     * 是否限制边界
     */
    private boolean isRestrict;
    private int mFillColor ;
    public EnjoyCropLayout(Context context) {
        super(context);
        init();
    }

    public EnjoyCropLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 是否限制边界
     * @return
     */
    public boolean isRestrict() {
        return isRestrict;
    }

    /**
     * 限制边界
     * @param restrict　
     */
    public void setRestrict(boolean restrict) {
        isRestrict = restrict;
        if(isRestrict){
            setRestrict();
        }else{
            mImageView.setRestrictBound(null);
        }
    }

    public int getFillColor() {
        return mFillColor;
    }

    /**
     * 设置超出边界时候的填充颜色
     * @param fillColor
     */
    public void setFillColor(int fillColor) {
        this.mFillColor = fillColor;
    }

    /**
     * 初始化裁剪所需组件，并且添加到容器中
     */

    private void init(){
        mImageView = new EnjoyImageView(getContext());
        mImageView.setScaleType(ImageView.ScaleType.MATRIX);
        mFillColor = Color.BLACK;
    }
    public void setLayerView(BaseLayerView layerView){
        if(mLayerView!=null){
            removeView(mLayerView);
        }
        if(mImageView!=null){
            removeView(mImageView);
        }
        addView(mImageView);
        addView(layerView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mLayerView = layerView;
    }


    private void setRestrict(){

        post(new Runnable() {
            @Override
            public void run() {
                if(mLayerView==null){
                    return;
                }
                int centerX = (mLayerView.getLeft()+mLayerView.getRight())/2;
                int centerY = (mLayerView.getTop()+mLayerView.getBottom())/2;
                int shapeLeft = centerX- mLayerView.getShape().width()/2;
                int shapeRight = centerX+mLayerView.getShape().width()/2;
                int shapeTop = centerY-mLayerView.getShape().height()/2;
                int shapeBottom = centerY+mLayerView.getShape().height()/2;
                RectF rectF = new RectF(shapeLeft,shapeTop,shapeRight,shapeBottom);
                mImageView.setRestrictBound(rectF);
            }
        });


    }


    /**
     * 设置将裁剪的图片
     * @param resId
     */
    public void setImageResource(int resId){
        mImageView.setImageResource(resId);
    }

    /**
     * 设置将裁剪的图片
     * @param bitmap
     */
    public void setImage(Bitmap bitmap){
        mImageView.setImageBitmap(bitmap);
    }

    /**
     * 给第三方图片加载提供引用
     * @return
     */
    public ImageView getImageView(){
        return mImageView;
    }


    /**
     * 裁剪
     */
    public Bitmap crop(){

        if(mLayerView ==null){
            L.error(" layerView is null");
        }
        if(mImageView.getDrawable()==null){
            L.error("ImageView 'drawable is null");
            return null;
        }
        if(!(mImageView.getDrawable() instanceof BitmapDrawable)){
            L.error("only support the type of BitmapDrawable");
            return null;
        }
        IShape iShape = mLayerView.getShape();
        if(iShape==null){
            L.error("shape is null");
            return null;
        }

        BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
        Bitmap bitmap  = drawable.getBitmap();
        if(bitmap==null){
            L.error("bitmap is null");
            return null;
        }
        //以下所有步骤的思路，均是将点或者大小还原到加载图片大小比例后，再进行处理。
        int cropLeft=0,cropTop = 0;
        double scale = mImageView.getScale();
        //获取裁剪区域的实际长宽
        int actuallyWidth = (int) (iShape.width()*1.0/scale)                                                   ;
        int actuallyHeight = (int) (iShape.height()*1.0/scale);
        //重新计算得出最终裁剪起始点
        cropLeft = (int) (bitmap.getWidth()/2-mImageView.getActuallyScrollX()/scale-actuallyWidth/2);
        cropTop = (int) (bitmap.getHeight()/2-mImageView.getActuallyScrollY()/scale - actuallyHeight/2);

      //  Bitmap newBitmap = Bitmap.createBitmap(bitmap,centerX,centerY,actuallyWidth,actuallyHeight);
        //保存的图片
        Bitmap saveBitmap = Bitmap.createBitmap(actuallyWidth,actuallyHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(saveBitmap);
        //当裁剪超出图片边界，超出区域以颜色填充
        canvas.drawColor(mFillColor);
        //计算显示与实际裁剪的大小
        int showRight = actuallyWidth;
        int showBottom = actuallyHeight;
        int cropRight = cropLeft+actuallyWidth;
        int cropBottom =  cropTop+actuallyHeight;
        //裁剪超出图片边界超出边界
        if(cropRight>bitmap.getWidth()){
            cropRight = bitmap.getWidth();
            showRight = bitmap.getWidth()-cropLeft;
        }
        if(cropBottom>bitmap.getHeight()){
            cropBottom = bitmap.getHeight();
            showBottom = bitmap.getHeight()-cropTop;
        }
        Rect cropRect = new Rect(cropLeft,cropTop,cropRight,cropBottom);
        Rect showRect = new Rect(0,0,showRight,showBottom);
        canvas.drawBitmap(bitmap,cropRect,showRect,new Paint());

        return saveBitmap;
    /*    String folder = Environment.getExternalStorageDirectory().getPath()+"/"+"11EnjoyCrop";
        FileUtils.createFolder( folder,FileUtils.MODE_UNCOVER);
        String fileName= folder+ File.separator+ Calendar.getInstance().getTime().toString()+".jpg";
        FileUtils.createFile(fileName,FileUtils.MODE_COVER);
        BitmapUtils.saveBitmaps(getContext(),saveBitmap,new File(fileName));*/

    }

    /**
     * 设置默认的半透明背景，方形预览框
     * @param squareWidth 方形预览框宽度
     */
    public void setDefaultSquareCrop(int squareWidth){

        BaseLayerView layerView = new ClipPathLayerView(getContext());
        layerView.setMask(ColorMask.getTranslucentMask());
        layerView.setShape(new ClipPathSquare(squareWidth));
        setLayerView(layerView);
        setRestrict(true);
    }

    /**
     * 设置默认的半透明背景，圆形预览框
     * @param radius 圆形预览框半径
     */
    public void setDefaultCircleCrop(int radius){

        BaseLayerView layerView = new ClipPathLayerView(getContext());
        layerView.setMask(ColorMask.getTranslucentMask());
        layerView.setShape(new ClipPathCircle(radius));
        setLayerView(layerView);
        setRestrict(true);
    }


}
