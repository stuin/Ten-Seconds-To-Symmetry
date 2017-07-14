package com.stuin.tenseconds.Background;

import java.util.Random;

/**
 * Created by Stuart on 7/13/2017.
 */
public class Range {
    public int min;
    public int max;

    public Range() {

    }

    public Range(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int GetInt(Random rand) {
        return rand.nextInt(max) + min;
    }
}
