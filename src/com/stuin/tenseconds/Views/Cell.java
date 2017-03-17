package com.stuin.tenseconds.Views;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.graphics.*;
import com.stuin.tenseconds.*;

/**
 * Created by Stuart on 3/14/2017.
 */
public class Cell extends FrameLayout {
    private int x;
    private int y;

    public int color;
    public int mark = -1;

    public Cell(Context context, int color, int x, int y) {
        super(context);
        this.color = color;
        this.x = x;
        this.y = y;

        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Round.moving) {
                    Player player = (Player) getParent().getParent();
                    if(mark == -1) player.lose();
                    else player.win();
                }
            }
        };

		setMinimumWidth(Round.scale);
		setMinimumHeight(Round.scale);
		if(mark == -1) setColor(color);
		setOnClickListener(clickListener);
    }
	
	Cell copy() {
		Cell cell = new Cell(getContext(), color, x, y);
		cell.mark = mark;
		return cell;
	}
	
	void setColor(int color) {
		switch(color) {
            case 0:
                setBackgroundColor(Color.RED);
				break;
            case 1:
                setBackgroundColor(Color.GREEN);
				break;
            case 2:
                setBackgroundColor(Color.BLUE);
				break;
            case 3:
                setBackgroundColor(Color.WHITE);
				break;
            case 4:
                setBackgroundColor(Color.BLACK);
				break;
		}

		if(Round.colorblind) {
		    TextView textView = new TextView(new ContextThemeWrapper(getContext(), R.style.style_text));
		    textView.setText(String.valueOf(color));
		    textView.setWidth(Round.scale);
		    textView.setGravity(Gravity.CENTER);
		    textView.setTextSize(Round.text);

            removeAllViewsInLayout();
		    addView(textView);
        }
	}

    void display() {
        TextView space = new TextView(new ContextThemeWrapper(getContext(), R.style.style_background));
        space.setMinimumHeight(Round.scale);
        space.setMinimumWidth(Round.scale);
        addView(space);
    }
}
