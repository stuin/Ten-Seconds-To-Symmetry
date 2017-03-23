package com.stuin.tenseconds.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;
import com.stuin.tenseconds.Round;
import com.stuin.cleanvisuals.Slider;

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

		slider.endings = new Slider.Endings() {
			@Override
			public void enter() {
				Round.moving = false;
			}

			@Override
			public void exit() {
				Round.moving = false;
				removeAllViewsInLayout();
			}
		};
    }

    void enter() {
    	if(slider.unSet) {
			int s = Round.length;
			if(top) s = -s;

			slider.setup(false, s, 700);
		}

		removeAllViewsInLayout();
        setColumnCount(Round.size);
        
		if(top) {
			for(Cell c : Round.cells) {
				addView(c);
				if(c.mark > -1) {
					marked = c;
					marked.setColor(marked.mark);
				}
				else c.setColor(c.color);
			}
		} else {
			for(Cell c : Round.cells) {
				c = c.copy();
				addView(c);
				if(c.mark > -1) {
					marked = c;
					marked.setColor(marked.color);
				} else c.setColor(c.color);
			}
		}

		slider.enter();
    }

    void exit() {
    	slider.exit();
	}

	void show() {
		for(int i = 0; i < getChildCount(); i++) {
			Cell c = (Cell) getChildAt(i);
			if(c.mark == -1) c.display();
		}
	}
}
