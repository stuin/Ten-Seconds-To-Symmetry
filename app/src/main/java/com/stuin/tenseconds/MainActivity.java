package com.stuin.tenseconds;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.stuin.cleanvisuals.Settings;
import com.stuin.tenseconds.Menu.Drawer;
import com.stuin.tenseconds.Game.Player;
import com.stuin.tenseconds.Scoring.Single;

/**
 * Created by Stuart on 2/14/2017.
 */
public class MainActivity extends Activity {
    public Player player;

    private boolean unSet = true;
    private Drawer drawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //start scoreboard system
        player = (Player) findViewById(R.id.Main_Player);
        player.scoreboard = new Single(player);

        //Run setup when ready
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.Main_Layout);
        relativeLayout.post(new Runnable() {
            @Override
            public void run() {
                setup();
            }
        });
    }

    @Override
    protected void onResume() {
        //Refresh and load game
        super.onResume();
        player.scoreboard.load();
    }

    @Override
    protected void onPause() {
        //save and pause game
        super.onPause();
        player.clear();
        player.scoreboard.save();
    }

    private void setup() {
        unSet = false;

        //Set various dimensions
        Round.length = findViewById(R.id.Main_Layout).getHeight() / 2;

        //Set title text
        TextView textView = (TextView) findViewById(R.id.Top_Text);
        textView.setTranslationY(Round.length / 2.5f);
        player.titleAnimation = new TextAnimation(textView);
        textView.setMinWidth(textView.getWidth());
        textView.setText("");

        //Set button text
        textView = (TextView) findViewById(R.id.Bot_Button);
        textView.setTranslationY(Round.length / -2.5f);
        player.buttonAnimation = new TextAnimation(textView);

        //Drawer setup
        drawer = (Drawer) findViewById(R.id.Drawer_Layout);
        drawer.setup(this);

        //Check background start
        if(Settings.get("Background")) {
            Settings.set("Background", false);
            player.titleAnimation.finish = new Runnable() {
                @Override
                public void run() {
                    Settings.set("Background", true);
                    ((TextView) findViewById(R.id.Top_Text)).setMinWidth(0);
                    player.titleAnimation.finish = null;
                }
            };
        }

        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                player.titleAnimation.shift(getResources().getText(R.string.app_name).toString(), 50);
            }
        }, 300);
    }


    public void startGame(View view) {
        //Make sure dimensions set
        if(unSet) setup();
        player.titleAnimation.stop();
        player.buttonAnimation.stop();

        //Begin next round
        if(!Round.moving && !player.playing()) {
            Round.generate(this);
            player.start();
            drawer.slideDrawer.hide();
        }
    }

    public void drawer(View view) {
        //Make sure dimensions set
        if(unSet) setup();

        drawer.button(view);
    }

    @Override
    public void onBackPressed() {
        if(!player.playing()) {
            //hide drawer or go to home screen
            if(!drawer.slideDrawer.showSecondary() && Round.loss) player.menu();
            if(Settings.get("Versus")) player.scoreboard.save();
            //Pause game
        } else if(!Round.moving && !Settings.get("Versus")) {
            player.clear();
            if(Round.tutorial > 0) Round.tutorial--;
        }
    }
}
