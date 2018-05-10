package com.stuin.tenseconds.Menu;

import android.app.*;
import android.content.DialogInterface;
import android.os.Bundle;
import com.stuin.tenseconds.R;

/**
 * Created by Stuart on 7/14/2017.
 */
public class Changelog extends DialogFragment {
    public Changelog() {
        //Arbitrary empty constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Get all text
        String title = getResources().getString(R.string.changelog_title);
        String button = getResources().getString(R.string.changelog_button);
        String text = getResources().getString(R.string.changelog_text);

        //Build complete view
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setMessage(text).setNeutralButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    public static void viewFragment(Activity activity) {
        //Create and show dialog
        FragmentManager fm = activity.getFragmentManager();
        Changelog dialog = new Changelog();
        dialog.show(fm, "fragment_alert");
    }

}
