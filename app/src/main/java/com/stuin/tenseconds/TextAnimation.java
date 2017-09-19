package com.stuin.tenseconds;

import android.widget.TextView;

public class TextAnimation {
    public Runnable finish;

    private TextView textView;
    private String target;
    private int speed;

    private int digit;
    private boolean interrupt;

    public TextAnimation(TextView textView) {
        this.textView = textView;
    }

    public void stop() {
        interrupt = true;
    }

    public void shift(String target, int speed) {
        this.target = target;
        this.speed = speed;

        interrupt = false;
        digit = 0;
        textView.postDelayed(shift, speed);
    }

    //Animation to fit text behind grid
    private Runnable shift = new Runnable() {
        public void run() {
            if(!textView.getText().equals(target) && !interrupt) {
                String text = textView.getText().toString();
                if(text.length() > target.length()) {
                    //Shrink text by one letter
                    text = text.substring(1, text.length());
                } else if(text.length() <= target.length()) {
                    //Set next letter
                    if(digit >= text.length()) {
                        text += target.toCharArray()[digit];
                    } else {
                        char[] chars = text.toCharArray();
                        chars[digit] = target.toCharArray()[digit];
                        text = String.copyValueOf(chars);
                    }

                    digit++;
                }

                //Finalize change
                textView.setText(text);
                if(!textView.getText().equals(target)) textView.postDelayed(shift, speed);
                else if(finish != null) finish.run();
            }
        }
    };


}
