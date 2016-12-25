package com.stuin.tensecondstosymmetry;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

class Cell extends TextView {

    boolean change = false;

    Cell(Context context, int size) {
        super(context);
        setWidth(size);
        setHeight(size);
    }

    void color(int i) {
        //Set color
        switch(i) {
            case 0:
                setBackgroundColor(Color.RED);
                break;
            case 1:
                setBackgroundColor(Color.BLUE);
                break;
            case 2:
                setBackgroundColor(Color.GREEN);
                break;
            case 3:
                setBackgroundColor(Color.WHITE);
                break;
            case 4:
                setBackgroundColor(Color.BLACK);
                break;
            case 5:
                setBackgroundColor(Color.YELLOW);
                break;
            case 6:
                setBackgroundColor(Color.MAGENTA);
                break;
            case 7:
                setBackgroundColor(Color.CYAN);
                break;
        }
    }
}
