package com.star.demo.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Sym on 2016/1/18.
 */
public class BitmapUtil {

    /**
     * 调整图片的大小 (先将图片缩小或者放大，然后对图片进行裁剪，让不同的图片都能占满不同的手机尺寸的屏幕)
     * @param boxWidth
     * @param boxHeight
     * @param bitmap 原图
     * @return
     */
    public static Bitmap resizeBitmap(int boxWidth, int boxHeight, Bitmap bitmap) {

        float scaleX = ((float) boxWidth) / ((float) bitmap.getWidth());
        float scaleY = ((float) boxHeight) / ((float) bitmap.getHeight());
        float scale = 1.0f;

        if ((scaleX >= scaleY && scaleY >= 1.0f) || (scaleX > scaleY && scaleX < 1.0f) || (scaleX >= 1.0f && scaleY < 1.0f)) {
            scale = scaleX;
        }
        if ((scaleY > scaleX && scaleX >= 1.0f) || (scaleY > scaleX && scaleY < 1.0f) || (scaleX < 1.0f && scaleY >= 1.0f)) {
            scale = scaleY;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Bitmap alterBitmap = Bitmap.createBitmap(newBitmap, 0, 0, boxWidth, boxHeight);
        newBitmap = null;
        return alterBitmap;
    }

}
