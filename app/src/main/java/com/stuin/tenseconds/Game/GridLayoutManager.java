package com.stuin.tenseconds.Game;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.stuin.tenseconds.Round;

public class GridLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //Clear grid
        removeAndRecycleAllViews(recycler);

        int i = 0;
        for(int y = 0; y < Round.size; y++) {
            for(int x = 0; x < Round.size; x++) {
                //Set view to new position
                View view = recycler.getViewForPosition(i);
                layoutDecorated(view,
                        Round.scale * x, Round.scale * y,
                        Round.scale * (x + 1), Round.scale * (y + 1));
                addView(view);
                i++;
            }
        }
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);

        //Set measure based on existing parameters
        int length = Round.scale * Round.size;
        setMeasuredDimension(length, length);
    }
}
