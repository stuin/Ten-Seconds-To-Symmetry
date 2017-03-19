package com.stuin.tenseconds.Views;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.stuin.cleanvisuals.SliderSync;
import com.stuin.tenseconds.R;
import com.stuin.tenseconds.Round;

/**
 * Created by Stuart on 3/12/2017.
 */
public class Timer extends FrameLayout {
    private SliderSync sliderSync;
    private LinearLayout linearLayout;
    private int time;

    public Timer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    void clear() {
        //Clear timer bar
        ProgressBar progressBar = (ProgressBar) linearLayout.getChildAt(1);
        progressBar.setProgress(0);

        //Clear text
        String text = getResources().getText(R.string.app_time).toString();
        ((TextView) linearLayout.getChildAt(0)).setText(text);
        ((TextView) linearLayout.getChildAt(2)).setText(text);

        //Display timer
        sliderSync.showPrimary();
    }

    public void write(String text) {
        //Write message
        TextView textView = (TextView) getChildAt(1);
        textView.setText(text);

        //Display text
        sliderSync.showSecondary();
    }

    void start() {
        if(sliderSync.unSet) {
            //Setup animation if not done yet
            linearLayout = (LinearLayout) getChildAt(0);
            sliderSync = new SliderSync(linearLayout, getChildAt(1));
            sliderSync.setup(true, -Round.length, Round.length, 500);
        }

        //Start timer at 0
        clear();
        sliderSync.showPrimary();
        countDownTimer.start();
    }

    int end() {
        //Get remaining time
        countDownTimer.cancel();
        return time;
    }

    private CountDownTimer countDownTimer = new CountDownTimer(10000, 10) {
        @Override
        public void onTick(long l) {
            time = (int)l;

            //Add to timer bar
            ProgressBar progressBar = (ProgressBar) linearLayout.getChildAt(1);
            progressBar.setProgress(1000 - (time / 10));

            //Show remaining seconds
            ((TextView) linearLayout.getChildAt(0)).setText(String.valueOf(time / 1000));
            ((TextView) linearLayout.getChildAt(2)).setText(String.valueOf(time / 1000));
        }

        @Override
        public void onFinish() {
            ((Player) getParent()).lose();
        }
    };
}
