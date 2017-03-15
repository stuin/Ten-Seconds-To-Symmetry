package com.stuin.tenseconds.Views;

import android.content.Context;
import android.widget.GridLayout;
import com.stuin.tenseconds.Round;
import java.util.*;

/**
 * Created by Stuart on 3/12/2017.
 */
public class Grid extends GridLayout {
	boolean top;
	
    public Grid(Context context) {
        super(context);
    }

    public void reset() {
        removeAllViewsInLayout();
        setColumnCount(Round.size);

        for(Cell c : Round.cells) addView(c);
		if(top) {
			Cell c = (Cell) getChildAt(Round.pos);
			int next = new Random().nextInt(Round.colors - 1);
			if(next == c.color) next = Round.colors - 1;
			c.color = next;
		}
    }
}
