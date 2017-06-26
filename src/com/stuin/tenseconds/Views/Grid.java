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

	//Parts of grid
	Slider slider = new Slider(this);
	Cell marked;
	
    public Grid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
		//Get custom value
		top = attributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res/com.stuin.tenseconds","top",false);

		//Add ending to slide
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

    void Enter() {
		//Make sure animation set up
    	if(slider.unSet) {
			int s = Round.length;
			if(top) s = -s;

			slider.setup(false, s, 700);
		}

		//Clear old grid
		removeAllViewsInLayout();
        setColumnCount(Round.size);
        
		//Place new grid
		for(Cell c : Round.cells) {
			if(top) c = c.Copy();
			addView(c);
			if(c.mark > -1 && top) {
				marked = c;
				marked.setColor(marked.mark);
			}
			else c.setColor(c.color);
		}
		
		//Start animation
		slider.enter();
    }

    void Exit() {
		//Start leaving animation
    	slider.exit();
	}

	void Show() {
		//Shade all but awnser
		for(int i = 0; i < getChildCount(); i++) {
			Cell c = (Cell) getChildAt(i);
			if(c.mark == -1) c.display();
		}
	}
}
