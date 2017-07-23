package com.stuin.tenseconds;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stuin.cleanvisuals.Settings;
import com.stuin.tenseconds.Views.Player;
import com.stuin.tenseconds.Views.Timer;

public class Versus implements Scoreboard {
    private Player player;
    private RelativeLayout relativeLayout;
    private int topScore = 0;
    private int botScore = 0;
    private boolean set;

    public Versus(Player player) {
        this.player = player;
        relativeLayout = (RelativeLayout) player.getParent();
        relativeLayout.findViewById(R.id.Bar_Right).setRotation(180);
    }

    private void Setup() {
        //Make text place changes
        relativeLayout.getChildAt(4).setTranslationY(Round.length / -2.5f);
        relativeLayout.getChildAt(4).setVisibility(View.VISIBLE);
        relativeLayout.getChildAt(3).setVisibility(View.GONE);
        relativeLayout.getChildAt(2).setRotation(180);

        Settings.set("Tutorial", false);
        set = true;
    }

    @Override
    public void Win(int time, boolean top) {
        if(!set) Setup();

        if(top) topScore++;
        else botScore++;

        //Write vertical score
        String text = "+" + topScore + " / " + botScore + "+";
        if(!top) text = text.replace('+', '-');
        ((TextView) relativeLayout.getChildAt(2)).setText(text);

        //Write bottom score
        text = "+" + botScore + " / " + topScore + "+";
        if(top) text = text.replace('+', '-');
        ((TextView) relativeLayout.getChildAt(4)).setText(text);

        //Prepare next round
        if(Round.size == 9 && Round.next &&
                ((Round.colors == 5 && !Settings.get("Expanded")) || Round.colors == 8)) Done(true);
        else Round.Next();

        //Start short timer
        ((Timer) player.getChildAt(1)).StartReset(false);
    }

    @Override
    public void Done(boolean win) {
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

        //Add scores
        topText += topScore + " / " + botScore;
        botText += botScore + " / " + topScore;

        //Write to text
        ((TextView) relativeLayout.getChildAt(2)).setText(topText);
        ((TextView) relativeLayout.getChildAt(4)).setText(botText);

        Round.Reset();
        Round.loss = true;
        topScore = 0;
        botScore = 0;
    }

    @Override
    public void Load() {

    }

    @Override
    public void Save() {
        //Reset text placing
        relativeLayout.getChildAt(3).setVisibility(View.VISIBLE);
        relativeLayout.getChildAt(4).setVisibility(View.GONE);
        relativeLayout.getChildAt(2).setRotation(0);
        relativeLayout.findViewById(R.id.Bar_Right).setRotation(0);
        ((Timer) relativeLayout.findViewById(R.id.Bar_Layout)).End();

        Settings.set("Versus", false);
        player.Menu();
        set = false;
        Round.Reset();
    }
}
