package com.stuin.tenseconds.Menu;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.stuintech.cleanvisuals.Settings;
import com.stuin.tenseconds.BuildConfig;
import com.stuin.tenseconds.R;

public class LoadAll {
    public SharedPreferences sharedPreferences;

    public LoadAll(Activity activity) {
        //Get save file
        sharedPreferences = activity.getSharedPreferences("TenSeconds", Context.MODE_PRIVATE);

        //Load game save values (default false)
        String[] KEYS = {
                "Expert", "Hexmode", "Rated", "Versus", "Music"};
        Settings.load(sharedPreferences, KEYS, false);

        //Load system save values (default true)
        String[] KEYS2 = {
                "Background", "RateDialog", "Tutorial"};
        Settings.load(sharedPreferences, KEYS2, true);

        //Show app version
        String version = getVersion(activity);
        ((TextView) activity.findViewById(R.id.Drawer_Version)).setText(version);

        //Check new version
        if(sharedPreferences.getInt("HighScore", -1) > 0) {
            String savedVersion = sharedPreferences.getString("Version", "none");
            if(!version.equals(savedVersion)) {

                //Show changelog
                Changelog.viewFragment(activity);
                sharedPreferences.edit().putString("Version", version).apply();
            }

            //Clear old score system
            if(savedVersion.equals("none"))
                sharedPreferences.edit().putInt("HighScore", sharedPreferences.getInt("HighScore", -1) / 1000).apply();
        } else
            sharedPreferences.edit().putString("Version", version).apply();

        //Link settings switches
        Settings.linkId(R.id.Drawer_Game_Tutorial, "Tutorial");
        Settings.linkId(R.id.Second_Versus, "Versus");
        Settings.linkId(R.id.Drawer_Game_Hexmode, "Hexmode");

        //Set background button
        ToggleButton button = activity.findViewById(R.id.Drawer_Background);
        button.setChecked(Settings.linkId(button.getId(), "Background"));

        //Set background button
        button = activity.findViewById(R.id.Drawer_Game_Expert);
        button.setChecked(Settings.linkId(button.getId(), "Expert"));

        //Set music button
        button = activity.findViewById(R.id.Drawer_Music);
        button.setChecked(Settings.linkId(button.getId(), "Music"));
    }

    private String getVersion(Activity activity) {
        String string;
        try {
            string = 'v' + activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;

            //Unlock debug mode
            if(BuildConfig.DEBUG) {
                string += "a";

                Settings.set("Rated", false);
            }
        } catch(PackageManager.NameNotFoundException e) {
            string = "no version";
        }
        return string;
    }
}
