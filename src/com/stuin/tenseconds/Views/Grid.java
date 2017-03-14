package com.stuin.tenseconds.Views;

import android.content.Context;
import android.widget.GridLayout;
import com.stuin.tenseconds.Round;

/**
 * Created by Stuart on 3/12/2017.
 */
public class Grid extends GridLayout {
    public Grid(Context context) {
        super(context);

    }

    public void reset() {
        removeAllViewsInLayout();
        setColumnCount(Round.size);

        for(Cell c : Round.cells) addView(c);
    }
}
