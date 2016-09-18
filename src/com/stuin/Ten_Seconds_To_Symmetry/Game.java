package com.stuin.Ten_Seconds_To_Symmetry;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;
import android.widget.*;

public class Game extends Activity {
    private int size = 5;
    private int change;
    private int maxColor = 3;
    private int points = 0;
    private Random random = new Random();
    private CountDownTimer countDownTimer;
    private boolean win = false;
    private int[] grid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        if(getActionBar() != null)getActionBar().hide();

        new CountDownTimer(500,1) {
            @Override
            public void onTick(long l) {
                Space space = (Space) findViewById(R.id.TopSpace);
                space.setMinimumHeight((int)l + 75);
                space = (Space) findViewById(R.id.BottomSpace);
                space.setMinimumHeight((int)l + 75);
            }
            @Override
            public void onFinish() {
                Space space = (Space) findViewById(R.id.TopSpace);
                space.setMinimumHeight(50);
                space = (Space) findViewById(R.id.BottomSpace);
                space.setMinimumHeight(50);
            }
        }.start();
    }

    private void clear() {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.Top);
        gridLayout.removeAllViews();
        gridLayout = (GridLayout) findViewById(R.id.Bottom);
        gridLayout.removeAllViews();
    }

    public void generate(View v) {
        grid = new int[size * size];

        //Generate grid
        for(int i = 0; i < grid.length; i++) grid[i] = random.nextInt(maxColor);
        change = random.nextInt(grid.length);

        //Hide menu
        TextView textView = (TextView) findViewById(R.id.button);
        textView.setVisibility(View.GONE);
        textView = (TextView) findViewById(R.id.Score);
        textView.setVisibility(View.GONE);

        //Set spaces
        Space space = (Space) findViewById(R.id.TopSpace);
        space.setMinimumHeight(1000);
        space = (Space) findViewById(R.id.BottomSpace);
        space.setMinimumHeight(1000);

        //Set up grids
        GridLayout gridLayout = (GridLayout) findViewById(R.id.Top);
        setGrid(gridLayout,true);
        gridLayout = (GridLayout) findViewById(R.id.Bottom);
        setGrid(gridLayout,false);

        //Ready startup animation
        new CountDownTimer(500,1) {
            @Override
            public void onTick(long l) {
                Space space = (Space) findViewById(R.id.TopSpace);
                space.setMinimumHeight(2 * (int)l);
                space = (Space) findViewById(R.id.BottomSpace);
                space.setMinimumHeight(2 * (int)l);
            }
            @Override
            public void onFinish() {
                Space space = (Space) findViewById(R.id.TopSpace);
                space.setMinimumHeight(20);
                space = (Space) findViewById(R.id.BottomSpace);
                space.setMinimumHeight(20);
            }
        }.start();

        //Set difference color
        textView = (TextView) findViewById(change);
        int c = random.nextInt(maxColor - 1);
        if(grid[change] == c) c = maxColor - 1;
        setColor(textView,c);

        //Start timer
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        countDownTimer = new CountDownTimer(10500,100) {
            @Override
            public void onTick(long l) {
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setProgress(progressBar.getProgress() + 1);

                TextView textView = (TextView) findViewById(R.id.Right);
                String t = (l / 1000) + " ";
                textView.setText(t);

                textView = (TextView) findViewById(R.id.Left);
                t = " " + (l / 1000);
                textView.setText(t);
            }
            @Override
            public void onFinish() {
                loseListener.onClick(null);
            }
        }.start();
    }

    private void setGrid(GridLayout gridLayout, boolean top) {
        int i = 0;
        for(int cell : grid) {
            TextView textView = new TextView(this);
            textView.setWidth(100);
            textView.setHeight(100);
            setColor(textView,cell);
            gridLayout.addView(textView);
            gridLayout.setColumnCount(size);
            
            if(i == change) {
                textView.setOnClickListener(winListener);
                if(top) textView.setId(change);
            } else textView.setOnClickListener(loseListener);
            i++;
        }
    }

    private void setColor(TextView textView, int i) {
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

    private View.OnClickListener loseListener = new View.OnClickListener() {
        @Override
        public void onClick(View p1) {
            TextView textView = (TextView) findViewById(R.id.button);
            String t = "Restart";
            textView.setText(t);
            textView.setVisibility(View.VISIBLE);

            Space space = (Space) findViewById(R.id.TopSpace);
            space.setMinimumHeight(50);
            space = (Space) findViewById(R.id.BottomSpace);
            space.setMinimumHeight(50);

            countDownTimer.cancel();
            clear();

            textView = (TextView) findViewById(R.id.Score);
            t = "Game Lost: " + points;
            textView.setText(t);
            textView.setVisibility(View.VISIBLE);
            
            points = 0;
            win = false;
            size = 5;
            maxColor = 3;
        }
    };

    private View.OnClickListener winListener = new View.OnClickListener() {
        @Override
        public void onClick(View p1) {
            TextView textView = (TextView) findViewById(R.id.button);
            String t = "Next Round";
            textView.setText(t);
            textView.setVisibility(View.VISIBLE);

            Space space = (Space) findViewById(R.id.TopSpace);
            space.setMinimumHeight(50);
            space = (Space) findViewById(R.id.BottomSpace);
            space.setMinimumHeight(50);

            countDownTimer.cancel();
            clear();
            
            if(size < 9 && win) {
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
            t = "+" + points + "+";
            textView.setText(t);
            textView.setVisibility(View.VISIBLE);
        }
    };
}
