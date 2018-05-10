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
    private SliderSync slideDrawer;
	private SliderSync secondPage;
    private MainActivity activity;

    public Drawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setup(MainActivity activity) {
        this.activity = activity;

        //Set drawer animation
        FrameLayout icon = activity.findViewById(R.id.Drawer_Button);
        slideDrawer = new SliderSync(this, icon);
        slideDrawer.setup(true, Round.length, 200, 250);
		
		//Set second page animation
		secondPage = new SliderSync(this.findViewById(R.id.Drawer_First), this.findViewById(R.id.Drawer_Second));
		secondPage.setup(true, -Round.length, Round.length, 250);
    }
	
	public boolean hide(boolean full) {
		if(full)
			return slideDrawer.hide();
		return slideDrawer.showSecondary();
	}
	
	public void showPage(int number) {
		//Iterate through pages
		LinearLayout layout = this.findViewById(R.id.Drawer_Second);
		for(int i = 2; i < layout.getChildCount(); i++) {
			
			//Hide all but chosen one
			View v = layout.getChildAt(i);
			Round.visible(v, i == number + 1);
		}
		
		//Show second page of drawer
		slideDrawer.showPrimary();
		secondPage.showSecondary();
	}
	
	private void openSite(int stringID) {
		//Open website
		Uri url = Uri.parse(getResources().getString(stringID));
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, url);
        activity.startActivity(launchBrowser);
	}

    public void button(View view) {
        switch(view.getId()) {
            //Visibility of drawer
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
                if(slideDrawer.primaryShown()) 
					slideDrawer.showSecondary();
                break;

            //Special buttons
            case R.id.Drawer_Background:case R.id.Drawer_Music:
			
                //Toggle button setting
                ToggleButton button = (ToggleButton) view;
                Settings.setId(view.getId(), button.isChecked());
                if(view.getId() == R.id.Drawer_Music)
                    activity.music.set();
                break;
            case R.id.Drawer_Tutorial:case R.id.Second_Versus: case R.id.Drawer_Hexmode:
			
                //load Gamemode
                Settings.setId(view.getId(), true);
                activity.startGame(null);
                break;
            case R.id.Drawer_Quit:

                //Quit game
                slideDrawer.showSecondary();
                activity.player.scoreboard.done(false);
                break;

            //Other menus
            case R.id.Drawer_Changelog:

                //Open changelog
                Changelog.viewFragment(activity);
                break;
			case R.id.Drawer_Credits:
			
				//Go to credits page
				showPage(1);
				break;
            case R.id.Drawer_Rate:
			
                //Go to ratings page
                showPage(2);
                break;
            case R.id.Drawer_Versus:

                //Go to versus page
                showPage(3);
                break;
			case R.id.Second_Back:
			
				//Return to first page
				secondPage.showPrimary();
				break;

            //External links
			case R.id.Second_Website:
			
				//Open personal website
                openSite(R.string.second_credits_url);
				break;
			case R.id.Second_Soundcloud:
			
				//Open soundcloud link
                openSite(R.string.second_credits_soundcloud);
				break;
            case R.id.Second_Accept:
                Settings.set("Rated", true);

                //Open app play store website
                openSite(R.string.app_url);
                break;
        }
    }
}
