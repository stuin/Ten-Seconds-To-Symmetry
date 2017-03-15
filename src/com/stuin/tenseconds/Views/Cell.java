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
		setColor();
		setOnClickListener(clickListener);
    }
	
	public Cell clone() {
		Cell cell = new Cell(getContext(), color, x, y);
		cell.mark = mark;
		return cell;
	}
	
	void setColor() {
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
	}

    void display() {
        Space space = new Space(getContext());
        space.setMinimumHeight(Round.scale);
        space.setMinimumWidth(Round.scale);
        space.setBackgroundColor(getResources().getColor(R.color.app_layout));
        addView(space);
    }
}
