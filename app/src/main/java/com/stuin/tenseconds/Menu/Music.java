package com.stuin.tenseconds.Menu;

import android.content.Context;
import android.media.MediaPlayer;
import com.stuin.cleanvisuals.Settings;
import com.stuin.tenseconds.R;

public class Music {
    private MediaPlayer player;

    public Music(Context context) {
        player = MediaPlayer.create(context, R.raw.tenseconds);
        player.setLooping(true);
        set();
        player.start();
    }

    public void set() {
        if(Settings.get("Music"))
            player.setVolume( 1, 1);
        else
            player.setVolume( 0, 0);
        player.seekTo(0);
    }

    public void end() {
        player.stop();
    }
}
