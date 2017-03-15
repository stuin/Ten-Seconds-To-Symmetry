package com.stuin.tenseconds.Views;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.graphics.*;
import com.stuin.tenseconds.*;

/**
 * Created by Stuart on 3/14/2017.
 */
public class Cell extends FrameLayout {
    int x;
    int y;

    public int color;
    public int mark;

    public Cell(Context context, int color, int x, int y) {
        super(context);
        this.color = color;
        this.x = x;
        this.y = y;
		
		setMinimumWidth(Round.scale);
		setMinimumHeight(Round.scale);
		setBackgroundColor(getColor());
    }
	
	private int getColor() {
		switch(color) {
            case 0:
                return Color.RED;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.WHITE;
            case 4:
                return Color.BLACK;
		}
		return 0;
	}


}
