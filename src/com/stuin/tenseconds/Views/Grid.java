package com.stuin.tenseconds.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;
import com.stuin.tenseconds.Round;

/**
 * Created by Stuart on 3/12/2017.
 */
public class Grid extends GridLayout {
	boolean top = false;
	Cell marked;
	
    public Grid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    void reset() {
        removeAllViewsInLayout();
        setVisibility(VISIBLE);
        setColumnCount(Round.size);

        for(Cell c : Round.cells) addView(c);
        marked = (Cell) getChildAt(Round.pos);
		if(top) marked.color = marked.mark;
    }
}
