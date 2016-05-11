package com.zhouztashin.android.enjoycrop.core;

import android.graphics.Canvas;

import com.zhouztashin.android.enjoycrop.core.mask.ColorMask;

/**
 * 在视图上面显示的遮罩层。通常均是覆盖在预览图片上的半透明遮罩层，目前也只实现了半透明样式的遮罩层 {@link ColorMask}
 *@author Zhozutashin
 *@version 1.0
 *@created  2016/3/15.
 */
public interface IMask {


    /**
     * 绘画具体的遮罩内容
     * @param layer 预览层
     * @param canvas 画布
     */
    void draw(ILayer layer, Canvas canvas);
}
