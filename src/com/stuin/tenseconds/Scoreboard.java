package com.stuin.tenseconds;

import android.content.Context;
import android.content.SharedPreferences;
import com.stuin.tenseconds.Service;

public class Scoreboard {
	private MainActivity mainActivity;
	private SharedPreferences sharedPreferences;
	private int highScore;
	private boolean expanded;

	public int score;

	Scoreboard(MainActivity mainActivity) {
	    this.mainActivity = mainActivity;
	    sharedPreferences = mainActivity.getSharedPreferences("TenSeconds", Context.MODE_PRIVATE);

	    highScore = sharedPreferences.getInt("HighScore", 0);
	    expanded = sharedPreferences.getBoolean("Expanded", false);
    }

    public void win(int time) {
	    score += time * (Round.size /2) * Round.colors;

	    if(Round.size == 8 && Round.next) {
	        if((Round.colors == 5 && !expanded) || Round.colors == 8) {

            } else {
	            Round.colors++;
	            Round.size = 5;
	            Round.next = false;
            }
        } else {
	        if(Round.next) Round.size++;
	        Round.next = !Round.next;
        }
    }

    public void done() {

    }
}
