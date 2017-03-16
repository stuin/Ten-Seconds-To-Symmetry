package com.stuin.tenseconds.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import com.stuin.tenseconds.Round;
import android.transition.*;
import android.widget.*;
import com.stuin.tenseconds.R;

/**
 * Created by Stuart on 3/12/2017.
 */
public class Grid extends GridLayout {
	private boolean top;
	private TranslateAnimation enterAnimation;
	private TranslateAnimation exitAnimation;
	Cell marked;
	
    public Grid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
		top = attributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res/com.stuin.tenseconds","top",false);
		
		post(new Runnable() {
			public void run() {
				int s = Round.length;
				if(top) s = -s;

				enterAnimation = new TranslateAnimation(0, 0, s, 0);
				enterAnimation.setDuration(500);

				exitAnimation = new TranslateAnimation(0, 0, 0, s);
				exitAnimation.setDuration(500);
			}
		});
    }

    void enter() {
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
		
		startAnimation(enterAnimation);
		Round.moving = true;
		postDelayed(new Runnable() {
			@Override
			public void run() {
				Round.moving = false;
			}
		}, enterAnimation.getDuration());
    }

    void exit() {
    	startAnimation(exitAnimation);
    	Round.moving = true;
    	postDelayed(new Runnable() {
			@Override
			public void run() {
				removeAllViewsInLayout();
				Round.moving = false;
			}
		}, exitAnimation.getDuration());
	}
}
