package com.stuin.tensecondstosymmetry;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Space;

import java.util.Random;

public class Game extends Activity {
    private int size = 5;
    private int change = -1;
    private int maxColor = 3;
    private int points = 0;
    private Random random = new Random();
    private CountDownTimer countDownTimer;
    private boolean second = false;
    private int[] grid;
    private int highScore = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Set up app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        if(getActionBar() != null)getActionBar().hide();

        //Retrieve high score
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        highScore = sharedPreferences.getInt("HighScore", 0);

        //Start intro animation
        new CountDownTimer(1000,1) {
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
                space.setMinimumHeight(50);
                space = (Space) findViewById(R.id.BottomSpace);
                space.setMinimumHeight(50);
            }
        }.start();
    }

    private void clear() {
        //Clear grids
        GridLayout gridLayout = (GridLayout) findViewById(R.id.Top);
        gridLayout.removeAllViews();
        gridLayout = (GridLayout) findViewById(R.id.Bottom);
        gridLayout.removeAllViews();

        //Set spacing
        Space space = (Space) findViewById(R.id.TopSpace);
        space.setMinimumHeight(50);
        space = (Space) findViewById(R.id.BottomSpace);
        space.setMinimumHeight(50);

        //Clear settings
        countDownTimer.cancel();
        change = -1;
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

        //Set up grids
        GridLayout gridLayout = (GridLayout) findViewById(R.id.Top);
        setGrid(gridLayout,true);
        gridLayout = (GridLayout) findViewById(R.id.Bottom);
        setGrid(gridLayout,false);

        //Show timer
        findViewById(R.id.Left).setVisibility(View.VISIBLE);
        findViewById(R.id.Right).setVisibility(View.VISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        findViewById(R.id.HighScore).setVisibility(View.GONE);

        //Set spacing
        Space space = (Space) findViewById(R.id.TopSpace);
        space.setMinimumHeight(1000);
        space = (Space) findViewById(R.id.BottomSpace);
        space.setMinimumHeight(1000);

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
        gridLayout.setColumnCount(size);
        for(int cell : grid) {
            //Set default square
            TextView textView = new TextView(this);
            textView.setWidth(100);
            textView.setHeight(100);
            gridLayout.addView(textView);

            //Set changed square
            if(i == change) {
                textView.setOnClickListener(winListener);
                if(top) {
                    cell = random.nextInt(maxColor - 1);
                    if(grid[change] == cell) cell = maxColor - 1;
                }
            } else textView.setOnClickListener(loseListener);
            i++;

            //Set color
            switch(cell) {
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
    }

    private View.OnClickListener loseListener = new View.OnClickListener() {
        @Override
        public void onClick(View p1) {
            clear();

            //Set button
            TextView textView = (TextView) findViewById(R.id.button);
            String t = "Restart";
            textView.setText(t);
            textView.setVisibility(View.VISIBLE);

            //Show score
            textView = (TextView) findViewById(R.id.Score);
            t = "Game Lost: " + points;
            textView.setText(t);
            textView.setVisibility(View.VISIBLE);

            //Check high score
            textView = (TextView) findViewById(R.id.HighScore);
            if(points > highScore) {
                t = "New High Score";
                highScore = points;

                SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("HighScore",highScore);
                editor.commit();
            } else t = "High Score: " + highScore;
            textView.setText(t);
            textView.setVisibility(View.VISIBLE);

            //Hide timer
            findViewById(R.id.Left).setVisibility(View.GONE);
            findViewById(R.id.Right).setVisibility(View.GONE);
            findViewById(R.id.progressBar).setVisibility(View.GONE);

            //Clear game
            points = 0;
            second = false;
            size = 5;
            maxColor = 3;
        }
    };

    @Override
    public void onBackPressed() {
        if(change > -1) loseListener.onClick(null);
    }

    private View.OnClickListener winListener = new View.OnClickListener() {
        @Override
        public void onClick(View p1) {
            clear();

            //Set button
            TextView textView = (TextView) findViewById(R.id.button);
            String t = "Next Round";
            textView.setText(t);
            textView.setVisibility(View.VISIBLE);

            //Check level scaling
            if(size < 9 && second) {
                size++;
                second = false;
            }
            else second = true;
            if(size == 9 && maxColor < 5)  {
                size = 5;
                maxColor++;
            } else if(size == 9) size = 8;

            //Calculate score
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            points += (progressBar.getMax() - progressBar.getProgress()) * (size / 2 + maxColor);

            //Show Score
            textView = (TextView) findViewById(R.id.Score);
            t = "+" + points + "+";
            textView.setText(t);
            textView.setVisibility(View.VISIBLE);
        }
    };
}
