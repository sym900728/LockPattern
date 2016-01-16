package com.star.lockpattern.util;

import android.content.Context;

import com.star.lockpattern.widget.LockPatternView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sym on 2016/1/16.
 */
public class LockPatternUtil {

    /**
     * change string value (ex: 10.0dip => 20) to int value
     *
     * @param context
     * @param value
     * @return
     */
    @Deprecated
    public static int changeSize(Context context, String value) {
        if (value.contains("dip")) {
            float dip = Float.valueOf(value.substring(0, value.indexOf("dip")));
            return LockPatternUtil.dip2px(context, dip);
        } else if (value.contains("px")) {
            float px = Float.valueOf(value.substring(0, value.indexOf("px")));
            return (int) px;
        } else if (value.contains("@")) {
            float px = context.getResources().getDimension(Integer.valueOf(value.replace("@", "")));
            return (int) px;
        }
        else {
            throw new IllegalArgumentException("can not use wrap_content " +
                    "or match_parent or fill_parent or others' illegal parameter");
        }
    }

    /**
     * dip to px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * check the touch cell is or not in the circle
     *
     * @param sx
     * @param sy
     * @param r      the radius of circle
     * @param x      the x position of circle's center point
     * @param y      the y position of circle's center point
     * @param offset the offset to the frame of the circle
     *               (if offset > 0 : the offset is inside the circle; if offset < 0 : the offset is outside the circle)
     * @return
     */
    public static boolean checkInRound(float sx, float sy, float r, float x, float y, float offset) {
        return Math.sqrt((sx - x + offset) * (sx - x + offset) + (sy - y + offset) * (sy - y + offset)) < r;
    }

    /**
     * get distance between two points
     *
     * @param fpX first point x position
     * @param fpY first point y position
     * @param spX second point x position
     * @param spY second point y position
     * @return
     */
    public static float getDistanceBetweenTwoPoints(float fpX, float fpY, float spX, float spY) {
        return (float) Math.sqrt((spX - fpX) * (spX - fpX) + (spY - fpY) * (spY - fpY));
    }

    /**
     * get the angle which the line intersect x axis
     *
     * @param fpX
     * @param fpY
     * @param spX
     * @param spY
     * @param distance
     * @return degrees
     */
    public static float getAngleLineIntersectX(float fpX, float fpY, float spX, float spY, float distance) {
        return (float) Math.toDegrees(Math.acos((spX - fpX) / distance));
    }

    /**
     * get the angle which the line intersect y axis
     *
     * @param fpX
     * @param fpY
     * @param spX
     * @param spY
     * @param distance
     * @return degrees
     */
    public static float getAngleLineIntersectY(float fpX, float fpY, float spX, float spY, float distance) {
        return (float) Math.toDegrees(Math.acos((spY - fpY) / distance));
    }

    /**
     * Generate an SHA-1 hash for the pattern. Not the most secure, but it is at
     * least a second level of protection. First level is that the file is in a
     * location only readable by the system process.
     *
     * @param pattern
     * @return the hash of the pattern in a byte array.
     */
    public static byte[] patternToHash(List<LockPatternView.Cell> pattern) {
        if (pattern == null) {
            return null;
        } else {
            int size = pattern.size();
            byte[] res = new byte[size];
            for (int i = 0; i < size; i++) {
                LockPatternView.Cell cell = pattern.get(i);
                res[i] = (byte) cell.getIndex();
            }
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-1");
                return md.digest(res);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return res;
            }
        }
    }

    /**
     * Check to see if a pattern matches the saved pattern. If no pattern
     * exists, always returns true.
     *
     * @param pattern
     * @param bytes
     * @return Whether the pattern matches the stored one.
     */
    public static boolean checkPattern(List<LockPatternView.Cell> pattern, byte[] bytes) {
        if (pattern == null || bytes == null) {
            return false;
        } else {
            byte[] bytes2 = patternToHash(pattern);
            return Arrays.equals(bytes, bytes2);
        }
    }
}
