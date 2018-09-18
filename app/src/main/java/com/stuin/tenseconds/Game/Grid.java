package com.stuin.tenseconds.Game;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import com.stuin.cleanvisuals.Settings;
import com.stuin.cleanvisuals.Slide.Endings;
import com.stuin.tenseconds.Game.Layout.*;
import com.stuin.tenseconds.Round;
import com.stuin.cleanvisuals.Slide.Slider;

/**
 * Created by Stuart on 3/12/2017.
 */
public class Grid extends RecyclerView {
	private boolean top;
	private GridLayoutManager gridManager;
	private HexLayoutManager hexManager;
	private GridAdaptor gridAdaptor;
	private boolean hex = false;

	//Parts of grid
	Slider slider = new Slider(this);
	
    public Grid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
		//Get custom value
		top = attributeSet.getAttributeBooleanValue(
				"http://schemas.android.com/apk/res-auto","top",false);

		//Prepare layout managers
		hexManager = new HexLayoutManager();
		gridManager = new GridLayoutManager();
		setLayoutManager(gridManager);

		//Set grid adaptor
		gridAdaptor = new GridAdaptor(top);
		setAdapter(gridAdaptor);

		//add ending to slide
		slider.endings = new Endings() {
			@Override
			public void enter() {
				Round.moving = false;
			}

			@Override
			public void exit() {
                gridAdaptor.notifyDataSetChanged();
				Round.moving = false;
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

		//Set new grid
		confirmManager();
        gridAdaptor.notifyDataSetChanged();
		
		//start animation
		slider.enter();
    }

    private void confirmManager() {
		//Check hex mode and layout manager
		if(Settings.get("Hexmode") != hex) {
			ColorReferences.hex = hex = !hex;
			if(hex)
				setLayoutManager(hexManager);
			else
				setLayoutManager(gridManager);
		}
	}
}
