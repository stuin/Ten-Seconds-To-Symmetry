package com.stuin.tenseconds;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stuin.tenseconds.Views.Grid;
import com.stuin.tenseconds.Views.Player;

/**
 * Created by Stuart on 2/14/2017.
 */
public class MainActivity extends Activity {
    private Player player;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
		findViewById(R.id.Relative).post(new Runnable() {
			@Override
			public void run() {
				setup();
			}
		});
    }
	
    protected void setup() {
        super.onStart();
        Round.reset();
        player = (Player) findViewById(R.id.PlayerLayout);
        player.scoreboard = new Scoreboard(player);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.Relative);
        ((Grid) findViewById(R.id.TopGrid)).top = true;

        Round.length = relativeLayout.getHeight() / 2;
        if(relativeLayout.getWidth() > Round.length) Round.length = relativeLayout.getWidth();

        TextView textView = (TextView) findViewById(R.id.TopText);
        textView.setTextSize(Round.length / 40);
		textView.setTranslationY(Round.length / 2.5f);

        textView = (TextView) findViewById(R.id.BotText);
        textView.setTextSize(Round.length / 45);
		textView.setTranslationY(Round.length / -2.5f);
    }

    public void startGame(View view) {
        if(!Round.moving) {
            Round.generate(this);
            player.start();
        }
    }
}
