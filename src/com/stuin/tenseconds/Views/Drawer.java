package com.stuin.tenseconds.Views;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.stuin.cleanvisuals.Settings;
import com.stuin.cleanvisuals.SliderSync;
import com.stuin.tenseconds.BuildConfig;
import com.stuin.tenseconds.MainActivity;
import com.stuin.tenseconds.R;
import com.stuin.tenseconds.Round;

/**
 * Created by Stuart on 7/14/2017.
 */
public class Drawer extends LinearLayout {
    public SliderSync slideDrawer;

    private MainActivity activity;

    public Drawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void Setup(MainActivity activity) {
        this.activity = activity;

        //Set drawer animation
        FrameLayout icon = (FrameLayout) activity.findViewById(R.id.Drawer_Button);
        slideDrawer = new SliderSync(this, icon);
        slideDrawer.setup(true, Round.length, 200, 250);

        //Show app version
        String string;
        try {
            string = 'v' + activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
            //Unlock debug mode
            if(BuildConfig.DEBUG) {
                string += "a";

                Settings.set("Expanded", true);
                Settings.set("Rated", false);
                //Settings.Set("RateDialog", false);
            }
        } catch(PackageManager.NameNotFoundException e) {
            string = "Version not found";
        }
        ((TextView) findViewById(R.id.Drawer_Version)).setText(string);

        //Link settings switches
        Settings.linkId(R.id.Drawer_Tutorial, "Tutorial");
        Settings.linkId(R.id.Drawer_Versus, "Versus");

        //Set background button
        ToggleButton button = (ToggleButton) findViewById(R.id.Drawer_Background);
        button.setChecked(Settings.get("Background"));
    }

    public void Button(View view) {
        switch(view.getId()) {
            case R.id.Drawer_Button:
                //Show drawer
                slideDrawer.showPrimary();

                //Hide certain buttons
                Round.Visible(findViewById(R.id.Drawer_Modes), Round.count == 0);
                Round.Visible(findViewById(R.id.Drawer_Quit), Round.count != 0);
                Round.Visible(findViewById(R.id.Drawer_Rate), !Settings.get("Rated"));
                break;
            case R.id.Drawer_Layout:case R.id.Main_Layout:
                //Hide drawer
                if(slideDrawer.primaryShown()) slideDrawer.showSecondary();
                break;
            case R.id.Drawer_Quit:
                //Quit game
                activity.player.scoreboard.Done(false);
                break;
            case R.id.Drawer_Rate:
                //Rate app
                RateDialog rateDialog = new RateDialog();
                rateDialog.show(activity.getFragmentManager(), "RateDialog");
                break;
            case R.id.Drawer_Background:
                //Toggle Background
                ToggleButton button = (ToggleButton) view;
                Settings.set("Background", button.isChecked());
                break;
            case R.id.Drawer_Tutorial:case R.id.Drawer_Versus:
                //Load Gamemode
                Settings.setId(view.getId(), true);
                activity.StartGame(null);
                break;
        }
    }
}
