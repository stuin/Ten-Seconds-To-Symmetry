package com.stuin.tenseconds;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stuin.cleanvisuals.Settings;
import com.stuin.tenseconds.Views.Player;
import com.stuin.tenseconds.Views.RateDialog;
import com.stuin.tenseconds.Views.Timer;

public class Single implements Scoreboard {
	private Player player;
	private SharedPreferences sharedPreferences;
	private String[] labels;
	private int highScore;
	private int score;

	public Single(Player player) {
		//Set variables
	    this.player = player;
	    sharedPreferences = player.sharedPreferences;

		//Read data
	    highScore = sharedPreferences.getInt("HighScore", -1);
	    labels = player.getResources().getStringArray(R.array.app_labels);
		
		//Check tutorial start
		Settings.Set("Tutorial", highScore == -1);
    }

    public void Win(int time, boolean top) {
		//Calculate score
	    score += time * (Round.size / 2) * Round.colors;

		//Show win screen
		RelativeLayout relativeLayout = (RelativeLayout) player.getParent();
		String text = '+' + Round.Separate(score) + '+';
		((TextView) relativeLayout.getChildAt(0)).setText(text);

		((TextView) relativeLayout.getChildAt(1)).setText(labels[4]);

		//Prepare next round
		if(Round.size == 9 && Round.next && 
			((Round.colors == 5 && !Settings.Get("Expanded")) || Round.colors == 8)) Done(true);
		else Round.Next();
    }

    public void Done(boolean win) {
		//Write score at top
		String text = labels[0] + Round.Separate(score);
		if(win) text = labels[1] + Round.Separate(score);
		
		RelativeLayout relativeLayout = (RelativeLayout) player.getParent();
		((TextView) relativeLayout.getChildAt(0)).setText(text);
		((TextView) relativeLayout.getChildAt(1)).setText(labels[5]);

		//show high score data
		Timer timer = (Timer) player.getChildAt(1);
		if(score > highScore) {
			timer.Write(labels[2]);

			sharedPreferences.edit().putInt("HighScore", score).apply();
			highScore = score;
		} else timer.Write(labels[3] + Round.Separate(highScore));
		
		//Prepare for restart
		Round.Reset();
		Round.loss = true;
		score = 0;

		//Show rating menu dialog
		if(!Settings.Get("Rated") && !Settings.Get("RateDialog")) {
			RateDialog rateDialog = new RateDialog();
			rateDialog.show(((Activity) player.getContext()).getFragmentManager(), "RateDialog");
		}
    }

    public void Save() {
		//Create save data
		if(!Round.loss && score > 0) {
			String file = Round.count + ":" + score;

			sharedPreferences.edit().putString("Save", file).apply();
		}
	}

	public void Load() {
		//Read save data
		String file = sharedPreferences.getString("Save", " ");

		//Set match variables
		Round.Reset();
		if(!file.equals(" ")) {
			Round.count = Integer.valueOf(file.split(":")[0]);
			score = Integer.valueOf(file.split(":")[1]);

			//Set correct round
			for(int i = 0; i < Round.count; i++) Round.Next();

			sharedPreferences.edit().putString("Save", " ").apply();
			Win(0, false);
		}
	}
}
