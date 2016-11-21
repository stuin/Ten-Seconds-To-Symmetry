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
    private int scale = 0;
    private String[] labels;
    private boolean expanded = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Set up app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        if(getActionBar() != null)getActionBar().hide();

        //Retrieve high score
        highScore = getPreferences(Context.MODE_PRIVATE).getInt("HighScore", 0);

        //Set spacing
        findViewById(R.id.TopSpace).setMinimumHeight(1000);
        findViewById(R.id.BottomSpace).setMinimumHeight(1000);

        labels = getResources().getStringArray(R.array.game_labels);
    }

    private void clear() {
        //Ready exit animation
        new CountDownTimer(500,1) {
            @Override
            public void onTick(long l) {
                Space space = (Space) findViewById(R.id.TopSpace);
                space.setMinimumHeight(2 * (500 - (int)l));
                space = (Space) findViewById(R.id.BottomSpace);
                space.setMinimumHeight(2 * (500 - (int)l));
            }
            @Override
            public void onFinish() {
                Space space = (Space) findViewById(R.id.TopSpace);
                space.setMinimumHeight(1000);
                space = (Space) findViewById(R.id.BottomSpace);
                space.setMinimumHeight(1000);

                //Clear grids
                GridLayout gridLayout = (GridLayout) findViewById(R.id.Top);
                gridLayout.removeAllViews();
                gridLayout = (GridLayout) findViewById(R.id.Bottom);
                gridLayout.removeAllViews();
            }
        }.start();
    }

    public void generate(View v) {
        grid = new int[size * size];
        scale = findViewById(R.id.relative).getHeight();

        //Generate grid
        for(int i = 0; i < grid.length; i++) grid[i] = random.nextInt(maxColor);
        change = random.nextInt(grid.length);
        if(change % size == 0 || change % size == size - 1 || change < size || change / size == size - 1) change = random.nextInt(grid.length);

        //Set up grids
        GridLayout gridLayout = (GridLayout) findViewById(R.id.Top);
        setGrid(gridLayout,true);
        gridLayout = (GridLayout) findViewById(R.id.Bottom);
        setGrid(gridLayout,false);

        //Show right timer
        TextView textView = (TextView) findViewById(R.id.Right);
        String t = 10 + " ";
        textView.setText(t);
        textView.setVisibility(View.VISIBLE);

        //Show left timer
        textView = (TextView) findViewById(R.id.Left);
        t = " " + 10;
        textView.setText(t);
        textView.setVisibility(View.VISIBLE);

        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

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

                //Hide menu
                findViewById(R.id.button).setVisibility(View.GONE);
                findViewById(R.id.Score).setVisibility(View.GONE);
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
            textView.setWidth(scale / 2 / (size + 1));
            textView.setHeight(scale / 2 / (size + 1));
            gridLayout.addView(textView);

            //Set changed square
            if(i == change) {
                textView.setOnClickListener(winListener);
                if(top) {
                    cell = random.nextInt(maxColor - 1);
                    if(grid[change] == cell) cell = maxColor - 1;
                    textView.setId(R.id.TopChange);
                } else textView.setId(R.id.BottomChange);

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
            if(change > -1) {
                countDownTimer.cancel();
                change = -1;

                TextView textView = (TextView) findViewById(R.id.TopChange);
                textView.setBackgroundColor(Color.parseColor("#ffb100"));
                textView = (TextView) findViewById(R.id.BottomChange);
                textView.setBackgroundColor(Color.parseColor("#ffb100"));

                new CountDownTimer(1000,1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        clear();
                        endGame();
                    }
                }.start();

                //Show score
                textView = (TextView) findViewById(R.id.Score);
                String t = labels[4] + points;
                textView.setText(t);
                textView.setVisibility(View.VISIBLE);
            }
        }
    };

    private void endGame() {
        //Check high score
        String t;
        TextView textView = (TextView) findViewById(R.id.Left);
        if (points > highScore) {
            t = labels[2];
            highScore = points;

            SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
            editor.putInt("HighScore", highScore);
            editor.apply();
        } else t = labels[3] + highScore;
        textView.setText(t);
        textView.setVisibility(View.VISIBLE);

        //Hide timer
        findViewById(R.id.Right).setVisibility(View.GONE);
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        points = 0;

        //Set button
        textView = (TextView) findViewById(R.id.button);
        textView.setText(labels[1]);
        textView.setVisibility(View.VISIBLE);

        //Clear game
        second = false;
        size = 5;
        maxColor = 3;
    }

    @Override
    public void onBackPressed() {
        if(change > -1) loseListener.onClick(null);
    }

    private View.OnClickListener winListener = new View.OnClickListener() {
        @Override
        public void onClick(View p1) {
            if(change > -1) {
                countDownTimer.cancel();
                change = -1;
                clear();

                //Check level scaling
                if (size < 9 && second) {
                    size++;
                    second = false;
                } else second = true;
                if (size == 9 && maxColor < 5) {
                    size = 5;
                    maxColor++;
                }

                //Calculate score
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                points += (progressBar.getMax() - progressBar.getProgress()) * (size / 2 + maxColor);

                if(maxColor > 5 && !expanded || maxColor == 8) {
                    //Show Score
                    TextView textView = (TextView) findViewById(R.id.Score);
                    String t = labels[5] + points;
                    textView.setText(t);
                    textView.setVisibility(View.VISIBLE);

                    endGame();
                } else {
                    //Set button
                    TextView textView = (TextView) findViewById(R.id.button);
                    textView.setText(labels[0]);
                    textView.setVisibility(View.VISIBLE);

                    //Show Score
                    textView = (TextView) findViewById(R.id.Score);
                    String t = "+" + points + "+";
                    textView.setText(t);
                    textView.setVisibility(View.VISIBLE);
                }
            }
        }
    };
}
