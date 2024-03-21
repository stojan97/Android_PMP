package com.example.myapplication.utils;

import android.content.Context;

import com.example.myapplication.DictionaryApplication;

/**
 * Dimension convertor for converting from other dimensions to pixels.
 */
public final class DimensionConvertor {

    /**
     * DP to pixel convertor.
     *
     * @param dp the dp value
     * @return the converted pixel value
     */
    public static int dpToPixel(int dp) {
        float density = DictionaryApplication.getApplicationDensity();
        return Math.round((float) dp * density);
    }

}
