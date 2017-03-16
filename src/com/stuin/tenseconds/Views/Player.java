package com.stuin.tenseconds.Views;
import android.widget.*;
import android.content.*;
import android.util.*;
import com.stuin.tenseconds.Scoreboard;

public class Player extends LinearLayout {
	public Scoreboard scoreboard;

	public Player(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public void start() {
		((Grid) getChildAt(0)).reset();
		((Grid) getChildAt(2)).reset();
		((Timer) getChildAt(1)).clear();
	}

	public void clear() {
		((Grid) getChildAt(0)).removeAllViewsInLayout();
		((Grid) getChildAt(2)).removeAllViewsInLayout();
	}

	void win() {
		clear();
		scoreboard.win(((Timer) getChildAt(1)).time);
	}

	void lose() {
		((Grid) getChildAt(0)).marked.display();
		((Grid) getChildAt(2)).marked.display();

		postDelayed(new Runnable() {
			@Override
			public void run() {
				scoreboard.done(false);
				clear();
			}
		},0);
	}
}
