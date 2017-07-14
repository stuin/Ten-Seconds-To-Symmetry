package com.stuin.tenseconds;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.stuin.tenseconds.Views.Drawer;
import com.stuin.tenseconds.Views.Player;

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

        //Start scoreboard system
        player = (Player) findViewById(R.id.Player_Layout);
        player.scoreboard = new Single(player);

        //Run setup when ready
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.Relative);
        relativeLayout.post(new Runnable() {
            @Override
            public void run() {
                Setup();
            }
        });
    }

    @Override
    protected void onResume() {
        //Refresh and load game
        super.onResume();
        player.scoreboard.Load();
    }

    @Override
    protected void onPause() {
        //Save and pause game
        super.onPause();
        player.Clear();
        player.scoreboard.Save();
    }

    private void Setup() {
        unSet = false;

        //Set various dimensions
        Round.length = findViewById(R.id.Relative).getHeight() / 2;
        findViewById(R.id.Top_Text).setTranslationY(Round.length / 2.5f);
        findViewById(R.id.Bot_Text).setTranslationY(Round.length / -2.5f);

        drawer = (Drawer) findViewById(R.id.Drawer_Layout);
        drawer.Setup(this);
    }


    public void StartGame(View view) {
        //Make sure dimensions set
        if(unSet) Setup();

        //Begin next round
        if(!Round.moving && !player.Playing()) {
            Round.Generate(this);
            player.Start();
            drawer.slideDrawer.hide();
        }
    }

    public void Drawer(View view) {
        //Make sure dimensions set
        if(unSet) Setup();

        drawer.Button(view);
    }

    @Override
    public void onBackPressed() {
        if(!player.Playing()) {
            //Hide drawer or go to home screen
            if(!drawer.slideDrawer.showSecondary() && Round.loss) player.Menu();
            //Pause game
        } else if(!Round.moving) player.Clear();
    }
}
