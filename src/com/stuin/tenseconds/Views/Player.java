package com.stuin.tenseconds.Views;

import android.widget.*;
import android.content.*;
import android.util.*;
import com.stuin.cleanvisuals.Settings;
import com.stuin.tenseconds.*;

public class Player extends LinearLayout {
	public Scoreboard scoreboard;
	public SharedPreferences sharedPreferences;

	private boolean menu = true;
	private int tutorial = 0;
	private String[] tutorialText;

	public Player(Context context, AttributeSet attr) {
		super(context, attr);

		//Get save data
		String[] KEYS = {
				"Expanded", "Tutorial", "Rated", "RateDialog", "Versus", "Background"};
		sharedPreferences = context.getSharedPreferences("TenSeconds", Context.MODE_PRIVATE);
		Settings.load(sharedPreferences, KEYS);
		Settings.set("Versus", false);
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
			if(tutorial < tutorialText.length) {
				timer.write(tutorialText[tutorial]);
				tutorial++;
			} else {
				//end tutorial
				Settings.set("Tutorial", false);
				tutorial = 0;
				timer.show();
			}
		} else {
			timer.show();
		}

		//Run text animation
		postDelayed(title, 100);
	}
	
	//Animation to fit text behind grid
	private Runnable title = new Runnable() {
		public void run() {
			TextView textView = (TextView) ((RelativeLayout) getParent()).getChildAt(2);
			if(textView.getText().length() > 15) {
				//Shrink text by one letter
				String text = textView.getText().toString();
				text = text.substring(0, text.length() - 1);
				textView.setText(text);

				postDelayed(title, 75);
			}
		}
	};

	public void clear() {
		Round.moving = true;

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
		((TextView) ((RelativeLayout) getParent()).findViewById(R.id.Top_Text))
			.setText(getResources().getText(R.string.app_name));
		((TextView) ((RelativeLayout) getParent()).findViewById(R.id.Bot_Button))
			.setText(getResources().getText(R.string.app_start));

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
		if(tutorial > 0) tutorial = 0;

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
