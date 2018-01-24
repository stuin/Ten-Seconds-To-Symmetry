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

        //show app version
        String string;
        try {
            string = 'v' + activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;

            //Unlock debug mode
            if(BuildConfig.DEBUG) {
                string += "a";

                Settings.set("Rated", false);
                Settings.set("RateDialog", true);
            }
        } catch(PackageManager.NameNotFoundException e) {
            string = "no version";
        }
        ((TextView) activity.findViewById(R.id.Drawer_Version)).setText(string);

        //Link settings switches
        Settings.linkId(R.id.Drawer_Tutorial, "Tutorial");
        Settings.linkId(R.id.Drawer_Versus, "Versus");
        Settings.linkId(R.id.Drawer_Hexmode, "Hexmode");
        Settings.set("Versus", false);
        Settings.set("Hexmode", false);

        //Set background button
        ToggleButton button = activity.findViewById(R.id.Drawer_Background);
        button.setChecked(Settings.linkId(button.getId(), "Background"));

        //Set music button
        button = activity.findViewById(R.id.Drawer_Music);
        button.setChecked(Settings.linkId(button.getId(), "Music"));
    }
}
