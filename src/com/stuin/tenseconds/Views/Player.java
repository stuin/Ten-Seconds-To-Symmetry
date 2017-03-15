package com.stuin.tenseconds.Views;
import android.widget.*;
import android.content.*;
import android.util.*;
import com.stuin.tenseconds.Round;

public class Player extends LinearLayout {
	public Player(Context context, AttributeSet attr) {
		super(context, attr);
		((Grid) getChildAt(0)).top = true;
	}

	public void start() {
		((Grid) getChildAt(0)).reset();
		((Grid) getChildAt(2)).reset();
	}

	public void clear() {
		getChildAt(0).setVisibility(GONE);
		getChildAt(2).setVisibility(GONE);
	}
}
