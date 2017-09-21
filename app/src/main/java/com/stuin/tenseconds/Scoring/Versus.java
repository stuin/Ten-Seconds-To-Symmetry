package com.stuin.tenseconds.Scoring;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stuin.cleanvisuals.Settings;
import com.stuin.tenseconds.Game.Player;
import com.stuin.tenseconds.Game.Timer;
import com.stuin.tenseconds.R;
import com.stuin.tenseconds.Round;

public class Versus implements Scoreboard {
    private Player player;
    private RelativeLayout relativeLayout;
    private int topScore = 0;
    private int botScore = 0;
    private boolean set;

    public Versus(Player player) {
        this.player = player;

        //Immediate changes
        relativeLayout = (RelativeLayout) player.getParent();
        relativeLayout.findViewById(R.id.Bar_Right).setRotation(180);
        Settings.set("Tutorial", false);
    }

    private void setup() {
        //Make text place changes
        relativeLayout.getChildAt(4).setTranslationY(Round.length / -2.5f);
        relativeLayout.getChildAt(4).setVisibility(View.VISIBLE);
        relativeLayout.getChildAt(3).setVisibility(View.GONE);
        relativeLayout.getChildAt(2).setRotation(180);

        set = true;
    }

    @Override
    public void win(int time, boolean top) {
        if(!set) setup();

        if(top) topScore++;
        else botScore++;

        //write vertical score
        String text = "+" + topScore + " / " + botScore + "+";
        if(!top) text = text.replace('+', '-');
        ((TextView) relativeLayout.getChildAt(2)).setText(text);

        //write bottom score
        text = "+" + botScore + " / " + topScore + "+";
        if(top) text = text.replace('+', '-');
        ((TextView) relativeLayout.getChildAt(4)).setText(text);

        //Prepare next round
        if(Round.size == 9 && Round.next &&
                ((Round.colors == 5 && !Settings.get("Expanded")) || Round.colors == 8)) done(true);
        else Round.next();

        //start short timer
        ((Timer) player.getChildAt(1)).startReset(false);
    }

    @Override
    public void done(boolean win) {
        //Make variables
        String[] labels = player.getResources().getStringArray(R.array.app_versus);
        String topText;
        String botText;

        //Set phrases
        if(topScore > botScore) {
            topText = labels[0];
            botText = labels[1];
        } else if(botScore > topScore) {
            topText = labels[1];
            botText = labels[0];
        } else {
            topText = labels[2];
            botText = labels[2];
        }

        //add scores
        topText += topScore + " / " + botScore;
        botText += botScore + " / " + topScore;

        //write to text
        ((TextView) relativeLayout.getChildAt(2)).setText(topText);
        ((TextView) relativeLayout.getChildAt(4)).setText(botText);

        //Reset variables
        Round.reset();
        Round.loss = true;
        topScore = 0;
        botScore = 0;
    }

    @Override
    public boolean load() {
        return false;
    }

    @Override
    public void save() {
        //reset text placing
        relativeLayout.getChildAt(3).setVisibility(View.VISIBLE);
        relativeLayout.getChildAt(4).setVisibility(View.GONE);
        relativeLayout.getChildAt(2).setRotation(0);
        relativeLayout.findViewById(R.id.Bar_Right).setRotation(0);
        ((Timer) relativeLayout.findViewById(R.id.Bar_Layout)).end();

        Settings.set("Versus", false);
        player.menu();
        set = false;
        Round.reset();
    }
}
