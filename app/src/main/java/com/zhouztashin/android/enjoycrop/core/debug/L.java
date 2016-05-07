package com.zhouztashin.android.enjoycrop.core.debug;

import android.graphics.Paint;
import android.util.Log;

/**
 * Created by Administrator on 2016/4/22.
 */
public class L {
    private final static String TAG = "EnjoyCrop";
    private final  static boolean LOG_ENABLE = true;
    public static void error(String message){
        if(LOG_ENABLE){
            Log.e(TAG,message+"");
        }
    }
}
