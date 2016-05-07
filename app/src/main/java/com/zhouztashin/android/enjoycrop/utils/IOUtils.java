

package com.zhouztashin.android.enjoycrop.utils;

import android.database.Cursor;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;

/**
 * IO处理工具类
 *
 * <ur>
 *     <li>{@link #closeQuietly(java.io.Closeable)} 关闭数据流</li>
 *     <li>{@link #closeQuietly(android.database.Cursor)} 关闭数据库游标</li>
 *     <li>{@link #copyStream(java.io.InputStream, java.io.OutputStream)} 复制流，默认缓冲为 8 * 1024</li>
 *     <li>{@link #copyStream(java.io.InputStream, java.io.OutputStream, int)} 复制流，自定义缓冲大小</li>
 *     <li>{@link #toByteArray(java.io.InputStream)} 将input流转为byte数组，自动关闭</li>
 * </ur>
 */
public class IOUtils {
    private IOUtils() {}

    static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

    /**
     * 关闭流
     * @param closeable 实现了{@link java.io.Closeable} 的类,像{@link java.io.InputStream},
     *              {@link java.io.OutputStream}...
     * */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
            }
        }
    }

    /**
     * 关闭数据库的游标
     * @param cursor 数据库游标
     */
    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable e) {
            }
        }
    }

    /**
     * 将输入流写出到输出流
     *
     * @param is  输入流
     * @param out  输出流
     * @throws java.io.IOException
     * */
    public static void copyStream(InputStream is, OutputStream out) throws IOException {
        copyStream(is, out, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 将输入流写出到输出流
     *
     * @param is 输入流
     * @param out 输出流
     * @param buffer_size 缓存区大小
     * @throws java.io.IOException
     * */
    public static void copyStream(InputStream is, OutputStream out, int buffer_size) throws IOException {
        byte[] buffer = new byte[buffer_size];
        int offset = 0;
        while ((offset = is.read(buffer)) != -1) {
            out.write(buffer, 0, offset);
        }
        out.flush();
    }

    /**
     * 将input流转为byte数组，自动关闭
     *
     * @param in 输入流
     * @return
     */
    public static byte[] toByteArray(InputStream in) throws Exception {
        if (in == null) {
            return null;
        }
        ByteArrayOutputStream output = null;
        byte[] result = null;
        try {
            output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buffer))) {
                output.write(buffer, 0, n);
            }
            result = output.toByteArray();
        } finally {
            closeQuietly(in);
            closeQuietly(output);
        }
        return result;
    }

    /**
     * 将输入流转为字符串
     * @param is 输入流
     * */
    public static String toString(InputStream is) {
        InputStreamReader reader = null;
        try {
            StringWriter writer = new StringWriter();
            char[] buffer = new char[1024];
            int count;
            reader = new InputStreamReader(is);
            while ((count = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, count);
            }
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeQuietly(reader);
        }
        return null;
    }
}
