package com.stuin.tenseconds.Game;

import android.widget.*;
import android.content.*;
import android.util.*;
import com.stuin.cleanvisuals.Settings;
import com.stuin.cleanvisuals.TextAnimation;
import com.stuin.tenseconds.*;
import com.stuin.tenseconds.Menu.Drawer;
import com.stuin.tenseconds.Round;
import com.stuin.tenseconds.Scoring.Scoreboard;
import com.stuin.tenseconds.Scoring.Single;
import com.stuin.tenseconds.Scoring.Versus;

public class Player extends LinearLayout {
	public Scoreboard scoreboard;
	public SharedPreferences sharedPreferences;
	public TextAnimation titleAnimation;
	public TextAnimation buttonAnimation;

	private boolean menu = true;
	private String[] tutorialText;

	public Player(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public void start() {
		//start the round
		Round.moving = true;
		Round.loss = false;
		
		//Play animations
		((Grid) getChildAt(0)).enter();
		((Grid) getChildAt(2)).enter();

		//start timer
		Timer timer = (Timer) getChildAt(1);
		timer.start();

		//Check for versus mode
		if(Settings.get("Versus")) {
			if(scoreboard.getClass() != Versus.class) scoreboard = new Versus(this);
		} else if(scoreboard.getClass() == Versus.class) scoreboard = new Single(this);


		//Run tutorial
		if(Settings.get("Tutorial")) {
			//load text
			if(tutorialText == null) tutorialText = getResources().getStringArray(R.array.app_tutor);
			if(Round.tutorial < tutorialText.length) {
				timer.write(tutorialText[Round.tutorial]);
				Round.tutorial++;
			}
			if(Round.tutorial == tutorialText.length) {
				//end tutorial
				Settings.set("Tutorial", false);
				Round.tutorial = 0;
			}
		} else {
			timer.show();
		}

		//Run text animation
		TextView textView = (TextView) ((RelativeLayout) getParent()).getChildAt(2);
		if(textView.length() > 15)
			titleAnimation.shift("", 75);
	}

	public void clear() {
		Round.moving = true;
		titleAnimation.stop();
		buttonAnimation.stop();

		//Play leaving animations
		((Grid) getChildAt(0)).slider.exit();
		((Grid) getChildAt(2)).slider.exit();
		((Timer) getChildAt(1)).end();

		if(!Settings.get("Versus"))
			((Drawer) ((RelativeLayout) getParent()).findViewById(R.id.Drawer_Layout)).slideDrawer.showSecondary();

		//Set next round
		if(Round.size == 5 && Round.colors == 3 && !Round.next && menu) menu();
		menu = true;
	}

	public void menu() {
		//Set text to main menu
		titleAnimation.shift(getResources().getText(R.string.app_name).toString(), 75);
		buttonAnimation.shift(getResources().getText(R.string.app_start).toString(), 75);

		//end timer
		Timer timer = ((Timer) getChildAt(1));
		timer.clear();
		timer.show();
	}

	void win(boolean top) {
		//Set end variables
		Round.moving = true;
		Round.count++;
		menu = false;
		
		//clear and set score
		clear();
		scoreboard.win(((Timer) getChildAt(1)).end() / 10, top);
	}

	void lose() {
		Round.moving = true;

		//show correct answer
		((Timer) getChildAt(1)).end();
		((Grid) getChildAt(0)).show();
		((Grid) getChildAt(2)).show();
		
		//Wait and clear
		postDelayed(new Runnable() {
			@Override
			public void run() {
				menu = false;
				clear();
				scoreboard.done(false);
			}
		},1000);
	}

	public boolean playing() {
		//Return if in game
		return ((Grid) getChildAt(0)).slider.shown();
	}
}
