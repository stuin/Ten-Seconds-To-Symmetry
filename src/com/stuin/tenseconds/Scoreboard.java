package com.stuin.tenseconds;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.stuin.tenseconds.Views.Player;
import com.stuin.tenseconds.Views.Timer;

public class Scoreboard {
	private Player player;
	private SharedPreferences sharedPreferences;
	private String[] labels;
	private int highScore;
	private int score;
	private boolean expanded;


	Scoreboard(Player player) {
		//Get save data
	    this.player = player;
	    sharedPreferences = player.getContext().getSharedPreferences("TenSeconds", Context.MODE_PRIVATE);

		//Read data
	    highScore = sharedPreferences.getInt("HighScore", 0);
	    expanded = sharedPreferences.getBoolean("Expanded", false);
	    Round.colorblind = sharedPreferences.getBoolean("Colorblind", false);

	    labels = player.getResources().getStringArray(R.array.app_labels);
    }

    public void Win(int time) {
		//Calculate score
	    score += time * (Round.size / 2) * Round.colors;

		//Show win screen
		RelativeLayout relativeLayout = (RelativeLayout) player.getParent();
		String text = '+' + Round.Separate(score) + '+';
		((TextView) relativeLayout.getChildAt(0)).setText(text);

		((TextView) relativeLayout.getChildAt(1)).setText(labels[4]);

		//Prepare next round
		if(Round.size == 9 && Round.next && ((Round.colors == 5 && !Round.expanded) || Round.colors == 8)) Done(true);
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
    }

    void Save() {
		//Create save data
		if(!Round.loss && score > 0) {
			String file = Round.count + ":" + score;

			sharedPreferences.edit().putString("Save", file).apply();
		}
	}

	void Load() {
		//Read save data
		String file = sharedPreferences.getString("Save", " ");
		if(!file.equals(" ")) {
			Round.count = Integer.valueOf(file.split(":")[0]);
			score = Integer.valueOf(file.split(":")[1]);

			//Set correct round
			for(int i = 0; i < Round.count; i++) Round.Next();

			sharedPreferences.edit().putString("Save", " ").apply();
			Win(0);
		}
	}

    void colorblind(View view) {
		Round.colorblind = ((Switch) view).isChecked();
		sharedPreferences.edit().putBoolean("Colorblind", Round.colorblind).apply();
	}
}
