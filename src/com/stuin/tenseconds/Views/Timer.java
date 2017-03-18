package com.stuin.tenseconds.Views;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.stuin.tenseconds.Animations.SliderSync;
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
        post(new Runnable() {
            @Override
            public void run() {
                linearLayout = (LinearLayout) getChildAt(0);
                sliderSync = new SliderSync(linearLayout, getChildAt(1));
            }
        });
    }

    public void clear() {
        ProgressBar progressBar = (ProgressBar) linearLayout.getChildAt(1);
        progressBar.setProgress(0);

        String text = getResources().getText(R.string.app_time).toString();
        ((TextView) linearLayout.getChildAt(0)).setText(text);
        ((TextView) linearLayout.getChildAt(2)).setText(text);

        sliderSync.showPrimary();
    }

    public void write(String text) {
        TextView textView = (TextView) getChildAt(1);
        textView.setText(text);

        sliderSync.showSecondary();
    }

    void start() {
        if(sliderSync.unSet) {
            sliderSync.setup(true, -Round.length, Round.length, 500);
        }

        clear();

        sliderSync.showPrimary();
        countDownTimer.start();
    }

    int end() {
        countDownTimer.cancel();
        return time;
    }

    private CountDownTimer countDownTimer = new CountDownTimer(10000, 10) {
        @Override
        public void onTick(long l) {
            time = (int)l;

            ProgressBar progressBar = (ProgressBar) linearLayout.getChildAt(1);
            progressBar.setProgress(1000 - (time / 10));

            ((TextView) linearLayout.getChildAt(0)).setText(String.valueOf(time / 1000));
            ((TextView) linearLayout.getChildAt(2)).setText(String.valueOf(time / 1000));
        }

        @Override
        public void onFinish() {
            ((Player) getParent()).lose();
        }
    };
}
