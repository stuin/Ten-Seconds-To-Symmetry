package com.stuin.tenseconds.Scoring;

import android.content.SharedPreferences;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stuin.cleanvisuals.Settings;
import com.stuin.tenseconds.Game.Player;
import com.stuin.tenseconds.Game.Timer;
import com.stuin.tenseconds.Menu.Drawer;
import com.stuin.tenseconds.R;
import com.stuin.tenseconds.Round;

public class Single implements Scoreboard {
	//Technical values
	private Player player;
	private SharedPreferences sharedPreferences;
	private String[] labels;

	//Game scoring
	private int highScore;
	private int score;
	private int totalTime;

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
	    score += time * (Round.size * .2) * (Round.colors - 2) / 100;
		totalTime += time;

		//Show win screen
		RelativeLayout relativeLayout = (RelativeLayout) player.getParent();
		String text = '+' + Round.separate(score) + '+';
		((TextView) relativeLayout.getChildAt(2)).setText(text);
		((TextView) relativeLayout.getChildAt(3)).setText(labels[4]);

		//Update current stats
		text = player.getResources().getString(R.string.drawer_stats);
		text = String.format(text, Round.count, totalTime / 100, highScore);
		((TextView) ((RelativeLayout) player.getParent()).findViewById(R.id.Drawer_Game_Stats)).setText(text);

		//Set next round
		Round.next();
		Round.generate();
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

			//Set high score
			sharedPreferences.edit().putInt("HighScore", score).apply();
			highScore = score;
		} else timer.write(labels[3] + Round.separate(highScore));
		
		//Prepare for restart
		Settings.set("Hexmode", false);
		Round.reset();
		Round.generate();
		Round.loss = true;
		score = 0;

		//Show rating menu dialog
		if(!Settings.get("Rated") && Settings.get("RateDialog") && Round.games >= 4) {
			((Drawer)((RelativeLayout) player.getParent()).findViewById(R.id.Drawer_Layout)).showPage(2);
			Settings.set("RateDialog", false);
		}
    }

    public void save() {
		//Create save data
		if(!Round.loss && score > 0) {
			String file = Round.count + ":" + score + ":" + totalTime;
			sharedPreferences.edit().putString("save", file).apply();
		}

		sharedPreferences.edit().putInt("Games", Round.games).apply();
	}

	public boolean load() {
		//Check for save
		String file = sharedPreferences.getString("save", " ");
		Round.games = sharedPreferences.getInt("Games", 0);

		//Process save data
		Round.reset();
		if(!file.equals(" ")) {
			//Get values
			Round.count = Byte.valueOf(file.split(":")[0]);
			score = Integer.valueOf(file.split(":")[1]);
			totalTime = Integer.valueOf(file.split(":")[2]);

			//Set correct round
			for(int i = 0; i < Round.count; i++) Round.next();

			//Clear save file
			sharedPreferences.edit().putString("save", " ").apply();
			win(0, false);
			return true;
		}
		return false;
	}
}
