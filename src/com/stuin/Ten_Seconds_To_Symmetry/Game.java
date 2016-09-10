package com.stuin.Ten_Seconds_To_Symmetry;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.view.View.*;
import android.renderscript.*;
import android.view.animation.*;

public class Game extends Activity {
    int size = 5;
    int change;
    int maxColor = 3;
    int points = 0;
    Random random = new Random();
    CountDownTimer countDownTimer;
    boolean win = false;
    int[] grid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getActionBar().hide();
    }
    
    public void clear() {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.Top);
        gridLayout.removeAllViews();
        gridLayout = (GridLayout) findViewById(R.id.Bottom);
        gridLayout.removeAllViews();
    }

    public void generate(View v) {
        grid = new int[size * size];
        
        for(int i = 0; i < grid.length; i++) grid[i] = random.nextInt(maxColor);
        change = random.nextInt(grid.length);
        
        TextView textView = (TextView) findViewById(R.id.button);
        textView.setText("Next Round");
        textView.setVisibility(View.GONE);

        textView = (TextView) findViewById(R.id.Score);
        textView.setVisibility(View.GONE);
        
        GridLayout gridLayout = (GridLayout) findViewById(R.id.Top);
        gridLayout.setColumnCount(size);
        setGrid(gridLayout,true);

        gridLayout = (GridLayout) findViewById(R.id.Bottom);
        gridLayout.setColumnCount(size);
        setGrid(gridLayout,false);
        
        textView = (TextView) findViewById(change);
        int c = random.nextInt(maxColor - 1);
        if(grid[change] == c) c = maxColor - 1;
        setColor(textView,c);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(0);

        countDownTimer = new CountDownTimer(10000,100) {
            @Override
            public void onTick(long l) {
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setProgress(progressBar.getProgress() + 1);

                TextView textView = (TextView) findViewById(R.id.Left);
                textView.setText((l / 1000) + "");

                textView = (TextView) findViewById(R.id.Right);
                textView.setText((l / 1000) + "");
            }

            @Override
            public void onFinish() {
                loseListener.onClick(null);
            }
        }.start();
    }
    
    public void setGrid(GridLayout gridLayout, boolean top) {
        int i = 0;
        for(int cell : grid) {
            TextView textView = new TextView(this);
            textView.setWidth(100);
            textView.setHeight(100);
            setColor(textView,cell);
            gridLayout.addView(textView);
            
            if(i == change) {
                textView.setOnClickListener(winListener);
                if(top) textView.setId(change);
            } else textView.setOnClickListener(loseListener);
            i++;
        }
    }

    public void setColor(TextView textView, int i) {
        switch(i) {
            case 0:
                textView.setBackgroundColor(Color.RED);
                break;
            case 1:
                textView.setBackgroundColor(Color.BLUE);
                break;
            case 2:
                textView.setBackgroundColor(Color.GREEN);
                break;
            case 3:
                textView.setBackgroundColor(Color.WHITE);
                break;
            case 4:
                textView.setBackgroundColor(Color.BLACK);
                break;
        }
    }
    
    View.OnClickListener loseListener = new View.OnClickListener() {
        @Override
        public void onClick(View p1) {
            TextView textView = (TextView) findViewById(R.id.button);
            textView.setVisibility(View.VISIBLE);

            countDownTimer.cancel();
            clear();
            
            size--;
            win = false;
            if(size < 3) {
                if(maxColor > 2) {
                    maxColor--;
                    size = 6;
                } else size = 3;
            }
            
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            points -= 2 * progressBar.getProgress() * (size / 2 + maxColor);

            textView = (TextView) findViewById(R.id.Score);
            textView.setText("-" + points + "-");
            textView.setVisibility(View.VISIBLE);
        }
    };
    
    View.OnClickListener winListener = new View.OnClickListener() {
        @Override
        public void onClick(View p1) {
            TextView textView = (TextView) findViewById(R.id.button);
            textView.setVisibility(View.VISIBLE);

            countDownTimer.cancel();
            clear();
            
            if(size < 9 && (win || maxColor == 2)) {
                size++;
                win = false;
            }
            else win = true;
            if(size == 9 && maxColor < 5)  {
                size = 5;
                maxColor++;
            } else if(size == 9) size = 8;

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            points += (progressBar.getMax() - progressBar.getProgress()) * (size / 2 + maxColor);

            textView = (TextView) findViewById(R.id.Score);
            textView.setText("+" + points + "+");
            textView.setVisibility(View.VISIBLE);
        }
    };
}
