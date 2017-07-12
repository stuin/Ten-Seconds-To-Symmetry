package com.stuin.tenseconds;

/**
 * Created by Stuart on 7/12/2017.
 */
public interface Scoreboard {
    void Win(int time, boolean top);
    void Done(boolean win);
    void Save();
    void Load();
}
