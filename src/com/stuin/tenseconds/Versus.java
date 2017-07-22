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
    private int topScore;
    private int botScore;

    public Versus(Player player) {
        this.player = player;
        relativeLayout = (RelativeLayout) player.getParent();
    }

    @Override
    public void Win(int time, boolean top) {
        if(top) topScore++;
        else botScore++;

        //Write top score
        String text = '+' + Round.Separate(botScore) + " / " + Round.Separate(topScore) + '+';
        if(!top) text = text.replace('+', '-');
        ((TextView) relativeLayout.getChildAt(0)).setText(text);

        //Write bottom score
        text = '+' + Round.Separate(topScore) + " / " + Round.Separate(botScore) + '+';
        if(top) text = text.replace('+', '-');
        ((TextView) relativeLayout.getChildAt(2)).setText(text);

        //Prepare next round
        if(Round.size == 9 && Round.next &&
                ((Round.colors == 5 && !Settings.Get("Expanded")) || Round.colors == 8)) Done(true);
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
        topText += Round.Separate(botScore) + " / " + Round.Separate(topScore);
        botText += Round.Separate(topScore) + " / " + Round.Separate(botScore);

        //Write to text
        ((TextView) relativeLayout.getChildAt(0)).setText(topText);
        ((TextView) relativeLayout.getChildAt(2)).setText(botText);
    }

    @Override
    public void Load() {
        //Make text place changes
        relativeLayout.getChildAt(2).setTranslationY(Round.length / -2.5f);
        relativeLayout.getChildAt(1).setVisibility(View.GONE);
        relativeLayout.getChildAt(0).setRotation(180);
        relativeLayout.findViewById(R.id.Bar_Right).setRotation(180);

        Settings.Set("Tutorial", false);
        Settings.Set("Versus", true);
    }

    @Override
    public void Save() {
        //Reset text placing
        relativeLayout.getChildAt(1).setVisibility(View.VISIBLE);
        relativeLayout.getChildAt(0).setRotation(0);
        relativeLayout.findViewById(R.id.Bar_Right).setRotation(0);

        Settings.Set("Versus", false);
        player.Menu();
    }
}
