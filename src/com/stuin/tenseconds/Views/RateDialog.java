package com.stuin.tenseconds.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.stuin.tenseconds.MainActivity;
import com.stuin.tenseconds.R;
import com.stuin.tenseconds.Settings;

/**
 * Created by Stuart on 7/14/2017.
 */
public class RateDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] labels = getResources().getStringArray(R.array.app_dialog);

        //Ask for rating
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(labels[0]).setPositiveButton(labels[1], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                ((MainActivity) getActivity()).Rate();
            }
        }).setNegativeButton(labels[2], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }
}
