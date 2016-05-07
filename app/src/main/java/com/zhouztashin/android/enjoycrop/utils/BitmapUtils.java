package com.zhouztashin.android.enjoycrop.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Zhouztashin on 2015/7/24.
 */
public class BitmapUtils {

    /**
     * 保存bitmap
     *
     * @param context
     * @param bitmap
     * @return
     */
    public static boolean saveBitmaps(Context context, Bitmap bitmap, File newFile) {
        try {

            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
                    newFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static Bitmap resizeBitmap(String bmpFilePath, int requestedWidth,
                                      int requestedHeight) {
        if (requestedWidth <= 0 || requestedHeight <= 0) {
            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeFile(bmpFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bmp;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();

        // 获得图片的宽高
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(bmpFilePath, options);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        final int srcWidth = options.outWidth;
        final int srcHeight = options.outHeight;

        options.inSampleSize = (int) getScale(srcWidth, srcHeight,
                requestedWidth, requestedHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(bmpFilePath, options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bitmap == null) {
            return null;
        }

        // 个别机型，用以上方法的压缩率不符合要求，所以再次精确压缩
        if (bitmap.getWidth() > requestedWidth
                || bitmap.getHeight() > requestedHeight) {
            bitmap = resizeBitmapInOldWay(bitmap, requestedWidth,
                    requestedHeight);
        }
        return bitmap;

    }

    /**
     * 统一计算缩放比例
     *
     * @param srcWidth
     * @param requestedWidth
     * @return
     */
    public static float getScale(int srcWidth, int srcHeight,
                                 int requestedWidth, int requestedHeight) {
        float scale = 1;
        if (requestedWidth <= 0 && requestedHeight <= 0) {
            // 不做任何缩放
            scale = 1;
        } else if (requestedWidth > 0 && requestedHeight > 0) {

            float scaleWidth = srcWidth * 1.0f / requestedWidth;
            float scaleHeight = srcHeight * 1.0f / requestedHeight;
            if (scaleWidth < scaleHeight) {
                scale = scaleHeight;
            } else {
                scale = scaleWidth;
            }
        } else if (requestedWidth > 0 && requestedHeight <= 0) {
            float scaleWidth = srcWidth * 1.0f / requestedWidth;
            scale = scaleWidth;
        } else if (requestedWidth <= 0 && requestedHeight > 0) {
            float scaleHeight = srcHeight * 1.0f / requestedHeight;
            scale = scaleHeight;
        }

        return scale;
    }
    public static Bitmap resizeBitmapInOldWay(Bitmap bitmap, int maxWidth,
                                              int maxHeight) {
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // matrix的缩小参数应该为getScale的倒数(小数)
        float scale = 1 / getScale(width, height, maxWidth, maxHeight);
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片
        Bitmap newbm = null;
        try {
            newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                    true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newbm;
    }
}
