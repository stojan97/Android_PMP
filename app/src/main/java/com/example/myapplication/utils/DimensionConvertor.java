package com.example.myapplication.utils;

import android.content.Context;

/**
 * Dimension convertor for converting from other dimensions to pixels.
 */
public final class DimensionConvertor {
    /**
     * DP to pixel convertor.
     *
     * @param density the density
     * @param dp      the dp value
     * @return the converted pixel value
     */
    public static int dpToPixel(float density, int dp) {
        return Math.round((float) dp * density);
    }

}
