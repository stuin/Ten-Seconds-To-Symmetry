package com.stuin.tenseconds.Game;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

public class ColorReferences {
    static private int[] base = {
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.WHITE,
        Color.BLACK,
        Color.YELLOW,
        Color.MAGENTA,
        Color.CYAN,
    };

    static public boolean hex = false;

    static Drawable getColor(int i) {
        if(hex)
            return new HexagonDrawable(base[i]);
        return new ColorDrawable(base[i]);
    }

    static Drawable getShadedColor(int i) {
        int r = Color.red(base[i]) / 2;
        int g = Color.green(base[i]) / 2;
        int b = Color.blue(base[i]) / 2;
        int c = Color.rgb(r, g, b);
        if(hex)
            return new HexagonDrawable(c);
        return new ColorDrawable(c);
    }
}
