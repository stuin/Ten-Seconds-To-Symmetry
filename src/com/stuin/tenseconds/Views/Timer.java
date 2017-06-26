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
import com.stuin.tenseconds.*;

/**
 * Created by Stuart on 3/12/2017.
 */
public class Timer extends FrameLayout {
    private SliderSync sliderSync;
    private LinearLayout linearLayout;
    private int time;

    public Timer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
		//Wait to do formatting
        post(new Runnable() {
            @Override
            public void run() {
				//Find parts
                linearLayout = (LinearLayout) getChildAt(0);
                sliderSync = new SliderSync(linearLayout, getChildAt(1));

				//Resize timer bar
                ProgressBar progressBar = (ProgressBar) linearLayout.getChildAt(1);
                progressBar.getLayoutParams().width = getWidth() / 4;
                progressBar.invalidate();
            }
        });
    }

    void Clear() {
        //Clear timer bar
        ProgressBar progressBar = (ProgressBar) linearLayout.getChildAt(1);
        progressBar.setProgress(0);

        //Clear text
        String text = getResources().getText(R.string.app_time).toString();
        ((TextView) linearLayout.getChildAt(0)).setText(text);
        ((TextView) linearLayout.getChildAt(2)).setText(text);

        //Display timer
        //sliderSync.showPrimary();
    }

    public void Write(String text) {
        //Write message
        TextView textView = (TextView) getChildAt(1);
        textView.setText(text);

        //Display text
        sliderSync.showSecondary();
    }

    void Start() {
        if(sliderSync.unSet) {
            //Setup animation if not done yet
            sliderSync.setup(true, -Round.length, Round.length, 500);
        }

        //Start timer at 0
        Clear();
		endTutorial = false;
        countDownTimer.start();
    }

    int End() {
        //Get remaining time
        countDownTimer.cancel();
		Show();
        return time;
    }
	
	void Show() {
		sliderSync.showPrimary();
	}
	
	private boolean endTutorial = false;

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
			
			//Hide tutorial text
			if(!endTutorial && time < 6000 && Settings.Get("Tutorial")) {
				sliderSync.showPrimary();
				endTutorial = true;
			}
        }

        @Override
        public void onFinish() {
            ((Player) getParent()).Lose();
        }
    };
}
