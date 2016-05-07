package com.zhouztashin.android.enjoycrop.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zhouztashin on 2015/8/31.
 */
public class StringUtils {

    private   final   static DecimalFormat nf   =   new DecimalFormat( "###,##0.00 ");


    public static boolean isEmailAddress(String strEmail) {
        String strPattern = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        if(m.matches()){
            return true;
        }
        return false;
    }

    /**
     *
     * @return 字符串中所含中文字的数目
     */
    public static int getChineseCharNumber(String text){
        int count = 0;
        String ChineseRegEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(ChineseRegEx);
        Matcher m = p.matcher(text);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        return count;
    }

    /**
     * @return 字符串中所含英文字符或数字的数目
     */
    public static int getEngllishOrDigitNumber(String text){
        int count = 0;
        String regEx = "[A-Za-z0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        return count;
    }


    public static String formatDecimal(float value){
        return nf.format(value);
    }
    public static String getSMSCode(String str){
        if(str ==null||!str.contains("作业通")) return null;

        int start = str.indexOf("：");
        int end = str.indexOf(",");
        if(start>0&&end>0){
            return str.substring(start+1,end);
        }
        return null;
    }

    /**
     * 格式化文件路径（去除一些特殊字符）
     *
     * @param filePath
     * @return
     */
    public static String formatFilePath(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        return filePath.replace("\\", "").replace("/", "").replace("*", "").replace("?", "").replace(":", "").replace("\"", "").replace("<", "").replace(">", "").replace("|", "");
    }

    /**
     * 单位换算
     *
     * @param fileSize
     * @return
     */
    public static String getSizeText(long fileSize) {
        if (fileSize <= 0) {
            return "0.0M";
        }
        if (fileSize > 0 && fileSize < 100 * 1024) {
            // 大于0小于100K时，直接返回“0.1M”
            return "0.1M";
        }
        float result = fileSize;
        String suffix = "M";
        result = result / 1024 / 1024;
        return String.format("%.1f", result) + suffix;
    }

}
