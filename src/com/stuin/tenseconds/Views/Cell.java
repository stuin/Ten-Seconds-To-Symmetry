package com.stuin.tenseconds.Views;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.graphics.*;
import com.stuin.tenseconds.*;

/**
 * Created by Stuart on 3/14/2017.
 */
public class Cell extends FrameLayout {
	//Square placement
    private int x;
    private int y;
    private int scale;
    private boolean top;

	//Square features
    public int color;
    public int mark = -1;

    public Cell(Context context, int color, int nx, int ny, int scale) {
		//Create cell
        super(context);
        this.color = color;
        this.x = nx;
        this.y = ny;
        this.scale = scale;

        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Round.moving) {
					//Check if correct
                    Player player = (Player) getParent().getParent();
                    if(mark == -1) {
						//Find adjacent cells
                        for(int nx = x - 1; nx <= x + 1; nx++) for(int ny = y - 1; ny <= y + 1; ny++) {
                            if(nx > -1 && nx < Round.size && ny > -1 && ny < Round.size) {
                                int pos = ny * Round.size + nx;
								//Check if win
                                if(Round.cells.get(pos).mark > -1) {
                                    player.Win(top);
                                    return;
                                }
                            }
                        }
						//If loss or direct hit
                        player.Lose();
                    } else player.Win(top);
                }
            }
        };

		//Finalize cell
		setMinimumWidth(scale);
		setMinimumHeight(scale);
		setOnClickListener(clickListener);
    }
	
	Cell Copy() {
		//Create opposite cell
		Cell cell = new Cell(getContext(), color, x, y, scale);
		cell.mark = mark;
		cell.top = true;
		return cell;
	}
	
	void setColor(int color) {
		//Display color of square
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
            case 5:
                setBackgroundColor(Color.YELLOW);
                break;
            case 6:
                setBackgroundColor(Color.MAGENTA);
                break;
            case 7:
                setBackgroundColor(Color.CYAN);
                break;
		}
	}

    void display() {
		//Create shade over cell
        TextView space = new TextView(new ContextThemeWrapper(getContext(), R.style.style_background));
        space.setBackgroundColor(R.color.app_menu);
        space.setMinimumHeight(scale);
        space.setMinimumWidth(scale);
        space.setTextSize(0);
        addView(space);
    }
}
