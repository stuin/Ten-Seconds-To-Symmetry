package com.stuin.tenseconds.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import com.stuin.tenseconds.Round;
import com.stuin.tenseconds.Slider;

/**
 * Created by Stuart on 3/12/2017.
 */
public class Grid extends GridLayout {
	private boolean top;
	Slider slider = new Slider(this);
	Cell marked;
	
    public Grid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
		top = attributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res/com.stuin.tenseconds","top",false);
		
		post(new Runnable() {
			public void run() {
				int s = Round.length;
				if(top) s = -s;

				slider.setup(false, s);
			}
		});
    }

    void enter() {
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
		
		slider.open();
    }

    void exit() {
    	slider.close();
	}
}
