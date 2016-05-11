package com.zhouztashin.android.enjoycrop.core.clippath;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.zhouztashin.android.enjoycrop.core.ILayer;
import com.zhouztashin.android.enjoycrop.core.IShape;

/**
 * 采用Canvas.clipPath(..)方案实现裁剪功能的形状接口
 * @author Zhouztashin
 * @version 1.0
 * @created 2016/3/15
 */
public interface IClipPathShape extends IShape{

    /**
     * 这里实质上是创建了具体的形状
     * @param layer 预览层
     * @return 返回带有实际绘画形状的Path引用,以便预览层可以显示定义的形状。
     */
    Path createShapePath(ILayer layer);

    /**
     * 创建形状对应的画笔
     * @return 返回形状对应的画笔
     */
    Paint createShapePaint();

}
