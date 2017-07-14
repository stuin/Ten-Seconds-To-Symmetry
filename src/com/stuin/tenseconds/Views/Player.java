package com.stuin.tenseconds.Views;

import android.widget.*;
import android.content.*;
import android.util.*;
import com.stuin.cleanvisuals.SliderSync;
import com.stuin.tenseconds.R;
import com.stuin.tenseconds.Round;
import com.stuin.tenseconds.Scoreboard;
import com.stuin.tenseconds.*;

public class Player extends LinearLayout {
	public Scoreboard scoreboard;
	public SliderSync slideDrawer;
	public SharedPreferences sharedPreferences;

	private Tutorial tutorial = null;
	private boolean menu = true;

	public Player(Context context, AttributeSet attr) {
		super(context, attr);

		//Get save data
		String[] KEYS = {
				"Expanded", "Tutorial", "Rated", "Versus"};
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
		
		Timer timer = (Timer) getChildAt(1);
		timer.Start();
		
		if(Settings.Get("Tutorial")) {
			if(tutorial == null) tutorial = new Tutorial(timer);
			tutorial.run = true;
			tutorial.Next();
		} else {
			if(tutorial != null) tutorial = null;
			timer.Show();
		}
		
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

		slideDrawer.showSecondary();

		//Set next round
		if(Round.size == 5 && Round.colors == 3 && !Round.next && menu) Menu();
		menu = true;
	}

	public void Menu() {
		//Set text to main menu
		((TextView) ((RelativeLayout) getParent()).findViewById(R.id.Top_Text))
			.setText(getResources().getText(R.string.app_name));
		((TextView) ((RelativeLayout) getParent()).findViewById(R.id.Bot_Text))
			.setText(getResources().getText(R.string.app_start));
		
		Timer timer = (Timer) findViewById(R.id.Bar_Layout);
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
