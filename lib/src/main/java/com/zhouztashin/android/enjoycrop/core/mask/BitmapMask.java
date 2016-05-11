package com.zhouztashin.android.enjoycrop.core.mask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.zhouztashin.android.enjoycrop.core.ILayer;
import com.zhouztashin.android.enjoycrop.core.IMask;

/**
 * 图片遮罩层
 *@author Zhoztaashin
 *@version 1.0
 *@created 2016/3/16.
 */
public class BitmapMask implements IMask {
    private final Context mContext;
    private int mResId;
    public BitmapMask(Context c){
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
    public void draw(ILayer layer, Canvas c) {
        Rect rect = new Rect();
        rect.left =layer.left();
        rect.right = layer.right();
        rect.top = layer.top();
        rect.bottom = layer.bottom();
        Bitmap bitmap =BitmapFactory.decodeResource(mContext.getResources(), mResId);
        c.drawBitmap(bitmap,null,rect,null);
        bitmap.recycle();

    }

}
