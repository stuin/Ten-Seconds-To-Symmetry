package com.stuin.tenseconds.Menu;

import android.content.Context;
import android.media.MediaPlayer;
import com.stuin.cleanvisuals.Settings;
import com.stuin.tenseconds.R;

public class Music {
    private MediaPlayer player;
    private int time = 0;

    public Music(Context context) {
        player = MediaPlayer.create(context, R.raw.tenseconds);
        player.setLooping(true);
        player.start();
        player.setVolume(0, 0);
    }

    public void set() {
        if(Settings.get("Music"))
            play();
        else
            pause();
    }

    public void play() {
        player.setVolume(1, 1);
        player.seekTo(time);
    }

    public void pause() {
        player.setVolume(0, 0);
        time = player.getCurrentPosition() - 5;
        if(time < 0)
            time = 0;
    }
}
