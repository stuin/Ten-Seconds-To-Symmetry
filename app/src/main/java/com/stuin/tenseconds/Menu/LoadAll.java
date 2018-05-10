package com.stuin.tenseconds.Menu;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.stuin.cleanvisuals.Drift.Engine;
import com.stuin.cleanvisuals.Settings;
import com.stuin.tenseconds.BuildConfig;
import com.stuin.tenseconds.R;

public class LoadAll {
    public SharedPreferences sharedPreferences;

    public LoadAll(Activity activity) {
        //Get save data
        sharedPreferences = activity.getSharedPreferences("TenSeconds", Context.MODE_PRIVATE);

        String[] KEYS = {
                "Expert", "Hexmode", "Rated", "Versus", "ExpertUnlocked", "HexmodeUnlocked"};
        Settings.load(sharedPreferences, KEYS, false);

        String[] KEYS2 = {
                "Background", "RateDialog", "Tutorial", "Music"};
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
                sharedPreferences.edit().putInt("HighScore", -1).apply();
        }

        //Link settings switches
        Settings.linkId(R.id.Drawer_Tutorial, "Tutorial");
        Settings.linkId(R.id.Second_Versus, "Versus");
        Settings.linkId(R.id.Drawer_Hexmode, "Hexmode");

        //Set background button
        ToggleButton button = activity.findViewById(R.id.Drawer_Background);
        button.setChecked(Settings.linkId(button.getId(), "Background"));

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
