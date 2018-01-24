package com.stuin.tenseconds.Game;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.stuin.tenseconds.Round;

public class HexLayoutManager extends RecyclerView.LayoutManager {

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

        int offset = 0;
        int i = 0;
        int expansion = (Round.scale - (int)(Math.sqrt(3) / 2 * Round.scale)) * 2;
        for(int y = 0; y < Round.size; y++) {
            for(int x = 0; x < Round.size; x++) {
                //Calculate and place view
                View view = recycler.getViewForPosition(i);
                layoutDecorated(view,
                        Round.scale * x + offset, Round.scale * y,
                        Round.scale * (x + 1) + offset, Round.scale * (y + 1) + expansion);
                addView(view);
                i++;
            }

            //Set offset to side
            if(offset == 0)
                offset = Round.scale / 2;
            else
                offset = 0;
        }
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);

        //Set measure based on existing parameters
        int expansion = (Round.scale - (int)(Math.sqrt(3) / 2 * Round.scale)) * 2;
        int length = Round.scale * Round.size;
        setMeasuredDimension(length + Round.scale / 2, length + expansion);
    }
}
