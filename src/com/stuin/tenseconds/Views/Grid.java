package com.stuin.tenseconds.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;
import com.stuin.tenseconds.Round;
import android.transition.*;
import android.widget.*;
import com.stuin.tenseconds.R;

/**
 * Created by Stuart on 3/12/2017.
 */
public class Grid extends GridLayout {
	public boolean top;
	Cell marked;
	Grid grid = this;
	
    public Grid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
		top = attributeSet.getAttributeBooleanValue("stuin","top",false);
		
		//post(slideSet);
    }
	
	Runnable slideSet = new Runnable() {
		public void run() {
			Slide slide = new Slide();
			slide.onAppear((RelativeLayout) grid.getParent().getParent(), grid, new TransitionValues(), new TransitionValues());
			slide.onDisappear((RelativeLayout) grid.getParent().getParent(), grid, new TransitionValues(), new TransitionValues()); 
		}
	};

    void reset() {
		removeAllViewsInLayout();
        setColumnCount(Round.size);
        
		if(top) {
			for(Cell c : Round.cells) addView(c);
			marked = (Cell) getChildAt(Round.pos);
			marked.color = marked.mark;
			marked.setColor();
			marked.display();
		} else {
			for(Cell c : Round.cells) addView(c.copy());
			marked = (Cell) getChildAt(Round.pos);
			marked.display();
		}
		
		setVisibility(VISIBLE);
    }
}
