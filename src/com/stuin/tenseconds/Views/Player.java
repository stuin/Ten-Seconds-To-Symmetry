package com.stuin.tenseconds.Views;
import android.widget.*;
import android.content.*;
import android.util.*;
import com.stuin.tenseconds.Animations.SliderSync;
import com.stuin.tenseconds.R;
import com.stuin.tenseconds.Round;
import com.stuin.tenseconds.Scoreboard;

public class Player extends LinearLayout {
	public Scoreboard scoreboard;
	public SliderSync slideDrawer;

	public Player(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public void start() {
		Round.playing = true;

		((Grid) getChildAt(0)).enter();
		((Grid) getChildAt(2)).enter();
		((Timer) getChildAt(1)).start();
		
		postDelayed(title, 100);
	}
	
	private Runnable title = new Runnable() {
		public void run() {
			TextView textView = (TextView) ((RelativeLayout) getParent()).getChildAt(0);
			if(textView.getText().length() > 15) {
				String text = textView.getText().toString();
				text = text.substring(1, text.length());
				textView.setText(text);

				postDelayed(title, 75);
			}
		}
	};

	public void clear() {
		Round.playing = false;

		((Grid) getChildAt(0)).exit();
		((Grid) getChildAt(2)).exit();
		slideDrawer.showSecondary();
	}

	void win() {
		Round.moving = true;
		clear();
		scoreboard.win(((Timer) getChildAt(1)).end() / 10);
	}

	void lose() {
		Round.moving = true;
		((Timer) getChildAt(1)).end();
		((Grid) getChildAt(0)).marked.display();
		((Grid) getChildAt(2)).marked.display();

		postDelayed(new Runnable() {
			@Override
			public void run() {
				scoreboard.done(false);
				clear();
			}
		},1000);
	}
}
