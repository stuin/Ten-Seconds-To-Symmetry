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
	    this.player = player;
	    sharedPreferences = player.getContext().getSharedPreferences("TenSeconds", Context.MODE_PRIVATE);

	    highScore = sharedPreferences.getInt("HighScore", 0);
	    expanded = sharedPreferences.getBoolean("Expanded", false);
	    Round.colorblind = sharedPreferences.getBoolean("Colorblind", false);

	    labels = player.getResources().getStringArray(R.array.app_labels);
    }

    public void win(int time) {
	    score += time * (Round.size / 2) * Round.colors;

	    next();

		RelativeLayout relativeLayout = (RelativeLayout) player.getParent();
		String text = "+" + score + '+';
		((TextView) relativeLayout.getChildAt(0)).setText(text);

		((TextView) relativeLayout.getChildAt(1)).setText(labels[4]);
    }

    public void done(boolean win) {
		String text = labels[0] + score;
		if(win) text = labels[1] + score;

		RelativeLayout relativeLayout = (RelativeLayout) player.getParent();
		((TextView) relativeLayout.getChildAt(0)).setText(text);
		((TextView) relativeLayout.getChildAt(1)).setText(labels[5]);

		Timer timer = (Timer) player.getChildAt(1);
		if(score > highScore) {
			timer.write(labels[2]);

			sharedPreferences.edit().putInt("HighScore", score).apply();
			highScore = score;
		} else timer.write(labels[3] + highScore);
		
		Round.reset();
    }

    void save() {
		if(!Round.loss) {
			String file = Round.count + ":" + score;

			sharedPreferences.edit().putString("Save", file).apply();
		}
	}

	void load() {
		String file = sharedPreferences.getString("Save", " ");
		if(!file.equals(" ")) {
			Round.count = Integer.valueOf(file.split(":")[0]);
			score = Integer.valueOf(file.split(":")[1]);

			for(int i = 0; i < Round.count; i++) next();

			sharedPreferences.edit().putString("Save", " ").apply();
			win(0);
		}
	}

	private void next() {
		if(!Round.next) Round.next = true;
		else {
			if(Round.size != 8) Round.size++;
			else {
				if((Round.colors == 5 && !expanded) || Round.colors == 8) {
					done(true);
					return;
				}
				Round.colors++;
				Round.size = 5;
				Round.next = false;
			}
		}
	}

    void colorblind(View view) {
		Round.colorblind = ((Switch) view).isChecked();
		sharedPreferences.edit().putBoolean("Colorblind", Round.colorblind).apply();
	}
}
