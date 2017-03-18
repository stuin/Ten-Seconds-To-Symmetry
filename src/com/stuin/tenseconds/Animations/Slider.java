package com.stuin.tenseconds.Animations;

import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * Created by Stuart on 3/16/2017.
 */
public class Slider {
    private TranslateAnimation enterAnimation;
    private TranslateAnimation exitAnimation;
    private View view;

    public boolean shown;
    public boolean unSet = true;
    public Endings endings;

    public Slider(View view) {
        this.view = view;
    }

    public void setup(boolean side, int start, int time) {
        unSet = false;

        if(side) enterAnimation = new TranslateAnimation(start, 0, 0, 0);
        else enterAnimation = new TranslateAnimation(0, 0, start, 0);
        enterAnimation.setDuration(time);

        if(side) exitAnimation = new TranslateAnimation(0, start, 0, 0);
        else exitAnimation = new TranslateAnimation(0, 0, 0, start);
        exitAnimation.setDuration(time);
    }

    public boolean enter() {
        if(!shown) {
            view.setVisibility(View.VISIBLE);
            view.startAnimation(enterAnimation);

            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    shown = true;
                    if(endings != null) endings.enter();
                }
            }, enterAnimation.getDuration());
            return true;
        }
        return false;
    }

    public boolean exit() {
        if(shown) {
            view.startAnimation(exitAnimation);

            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setVisibility(View.GONE);
                    shown = false;
                    if(endings != null) endings.exit();
                }
            }, exitAnimation.getDuration());
            return true;
        }
        return false;
    }

    public interface Endings {
        void enter();
        void exit();
    }
}
