package com.stuin.tenseconds.Scoring;

/**
 * Created by Stuart on 7/12/2017.
 */
public interface Scoreboard {
    void win(int time, boolean top);
    void done(boolean win);
    void save();
    void load();
}
