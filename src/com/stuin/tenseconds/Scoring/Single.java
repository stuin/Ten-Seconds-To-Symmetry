package com.stuin.tenseconds.Scoring;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stuin.cleanvisuals.Settings;
import com.stuin.tenseconds.Game.Player;
import com.stuin.tenseconds.Menu.RateDialog;
import com.stuin.tenseconds.Game.Timer;
import com.stuin.tenseconds.R;
import com.stuin.tenseconds.Round;

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
    }

    public void win(int time, boolean top) {
		//Calculate score
	    score += time * (Round.size / 2) * Round.colors;

		//Show win screen
		RelativeLayout relativeLayout = (RelativeLayout) player.getParent();
		String text = '+' + Round.separate(score) + '+';
		((TextView) relativeLayout.getChildAt(2)).setText(text);

		((TextView) relativeLayout.getChildAt(3)).setText(labels[4]);

		//Prepare next round
		if(Round.size == 9 && Round.next && 
			((Round.colors == 5 && !Settings.get("Expanded")) || Round.colors == 8)) done(true);
		else Round.next();
    }

    public void done(boolean win) {
		//write score at vertical
		String text = labels[0] + Round.separate(score);
		if(win) text = labels[1] + Round.separate(score);
		
		RelativeLayout relativeLayout = (RelativeLayout) player.getParent();
		((TextView) relativeLayout.getChildAt(2)).setText(text);
		((TextView) relativeLayout.getChildAt(3)).setText(labels[5]);

		//show high score data
		Timer timer = (Timer) player.getChildAt(1);
		if(score > highScore) {
			timer.write(labels[2]);

			sharedPreferences.edit().putInt("HighScore", score).apply();
			highScore = score;
		} else timer.write(labels[3] + Round.separate(highScore));
		
		//Prepare for restart
		Round.reset();
		Round.loss = true;
		score = 0;

		//Show rating menu dialog
		if(!Settings.get("Rated") && Settings.get("RateDialog") && Round.games >= 3) {
			RateDialog rateDialog = new RateDialog();
			rateDialog.show(((Activity) player.getContext()).getFragmentManager(), "RateDialog");
		}
    }

    public void save() {
		//Create save data
		if(!Round.loss && score > 0) {
			String file = Round.count + ":" + score;

			sharedPreferences.edit().putString("save", file).apply();
		}
	}

	public void load() {
		//Read save data
		String file = sharedPreferences.getString("save", " ");

		//Set match variables
		Round.reset();
		if(!file.equals(" ")) {
			Round.count = Byte.valueOf(file.split(":")[0]);
			score = Integer.valueOf(file.split(":")[1]);

			//Set correct round
			for(int i = 0; i < Round.count; i++) Round.next();

			sharedPreferences.edit().putString("save", " ").apply();
			win(0, false);
		}
	}
}
