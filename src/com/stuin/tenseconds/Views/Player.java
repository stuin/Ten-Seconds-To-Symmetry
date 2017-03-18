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
	public boolean loss = false;

	private boolean menu = true;

	public Player(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public void start() {
		Round.playing = true;
		loss = false;

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
		((Timer) getChildAt(1)).end();

		slideDrawer.showSecondary();

		if(Round.size == 5 && Round.colors == 3 && !Round.next && menu) menu();
		menu = true;
	}

	public void menu() {
		((TextView) ((RelativeLayout) getParent()).findViewById(R.id.TopText)).setText(getResources().getText(R.string.app_name));
		((TextView) ((RelativeLayout) getParent()).findViewById(R.id.BotText)).setText(getResources().getText(R.string.app_start));
		((Timer) findViewById(R.id.TimerLayout)).write("");
	}

	void win() {
		Round.moving = true;
		menu = false;
		clear();
		scoreboard.win(((Timer) getChildAt(1)).end() / 10);
	}

	void lose() {
		Round.moving = true;
		loss = true;

		((Timer) getChildAt(1)).end();
		((Grid) getChildAt(0)).marked.display();
		((Grid) getChildAt(2)).marked.display();

		postDelayed(new Runnable() {
			@Override
			public void run() {
				menu = false;
				clear();
				scoreboard.done(false);
			}
		},1000);
	}
}
