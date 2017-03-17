package com.stuin.tenseconds.Views;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.stuin.tenseconds.Round;

/**
 * Created by Stuart on 3/12/2017.
 */
public class Timer extends LinearLayout {
    private int time;

    public Timer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void write(String text) {
        ((TextView) getChildAt(0)).setText("");
        getChildAt(1).setVisibility(GONE);

        TextView textView = (TextView) getChildAt(2);
        textView.setText(text);
    }

    void start() {
        ProgressBar progressBar = (ProgressBar) getChildAt(1);
		progressBar.setVisibility(VISIBLE);
        progressBar.setProgress(0);

        ((TextView) getChildAt(0)).setText("0 ");
        ((TextView) getChildAt(2)).setText(" 0");

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

            ProgressBar progressBar = (ProgressBar) getChildAt(1);
            progressBar.setProgress(1000 - (time / 10));

            String seconds = " " + (time / 1000) + " ";
            ((TextView) getChildAt(0)).setText(seconds);
            ((TextView) getChildAt(2)).setText(seconds);
        }

        @Override
        public void onFinish() {
            ((Player) getParent()).lose();
        }
    };
}
