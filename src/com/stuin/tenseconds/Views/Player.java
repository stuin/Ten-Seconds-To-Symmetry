package com.stuin.tenseconds.Views;
import android.widget.*;
import android.content.*;
import android.util.*;
import com.stuin.tenseconds.Scoreboard;
import com.stuin.tenseconds.R;

public class Player extends LinearLayout {
	public Scoreboard scoreboard;

	public Player(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public void start() {
		((Grid) getChildAt(0)).reset();
		((Grid) getChildAt(2)).reset();
		((Timer) getChildAt(1)).clear();
		
		postDelayed(title, 100);
	}
	
	private Runnable title = new Runnable() {
		public void run() {
			TextView textView = (TextView) ((RelativeLayout) getParent()).getChildAt(0);
			if(textView.getText().length() > 10) {
				String text = textView.getText().toString();
				text = text.substring(1, text.length() - 1);
				textView.setText(text);

				postDelayed(title, 200);
			}
		}
	};

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
