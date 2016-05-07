package com.zhouztashin.android.enjoycrop.core.porterduff;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.zhouztashin.android.enjoycrop.core.BaseLayerView;
import com.zhouztashin.android.enjoycrop.core.IMask;
import com.zhouztashin.android.enjoycrop.core.debug.L;

/**
 * Created by Zhouztashin on 2016/3/15.
 * 这是一个利用PorterDuffXferMode来实现交叉打洞功能的集成层，该视图集合了形状和遮罩
 * 如果遮挡层是图像的话，使用这个视图会比较合理.
 * 注意：使用这个集成层会导致内存占有率显著提高。
 */
public  class PorterDuffLayerView extends BaseLayerView {


    public PorterDuffLayerView(Context context) {
        super(context);
    }

    public PorterDuffLayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(mShape==null){
            L.error("Shape can not be null");
        }
        if(!(mShape instanceof IPorterDuffShape)){
            L.error("Shape must be subclass of IPorterDuffShape");
        }
        Bitmap bitmap = Bitmap.createBitmap(width(), height(), Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(bitmap);
        if(mMask!=null){
            mMask.draw(this,c);
        }
        if(mShape!=null&&mShape instanceof IPorterDuffShape){
            ((IPorterDuffShape)mShape).draw(this,new CanvasWrapper(c));
        }
        canvas.drawBitmap(bitmap,0,0,null);
        bitmap.recycle();
        System.gc();
    }


}
