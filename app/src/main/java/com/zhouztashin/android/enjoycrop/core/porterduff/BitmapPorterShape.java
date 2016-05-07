package com.zhouztashin.android.enjoycrop.core.porterduff;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

import com.zhouztashin.android.enjoycrop.core.ILayer;
import com.zhouztashin.android.enjoycrop.core.debug.L;

/**图片预览框,主要这里镂空的图片必须是透明样式的png图片，否则可能会导致达不到想要的效果,这里会保留透明值，并镂空非透明区域。
 * @author Zhouztashin
 * @version  1.0
 * @created  2016/5/4
 */
public class BitmapPorterShape implements IPorterDuffShape {
    private Context mContext;
    private int mWidth;
    private int mHeight ;
    private int mResId;
    public BitmapPorterShape(Context c){
        mContext = c;
    }

    /**
     * 设置的预览的Drawable 资源ID
     * @param resId 资源ID
     */
    public void setResId(int resId){
        mResId = resId;
    }
    @Override
    public void draw(ILayer layer, CanvasWrapper canvasWrapper) {

        //图片镂空需要设置为图片镂空模式
        canvasWrapper.setBitmapMode();
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),mResId);
        if(bitmap==null){
            L.error("BitmapPorterShape must set a bitmap");
        }
        mWidth = bitmap.getWidth();
        mHeight = bitmap.getHeight();
        int left = layer.width()/2- mWidth /2;
        int top = layer.height()/2-mWidth /2;
        canvasWrapper.drawBitmap(bitmap,left,top,new Paint());
        bitmap.recycle();
    }

    @Override
    public int width() {
        return mWidth*2;
    }

    @Override
    public int height() {
        return mHeight*2;
    }
}
