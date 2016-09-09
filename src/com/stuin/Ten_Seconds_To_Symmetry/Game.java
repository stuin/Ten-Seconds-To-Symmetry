package com.stuin.Ten_Seconds_To_Symmetry;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.view.View.*;
import android.renderscript.*;

public class Game extends Activity {
    int size = 5;
    int change;
    int maxColor = 3;
    Random random = new Random();
    boolean win = false;
    int[] grid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getActionBar().hide();
        generate(null);
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
        textView.setVisibility(View.INVISIBLE);
        
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
            textView.setText("You Lose");
            textView.setVisibility(View.VISIBLE);
            clear();
            
            size--;
            win = false;
            if(size < 3) {
                size = 6;
                if(maxColor > 2) maxColor--;
            }
        }
    };
    
    View.OnClickListener winListener = new View.OnClickListener() {
        @Override
        public void onClick(View p1) {
            TextView textView = (TextView) findViewById(R.id.button);
            textView.setText("You Win");
            textView.setVisibility(View.VISIBLE);
            clear();
            
            if(size < 9 && (win || maxColor == 2)) {
                size++;
                win = false;
            }
            else win = true;
            if(size == 9 && maxColor < 5)  {
                size = 5;
                maxColor++;
            }
            
        }
    };
}
