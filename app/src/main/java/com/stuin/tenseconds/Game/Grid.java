package com.stuin.tenseconds.Game;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;
import com.stuin.cleanvisuals.Slide.Endings;
import com.stuin.tenseconds.Round;
import com.stuin.cleanvisuals.Slide.Slider;

/**
 * Created by Stuart on 3/12/2017.
 */
public class Grid extends GridLayout {
	private boolean top;

	//Parts of grid
	Slider slider = new Slider(this);
	
    public Grid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
		//Get custom value
		top = attributeSet.getAttributeBooleanValue(
				"http://schemas.android.com/apk/res-auto","top",false);

		//add ending to slide
		slider.endings = new Endings() {
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
		//Make sure animation set up
    	if(slider.unSet) {
			int s = Round.length;
			if(top) s = -s;

			slider.setup(false, s, 700);
		}

		//clear old grid
		removeAllViewsInLayout();
        setColumnCount(Round.size);
        
		//Place new grid
		for(Cell c : Round.cells) {
			//Add cell
			if(top) c = c.copy();
			addView(c);

			//Set color
			if(c.mark > -1 && top) c.setColor(c.mark);
			else c.setColor(c.color);
		}
		
		//start animation
		slider.enter();
    }

	void show() {
		//Shade all but answer
		for(byte i = 0; i < getChildCount(); i++) {
			Cell c = (Cell) getChildAt(i);
			if(c.mark == -1) c.display();
		}
	}
}
