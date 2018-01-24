package com.stuin.tenseconds.Game;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.stuin.cleanvisuals.Slide.SliderSync;
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
            }
        });
    }

    public void clear() {
        //clear timer bar
        ProgressBar progressBar = (ProgressBar) linearLayout.getChildAt(1);
        progressBar.setProgress(0);

        //clear text
        String text = getResources().getText(R.string.app_time).toString();
        ((TextView) linearLayout.getChildAt(0)).setText(text);
        ((TextView) linearLayout.getChildAt(2)).setText(text);
    }

    public void write(String text) {
        //write message
        TextView textView = (TextView) getChildAt(1);
        textView.setText(text);

        //Display text
        sliderSync.showSecondary();
    }

    public void start() {
        if(sliderSync.unSet) {
            //setup animation if not done yet
            sliderSync.setup(true, -Round.length, Round.length, 500);
        }

        //start timer at 10
        clear();
		endTutorial = false;
        mainTimer.start();
    }

    public int end() {
        //Get remaining time
        mainTimer.cancel();
        resetTimer.cancel();
		show();
        return time;
    }
	
	public void show() {
		if(sliderSync.unSet) {
			//setup animation if not done yet
			sliderSync.setup(true, -Round.length, Round.length, 500);
		}
		
		sliderSync.showPrimary();
	}

	private void setTime(int time) {
        //show remaining seconds
        ((TextView) linearLayout.getChildAt(0)).setText(String.valueOf(time / 1000));
        ((TextView) linearLayout.getChildAt(2)).setText(String.valueOf(time / 1000));
    }
	
	private boolean endTutorial = false;

    private CountDownTimer mainTimer = new CountDownTimer(10000, 10) {
        @Override
        public void onTick(long l) {
            time = (int) l;
            setTime(time);

            //add to timer bar
            ProgressBar progressBar = (ProgressBar) linearLayout.getChildAt(1);
            progressBar.setProgress(1000 - (time / 10));
			
			//hide tutorial text
			if(!endTutorial && time < 6000) {
			    sliderSync.showPrimary();
				endTutorial = true;
			}
        }

        @Override
        public void onFinish() {
            ((Player) getParent()).lose();
        }
    };

    public void startReset() {
        clear();
        resetTimer.start();
    }

    private CountDownTimer resetTimer = new CountDownTimer(5000, 10) {
        @Override
        public void onTick(long l) {
            time = (int) l;
            setTime(time);

            //add to timer bar
            ProgressBar progressBar = (ProgressBar) linearLayout.getChildAt(1);
            progressBar.setProgress(1000 - (time / 5));
        }

        @Override
        public void onFinish() {
            Round.generate();
            ((Player) getParent()).start();
        }
    };
}
