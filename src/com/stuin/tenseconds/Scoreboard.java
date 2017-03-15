package com.stuin.tenseconds;

import android.content.Context;
import android.content.SharedPreferences;
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


	public Scoreboard(Player player) {
	    this.player = player;
	    sharedPreferences = player.getContext().getSharedPreferences("TenSeconds", Context.MODE_PRIVATE);

	    highScore = sharedPreferences.getInt("HighScore", 0);
	    expanded = sharedPreferences.getBoolean("Expanded", false);

	    labels = player.getResources().getStringArray(R.array.app_labels);
    }

    public void win(int time) {
	    score += time * (Round.size /2) * Round.colors;

	    if(Round.size == 8 && Round.next) {
	        if((Round.colors == 5 && !expanded) || Round.colors == 8) {
	        	done(true);
	        	return;
			}
			else {
	            Round.colors++;
	            Round.size = 5;
	            Round.next = false;
            }
        } else {
	        if(Round.next) Round.size++;
	        Round.next = !Round.next;
        }

		String text = "+" + score + '+';
		((TextView) player.getChildAt(0)).setText(text);

		((TextView) player.getChildAt(2)).setText(labels[4]);
    }

    public void done(boolean win) {
		String text = labels[0] + score;
		if(win) text = labels[1] + score;
		((TextView) player.getChildAt(0)).setText(text);

		Timer timer = (Timer) player.getChildAt(1);
		if(score > highScore) {
			timer.write(labels[2]);

			sharedPreferences.edit().putInt("HighScore", score).apply();
			highScore = score;
		} else timer.write(labels[3] + highScore);

		((TextView) player.getChildAt(2)).setText(labels[5]);
    }
}
