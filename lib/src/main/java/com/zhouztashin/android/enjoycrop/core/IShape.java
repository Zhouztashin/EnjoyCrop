package com.zhouztashin.android.enjoycrop.core;

/**
 * 裁剪框接口,其返回的长宽会是最终裁剪的裁剪大小.具体实现方式参照{@link com.zhouztashin.android.enjoycrop.core.clippath.ClipPathCircle}
 * @author Zhouztashin
 * @version 1.0
 *  * @created  2016/4/22.
 */
public interface  IShape  {

    /**
     * 裁剪框宽度
     * @return
     */
    int width();

    /**
     * 裁剪框高度
     * @return
     */
    int height();

}
