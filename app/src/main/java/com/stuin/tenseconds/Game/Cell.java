package com.stuin.tenseconds.Game;

import android.view.View;
import android.widget.FrameLayout;
import com.stuin.tenseconds.Round;

/**
 * Created by Stuart on 3/14/2017.
 */
public class Cell {
	//Square place and colors
    private byte x;
    private byte y;
    private byte color;
    private byte mark = -1;

    //Related views
    private FrameLayout topView;
    private FrameLayout botView;

    public Cell(byte color, byte nx, byte ny) {
		//Create cell
        this.color = color;
        this.x = nx;
        this.y = ny;
    }

    public void setMark(byte mark) {
        //Set unique color for mark
        if(mark == color)
            mark = (byte) (Round.colors - 1);
        this.mark = mark;
    }

    public void makeView(View view, boolean top) {
        //Get future variable link
        if(top)
            topView = (FrameLayout) view;
        else
            botView = (FrameLayout) view;

        //Set constant properties
        view.setMinimumWidth(Round.scale);
        view.setMinimumHeight(Round.scale);
        view.setOnClickListener(clickListener);
        view.setPadding(0,0,0,0);
        ((FrameLayout) view).removeAllViewsInLayout();

        //Set proper color
        view.setBackground(ColorReferences.getColor(color));
        if(mark > -1 && top)
            view.setBackground(ColorReferences.getColor(mark));
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!Round.moving) {
                //Get variables
                boolean top = (topView == view);
                Player player = (Player) view.getParent().getParent();

                //Check if correct
                if(player.playing()) {
                    if(mark == -1) {
                        //Find adjacent cells
                        for(int nx = x - 1; nx <= x + 1; nx++)
                            for(int ny = y - 1; ny <= y + 1; ny++) {
                                if(nx > -1 && nx < Round.size && ny > -1 && ny < Round.size) {
                                    int pos = ny * Round.size + nx;

                                    //Check if win
                                    if(Round.cells.get(pos).mark > -1) {
                                        player.win(top);
                                        return;
                                    }
                                }
                            }

                        //If none correct
                        player.lose();
                    } else
                        player.win(top);
                }
            }
        }
    };

    void display() {
        //Shade if not marked
        if(mark == -1) {
            topView.setBackground(ColorReferences.getShadedColor(color));
            botView.setBackground(ColorReferences.getShadedColor(color));
        }
    }
}
