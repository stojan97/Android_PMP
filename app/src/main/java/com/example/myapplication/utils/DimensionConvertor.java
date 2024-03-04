package com.example.myapplication.utils;

import android.content.Context;

public final class DimensionConvertor {
    public static int dpToPixel(float density, int dp) {
        return Math.round((float) dp * density);
    }

}
