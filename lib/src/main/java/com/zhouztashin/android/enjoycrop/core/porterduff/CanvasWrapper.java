package com.zhouztashin.android.enjoycrop.core.porterduff;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Canvas 装饰类,实质上是装饰Paint，设置PorterDuffXfermode属性，达到镂空的效果。
 * @author Zhouztashin
 * @version 1.0
 * @created 2016/3/15
 */
public class CanvasWrapper {
    private Canvas mCanvas;
    private static PorterDuffXfermode sMode  = new PorterDuffXfermode(PorterDuff.Mode.SRC);

    public CanvasWrapper(Canvas canvas) {
        mCanvas = canvas;
    }
    public boolean isOpque(){
        return mCanvas.isOpaque();
    }
    public int getWidth(){
        return mCanvas.getWidth();
    }
    public int getHeight(){
        return mCanvas.getHeight();
    }
    public int getDensity(){
        return mCanvas.getDensity();
    }
    public void setDensity(int density){
        mCanvas.setDensity(density);
    }
    public void save(){
        mCanvas.save();
    }
    public void restore(){
        mCanvas.restore();
    }
    public void translate(float dx, float dy){
        mCanvas.translate(dx, dy);
    }
    public void scale(float sx, float sy) {
        mCanvas.scale(sx, sy);
    }
    public final void scale(float sx, float sy, float px, float py) {
        mCanvas.scale(sx, sy, px, py);
    }
    public void rotate(float degrees){
        mCanvas.rotate(degrees);
    }

    public final void rotate(float degrees, float px, float py) {
        mCanvas.rotate(degrees,px,py);
    }
    public boolean clipRect(@NonNull RectF rect, @NonNull Region.Op op) {
        return mCanvas.clipRect(rect,op);
    }
    public boolean clipRect(@NonNull RectF rect) {
        return mCanvas.clipRect(rect);
    }
    public boolean clipRect(float left, float top, float right, float bottom,
                            @NonNull Region.Op op) {
        return mCanvas.clipRect(left,top,right,bottom,op);
    }
    public boolean clipRect(float left, float top, float right, float bottom) {
        return mCanvas.clipRect(left, top, right, bottom);
    }
    public boolean clipPath(@NonNull Path path, @NonNull Region.Op op) {
        return mCanvas.clipPath(path, op);
    }
    public boolean clipPath(@NonNull Path path) {
        return mCanvas.clipPath(path);
    }

    public void setDrawFilter(@Nullable DrawFilter filter) {
        mCanvas.setDrawFilter(filter);
    }

    public boolean quickReject(@NonNull RectF rect, @NonNull Canvas.EdgeType type) {
        return mCanvas.quickReject(rect,type);
    }
    public boolean quickReject(@NonNull Path path, @NonNull Canvas.EdgeType type) {
        return mCanvas.quickReject(path,type);
    }
    public boolean quickReject(float left, float top, float right, float bottom,
                               @NonNull Canvas.EdgeType type) {
        return mCanvas.quickReject(left, top, right, bottom, type);
    }
    public boolean getClipBounds(@Nullable Rect bounds) {
        return mCanvas.getClipBounds(bounds);
    }
    public final @NonNull Rect getClipBounds() {
        return mCanvas.getClipBounds();
    }
    public void drawRGB(int r, int g, int b){
        mCanvas.drawRGB(r, g, b);
    }
    public void drawARGB(int a, int r, int g, int b){
        mCanvas.drawARGB(a, r, g, b);
    }
    public void drawColor( int color) {
        mCanvas.drawColor(color);
    }
    public void drawPoints( float[] pts, int offset, int count,
                           @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawPoints(pts, offset, count, paint);
    }
    public void drawPoints(@NonNull float[] pts, @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawPoints(pts, paint);
    }

    public void drawPoint(float x, float y, @NonNull Paint paint) {
        mCanvas.drawPoint(x, y, paint);
    }
    public void drawLine(float startX, float startY, float stopX, float stopY,
                         @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawLine(startX, startY, stopX, startY, paint);
    }

    public void drawLines(float[] pts, int offset, int count, Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawLines(pts, offset, count, paint);
    }
    public void drawLines( @NonNull float[] pts, @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawLines(pts, paint);
    }
    public void drawRect(@NonNull RectF rect, @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawRect(rect, paint);
    }
    public void drawRect(@NonNull Rect r, @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawRect(r,paint);
    }

    public void drawRect(float left, float top, float right, float bottom, @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawRect(left, top, right, bottom, paint);
    }
    public void drawOval(@NonNull RectF oval, @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawOval(oval, paint);
    }

    public void drawCircle(float cx, float cy, float radius, @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawCircle(cx, cy, radius, paint);
    }
    public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter,
                        @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
    }

    public void drawRoundRect(@NonNull RectF rect, float rx, float ry, @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawRoundRect(rect, rx, ry, paint);
    }

    public void drawPath(@NonNull Path path, @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawPath(path, paint);
    }
    public void drawBitmap(@NonNull Bitmap bitmap, float left, float top, @Nullable Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawBitmap(bitmap, left, top, paint);
    }
    public void drawBitmap(@NonNull Bitmap bitmap, @Nullable Rect src, @NonNull RectF dst,
                           @Nullable Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawBitmap(bitmap,src,dst,paint);
    }
    public void drawBitmap(@NonNull Bitmap bitmap, @NonNull Matrix matrix, @Nullable Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawBitmap(bitmap, matrix, paint);
    }
    public void drawBitmapMesh(@NonNull Bitmap bitmap, int meshWidth, int meshHeight,
                               @NonNull float[] verts, int vertOffset, @Nullable int[] colors, int colorOffset,
                               @Nullable Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawBitmapMesh(bitmap, meshWidth, meshHeight
                , verts, vertOffset, colors, colorOffset, paint);
    }
    public void drawVertices(@NonNull Canvas.VertexMode mode, int vertexCount, @NonNull float[] verts,
                             int vertOffset, @Nullable float[] texs, int texOffset, @Nullable int[] colors,
                             int colorOffset, @Nullable short[] indices, int indexOffset, int indexCount,
                             @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawVertices(mode, vertexCount, verts, vertOffset, texs, texOffset, colors,
                colorOffset, indices, indexOffset, indexCount, paint);
    }
    public void drawText(@NonNull char[] text, int index, int count, float x, float y,
                         @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawText(text, index, count, x, y, paint);
    }
    public void drawText(@NonNull String text, float x, float y, @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawText(text,x,y,paint);
    }
    public void drawText(@NonNull String text, int start, int end, float x, float y,
                         @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawText(text,start,end,x,y,paint);
    }
    public void drawText(@NonNull CharSequence text, int start, int end, float x, float y,
                         @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawText(text, start, end, x, y, paint);
    }

    public void drawTextOnPath(@NonNull char[] text, int index, int count, @NonNull Path path,
                               float hOffset, float vOffset, @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawTextOnPath(text, index, count, path, hOffset, vOffset, paint);
    }
    public void drawTextOnPath(@NonNull String text, @NonNull Path path, float hOffset,
                               float vOffset, @NonNull Paint paint) {
        wrapperPaint(paint);
        mCanvas.drawTextOnPath(text, path, hOffset, vOffset, paint);
    }
    public void drawPicture(@NonNull Picture picture) {
        mCanvas.drawPicture(picture);
    }

    public void drawPicture(@NonNull Picture picture, @NonNull RectF dst) {
        mCanvas.drawPicture(picture,dst);
    }
    public void drawPicture(@NonNull Picture picture, @NonNull Rect dst) {
        mCanvas.drawPicture(picture,dst);
    }

    /**
     * 设置为图片镂空模式
     */
    public void setBitmapMode(){
        sMode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }
    private void wrapperPaint(Paint paint){
            paint.setXfermode(sMode);
    }

}