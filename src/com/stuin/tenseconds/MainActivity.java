package com.stuin.tenseconds;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.stuin.cleanvisuals.SliderSync;
import com.stuin.tenseconds.Views.Player;

/**
 * Created by Stuart on 2/14/2017.
 */
public class MainActivity extends Activity {
    private Player player;
    private boolean unSet = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //Start scoreboard system
        player = (Player) findViewById(R.id.Player_Layout);
        player.scoreboard = new Scoreboard(player);
        //((TextView) findViewById(R.id.Colorblind)).setChecked(Round.colorblind);

        //Run setup when ready
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.Relative);
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
        Round.reset();
        player.scoreboard.load();
    }

    @Override
    protected void onPause() {
        //Save and pause game
        super.onPause();
        player.clear();
        player.scoreboard.save();
    }

    private void setup() {
        unSet = false;

        //Set various dimensions
        int text = findViewById(R.id.Relative).getWidth() / 40;
        Round.length = findViewById(R.id.Relative).getHeight() / 2;

        //Prepare the title text
        TextView textView = (TextView) findViewById(R.id.Top_Text);
        textView.setTextSize(text);
        textView.setTranslationY(Round.length / 2.5f);

        //Prepare the button
        textView = (TextView) findViewById(R.id.Bot_Text);
        textView.setTextSize(text);
        textView.setTranslationY(Round.length / -2.5f);

        //Get drawer and button
        LinearLayout drawer = (LinearLayout) findViewById(R.id.Drawer_Layout);
        FrameLayout icon = (FrameLayout) findViewById(R.id.Drawer_Button);

        //Make fancy animation
        player.slideDrawer = new SliderSync(drawer, icon);
        player.slideDrawer.setup(true, Round.length, 200, 250);

        //Set text in drawer
        for(int i = 0; i < drawer.getChildCount(); i++) {
            if(drawer.getChildAt(i) instanceof TextView) {
                textView = (TextView) drawer.getChildAt(i);
                textView.setTextSize(text);
            }
        }

        String string;
        try {
            string = 'v' + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            if(BuildConfig.DEBUG) string += "a";
        } catch(PackageManager.NameNotFoundException e) {
            string = "Version not found";
        }
        textView = (TextView) findViewById(R.id.Drawer_version);
        textView.setText(string);
        textView.setTextSize(text / 2);
    }


    public void startGame(View view) {
        //Make sure dimensions set
        if(unSet) setup();

        //Begin next round
        if(!Round.moving && !player.playing()) {
            Round.generate(this);
            player.start();
            player.slideDrawer.hide();
        }
    }

    public void drawer(View view) {
        switch(view.getId()) {
            case R.id.Drawer_Button:
                //Show drawer
                player.slideDrawer.showPrimary();

                //Write current level
                String text = "Level " + Round.count;
                ((TextView) findViewById(R.id.Level)).setText(text);
                break;
            case R.id.Drawer_Layout:case R.id.Relative:
                //Hide drawer
                player.slideDrawer.showSecondary();
                break;
            case R.id.Drawer_Colorblind:
                //Toggle theoretical colorblind mode
                player.scoreboard.colorblind(view);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(!player.playing()) {
            //Hide drawer or go to home screen
            if(!player.slideDrawer.showSecondary() && Round.loss) player.menu();
            //Pause game
        } else if(!Round.moving) player.clear();
    }
}
