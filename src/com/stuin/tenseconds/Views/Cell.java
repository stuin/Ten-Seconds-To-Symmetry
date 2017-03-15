package com.stuin.tenseconds.Views;

import android.content.Context;
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
		setBackgroundColor(getColor());
		setOnClickListener(clickListener);
    }
	
	private int getColor() {
		switch(color) {
            case 0:
                return Color.RED;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.WHITE;
            case 4:
                return Color.BLACK;
		}
		return 0;
	}

    void display() {
        Space space = new Space(getContext());
        space.setMinimumHeight(Round.scale);
        space.setMinimumWidth(Round.scale);
        space.setBackgroundColor(Color.BLACK);
        space.setAlpha(.25f);
        addView(space);
    }
}
