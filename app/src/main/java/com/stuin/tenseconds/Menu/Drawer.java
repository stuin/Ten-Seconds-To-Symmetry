package com.stuin.tenseconds.Menu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.*;
import com.stuin.cleanvisuals.Settings;
import com.stuin.cleanvisuals.Slide.SliderSync;
import com.stuin.tenseconds.BuildConfig;
import com.stuin.tenseconds.MainActivity;
import com.stuin.tenseconds.R;
import com.stuin.tenseconds.Round;

/**
 * Created by Stuart on 7/14/2017.
 */
public class Drawer extends RelativeLayout {
    public SliderSync slideDrawer;
	public SliderSync secondPage;

    private MainActivity activity;

    public Drawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setup(MainActivity activity) {
        this.activity = activity;

        //Set drawer animation
        FrameLayout icon = (FrameLayout) activity.findViewById(R.id.Drawer_Button);
        slideDrawer = new SliderSync(this, icon);
        slideDrawer.setup(true, Round.length, 200, 250);
		
		//Set second page animation
		secondPage = new SliderSync(this.findViewById(R.id.Drawer_First), this.findViewById(R.id.Drawer_Second));
		secondPage.setup(true, -Round.length, Round.length, 250);

        //show app version
        String string;
        try {
            string = 'v' + activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;

            //Unlock debug mode
            if(BuildConfig.DEBUG) {
                string += "a";

                //Settings.set("Expanded", true);
                Settings.set("Rated", false);
                //Settings.set("RateDialog", true);
            }
        } catch(PackageManager.NameNotFoundException e) {
            string = "Version not found";
        }
        ((TextView) findViewById(R.id.Drawer_Version)).setText(string);

        ((TextView) findViewById(R.id.Drawer_Games)).setText("Games: " + Round.games);

        //Link settings switches
        Settings.linkId(R.id.Drawer_Tutorial, "Tutorial");
        Settings.linkId(R.id.Drawer_Versus, "Versus");
        Settings.set("Versus", false);

        //Set background button
        ToggleButton button = findViewById(R.id.Drawer_Background);
        button.setChecked(Settings.get("Background"));
    }
	
	public void showPage(int number) {
		LinearLayout layout = this.findViewById(R.id.Drawer_Second);
		for(int i = 2; i < layout.getChildCount(); i++) {
			View v = layout.getChildAt(i);
			Round.visible(v, i == number + 1);
		}
		slideDrawer.showPrimary();
		secondPage.showSecondary();
	}

    public void button(View view) {
        switch(view.getId()) {
            case R.id.Drawer_Button:
                //show drawer
				secondPage.showPrimary();
                slideDrawer.showPrimary();

                //set visibility of certain buttons
                Round.visible(findViewById(R.id.Drawer_Modes), Round.count == 0);
                Round.visible(findViewById(R.id.Drawer_Quit), Round.count != 0);
                Round.visible(findViewById(R.id.Drawer_Rate), !Settings.get("Rated"));
                break;
            case R.id.Drawer_Layout:case R.id.Main_Layout:case R.id.Bar_Layout:case R.id.Second_Decline:
                //hide drawer
				secondPage.showPrimary();
                if(slideDrawer.primaryShown()) slideDrawer.showSecondary();
                break;
            case R.id.Drawer_Quit:
                //Quit game
                activity.player.scoreboard.done(false);
                break;
            case R.id.Drawer_Rate:
                //Rate app
                showPage(2);
                break;
            case R.id.Drawer_Background:
                //Toggle Background
                ToggleButton button = (ToggleButton) view;
                Settings.set("Background", button.isChecked());
                break;
            case R.id.Drawer_Tutorial:case R.id.Drawer_Versus:
                //load Gamemode
                Settings.setId(view.getId(), true);
                activity.startGame(null);
                break;
			case R.id.Second_Back:
				//Return to first page
				secondPage.showPrimary();
				break;
			case R.id.Drawer_Credits:
				//Go to credits page
				showPage(1);
				break;
			case R.id.Second_Website:
				//Open personal website
                Uri url = Uri.parse(getResources().getString(R.string.second_credits_url));
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, url);
                activity.startActivity(launchBrowser);
				break;
            case R.id.Second_Accept:
                Settings.set("Rated", true);

                //Open app page
                url = Uri.parse(getResources().getString(R.string.app_url));
                launchBrowser = new Intent(Intent.ACTION_VIEW, url);
                activity.startActivity(launchBrowser);
                break;
        }
    }
}
