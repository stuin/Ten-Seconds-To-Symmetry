package com.stuin.tenseconds.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;
import com.stuin.tenseconds.Round;

/**
 * Created by Stuart on 3/12/2017.
 */
public class Grid extends GridLayout {
	public boolean top = false;
	Cell marked;
	
    public Grid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    void reset() {
        removeAllViewsInLayout();
        setVisibility(VISIBLE);
        setColumnCount(Round.size);

        
		if(top) {
			for(Cell c : Round.cells) {
				addView(c);
				c.display();
				if(c.mark != -1) marked = c;
			}
			//marked = (Cell) getChildAt(Round.pos);
			marked.color = marked.mark;
			marked.setColor();
			marked.display();
		} else {
			for(Cell c : Round.cells) {
				addView(c.clone());
			}
			marked = (Cell) getChildAt(Round.pos);
			marked.display();
		}
    }
}
