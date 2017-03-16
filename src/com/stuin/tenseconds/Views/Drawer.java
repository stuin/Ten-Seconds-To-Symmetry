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
			drawerSlide.setup(true, Round.length / 2);

			((TextView) getChildAt(0)).setTextSize(Round.text);
			((TextView) getChildAt(1)).setTextSize(Round.text);
			((TextView) getChildAt(3)).setTextSize(Round.text);
		}

		drawerSlide.open();
	}

}
