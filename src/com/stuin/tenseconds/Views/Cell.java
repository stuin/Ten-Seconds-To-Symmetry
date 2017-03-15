package com.stuin.tenseconds.Views;

import android.content.Context;
import android.widget.TextView;
import android.graphics.*;
import com.stuin.tenseconds.*;

/**
 * Created by Stuart on 3/14/2017.
 */
public class Cell extends TextView {
    int x;
    int y;

    public int color;
    public boolean mark;

    public Cell(Context context, int color, int x, int y) {
        super(context);
        this.color = color;
        this.x = x;
        this.y = y;
		
		setWidth(Round.scale);
		setHeight(Round.scale);
    }
	
	Color color() {
		switch(color) {
			
		}
		return null;
	}


}
