package com.stuin.tenseconds;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.stuin.cleanvisuals.Settings;
import com.stuin.cleanvisuals.SliderSync;
import com.stuin.tenseconds.Views.Player;
import com.stuin.tenseconds.Views.RateDialog;

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

        //Set drawer animation
        LinearLayout drawer = (LinearLayout) findViewById(R.id.Drawer_Layout);
        FrameLayout icon = (FrameLayout) findViewById(R.id.Drawer_Button);
        player.slideDrawer = new SliderSync(drawer, icon);
        player.slideDrawer.setup(true, Round.length, 200, 250);

        //Show app version
        String string;
        try {
            string = 'v' + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            //Unlock debug mode
            if(BuildConfig.DEBUG) {
                string += "a";

                Settings.Set("Expanded", true);
                Settings.Set("Rated", false);
                //Settings.Set("RateDialog", false);
            }
        } catch(PackageManager.NameNotFoundException e) {
            string = "Version not found";
        }
        ((TextView) findViewById(R.id.Drawer_Version)).setText(string);

        //Link settings switches
        Settings.LinkId(R.id.Drawer_Tutorial, "Tutorial");
        Settings.LinkId(R.id.Drawer_Versus, "Versus");
    }


    public void StartGame(View view) {
        //Make sure dimensions set
        if(unSet) Setup();

        //Begin next round
        if(!Round.moving && !player.Playing()) {
            Round.Generate(this);
            player.Start();
            player.slideDrawer.hide();
        }
    }

    public void Drawer(View view) {
        switch(view.getId()) {
            case R.id.Drawer_Button:
                //Show drawer
                player.slideDrawer.showPrimary();
				
				//Hide certain buttons
				Round.Visible(findViewById(R.id.Drawer_Modes), Round.count == 0);
                Round.Visible(findViewById(R.id.Drawer_Quit), Round.count != 0);
                Round.Visible(findViewById(R.id.Drawer_Rate), !Settings.Get("Rated"));
                break;
            case R.id.Drawer_Layout:case R.id.Relative:
                //Hide drawer
                player.slideDrawer.showSecondary();
                break;
            case R.id.Drawer_Quit:
                //Quit game
                player.scoreboard.Done(false);
                break;
            case R.id.Drawer_Rate:
                //Rate app
                RateDialog rateDialog = new RateDialog();
                rateDialog.show(getFragmentManager(), "RateDialog");
                break;
            case R.id.Drawer_Tutorial:case R.id.Drawer_Versus:
                //Load Gamemode
				Settings.SetId(view.getId(), true);
                StartGame(null);
				break;
        }
    }

    @Override
    public void onBackPressed() {
        if(!player.Playing()) {
            //Hide drawer or go to home screen
            if(!player.slideDrawer.showSecondary() && Round.loss) player.Menu();
            //Pause game
        } else if(!Round.moving) player.Clear();
    }
}
