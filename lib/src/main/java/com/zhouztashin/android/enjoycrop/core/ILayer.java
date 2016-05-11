package com.zhouztashin.android.enjoycrop.core;

/**
 * 预览层，集成了{@link IShape}与{@link IMask}，提供完整的预览功能。
 * @author Zhouztashin
 * @version 1.0
 * @created  2016/3/15.
 */
public interface ILayer {


    /**
     * 预览层的宽度，一般是该视图的宽度
     * @return
     */
    int width();


    /**
     * 预览层的高度，一般是该视图的高度
     * @return
     */
    int height();

    /**
     * 左边位置
     * @return
     */
    int left();

    /**
     * 右边位置
     * @return
     */
    int right();

    /**
     * 顶点位置
     * @return
     */
    int top();

    /**
     * 底部位置
     * @return
     */
    int bottom();


}
