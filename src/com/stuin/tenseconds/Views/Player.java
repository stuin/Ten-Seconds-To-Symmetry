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
				"Expanded", "Tutorial", "Rated", "RateDialog", "Versus"};
		sharedPreferences = context.getSharedPreferences("TenSeconds", Context.MODE_PRIVATE);
		Settings.Load(sharedPreferences, KEYS);
	}

	public void Start() {
		//Start the round
		Round.moving = true;
		Round.loss = false;
		
		//Play animations
		((Grid) getChildAt(0)).Enter();
		((Grid) getChildAt(2)).Enter();

		//Start timer
		Timer timer = (Timer) getChildAt(1);
		timer.Start();

		//Check for versus mode
		if(Settings.Get("Versus")) {
			if(scoreboard.getClass() != Versus.class) scoreboard = new Versus(this);
		} else if(scoreboard.getClass() == Versus.class) scoreboard = new Single(this);


		//Run tutorial
		if(Settings.Get("Tutorial")) {
			//Load text
			if(tutorialText == null) tutorialText = getResources().getStringArray(R.array.app_tutor);
			if(tutorial < tutorialText.length) {
				timer.Write(tutorialText[tutorial]);
				tutorial++;
			} else {
				//End tutorial
				Settings.Set("Tutorial", false);
				tutorial = 0;
				timer.Show();
			}
		} else {
			timer.Show();
		}

		//Run text animation
		postDelayed(title, 100);
	}
	
	//Animation to fit text behind grid
	private Runnable title = new Runnable() {
		public void run() {
			TextView textView = (TextView) ((RelativeLayout) getParent()).getChildAt(0);
			if(textView.getText().length() > 15) {
				//Shrink text by one letter
				String text = textView.getText().toString();
				text = text.substring(0, text.length() - 1);
				textView.setText(text);

				postDelayed(title, 75);
			}
		}
	};

	public void Clear() {
		Round.moving = true;

		//Play leaving animations
		((Grid) getChildAt(0)).slider.exit();
		((Grid) getChildAt(2)).slider.exit();
		((Timer) getChildAt(1)).End();

		if(!Settings.Get("Versus"))
			((Drawer) ((RelativeLayout) getParent()).findViewById(R.id.Drawer_Layout)).slideDrawer.showSecondary();

		//Set next round
		if(Round.size == 5 && Round.colors == 3 && !Round.next && menu) Menu();
		menu = true;
	}

	public void Menu() {
		//Set text to main menu
		((TextView) ((RelativeLayout) getParent()).findViewById(R.id.Top_Text))
			.setText(getResources().getText(R.string.app_name));
		((TextView) ((RelativeLayout) getParent()).findViewById(R.id.Bot_Button))
			.setText(getResources().getText(R.string.app_start));

		//End timer
		Timer timer = ((Timer) getChildAt(1));
		timer.Clear();
		timer.Show();
	}

	void Win(boolean top) {
		//Set end variables
		Round.moving = true;
		Round.count++;
		menu = false;
		
		//Clear and set score
		Clear();
		scoreboard.Win(((Timer) getChildAt(1)).End() / 10, top);
	}

	void Lose() {
		Round.moving = true;
		if(tutorial > 0) tutorial = 0;

		//Show correct answer
		((Timer) getChildAt(1)).End();
		((Grid) getChildAt(0)).Show();
		((Grid) getChildAt(2)).Show();
		
		//Wait and clear
		postDelayed(new Runnable() {
			@Override
			public void run() {
				menu = false;
				Clear();
				scoreboard.Done(false);
			}
		},1000);
	}

	public boolean Playing() {
		//Return if in game
		return ((Grid) getChildAt(0)).slider.shown();
	}
}
