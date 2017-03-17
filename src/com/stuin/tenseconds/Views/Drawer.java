package com.stuin.tenseconds.Views;
import android.widget.*;
import android.util.*;
import android.content.Context;
import com.stuin.tenseconds.Round;
import com.stuin.tenseconds.Slider;

public class Drawer extends LinearLayout {
	public Slider drawerSlide = new Slider(this);

	public Drawer(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	public void open() {
		if(drawerSlide.unSet) {
			drawerSlide.setup(true, Round.length / 2, 200);

			for(int i = 0; i < getChildCount(); i++) {
				if(getChildAt(i) instanceof TextView) {
					TextView textView = (TextView) getChildAt(i);
					textView.setTextSize(Round.text);
				} else if(getChildAt(i) instanceof Switch) {
					Switch view = (Switch) getChildAt(i);
					view.setChecked(Round.colorblind);
				}
			}
		}

		drawerSlide.enter();
	}

}
