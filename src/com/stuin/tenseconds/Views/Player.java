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

	void clear() {
		getChildAt(0).setVisibility(GONE);
		getChildAt(2).setVisibility(GONE);
	}

	void win() {
		clear();
		scoreboard.win(((Timer) getChildAt(1)).end());
	}

	void lose() {
		((Grid) getChildAt(0)).marked.display();
		((Grid) getChildAt(2)).marked.display();

		

		scoreboard.done(false);
		clear();
	}
}
