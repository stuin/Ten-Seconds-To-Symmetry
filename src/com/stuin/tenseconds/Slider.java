package com.stuin.tenseconds;

import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * Created by Stuart on 3/16/2017.
 */
public class Slider {
    private TranslateAnimation enterAnimation;
    private TranslateAnimation exitAnimation;
    private boolean shown;
    private View view;

    public boolean unSet = true;
    public boolean moving = false;

    public Slider(View view) {
        this.view = view;
    }

    public void setup(boolean side, int start) {
        unSet = false;

        if(side) enterAnimation = new TranslateAnimation(start, 0, 0, 0);
        else enterAnimation = new TranslateAnimation(0, 0, start, 0);
        enterAnimation.setDuration(500);

        if(side) exitAnimation = new TranslateAnimation(0, start, 0, 0);
        else exitAnimation = new TranslateAnimation(0, 0, 0, start);
        exitAnimation.setDuration(500);
    }

    public void open() {
        if(!shown && !moving) {
            moving = true;
            view.setVisibility(View.VISIBLE);
            view.startAnimation(enterAnimation);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    shown = true;
                    moving = false;
                }
            }, enterAnimation.getDuration());
        }
    }

    public void close() {
        if(shown && !moving) {
            moving = true;
            view.startAnimation(exitAnimation);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setVisibility(View.GONE);
                    shown = false;
                    moving = false;
                }
            }, exitAnimation.getDuration());
        }
    }
}
