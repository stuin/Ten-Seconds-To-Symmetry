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

public class Game extends Activity {
    int size = 5;
    Point change;
    Random random = new Random();
    Point[] grid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        generate(null);
    }

    public void generate(View v) {
        grid = new Point[size * size];
        int i = 0;
        for(int x = 0; x < size; x++) for(int y = 0; y < size; y++) {
            grid[i] = new Point(i);
            grid[i].color = random.nextInt(4);
            i++;
        }
        change = grid[random.nextInt(grid.length)];
        change.color = random.nextInt(3);
        if(change.color == grid[change.i].color) change.color = 3;
        showGrid();
    }

    public void showGrid() {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.Top);
        gridLayout.setColumnCount(size);
        for(Point p : grid) {
            TextView textView = new TextView(this);
            textView.setWidth(100);
            textView.setHeight(100);
            setColor(textView,p.color);
            gridLayout.addView(textView);
        }

        gridLayout = (GridLayout) findViewById(R.id.Bottom);
        gridLayout.setColumnCount(size);
        for(Point p : grid) {
            TextView textView = new TextView(this);
            textView.setWidth(100);
            textView.setHeight(100);
            if(p.i == change.i) setColor(textView,change.color);
            else setColor(textView,p.color);
            gridLayout.addView(textView);
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
                textView.setBackgroundColor(Color.BLACK);
                break;
        }
    }
}