package com.stuin.tenseconds.Game.Layout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.stuin.tenseconds.Round;

//Adaptor to create squares
public class GridAdaptor extends RecyclerView.Adapter {
    private boolean top;

    public GridAdaptor(boolean top) {
        this.top = top;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Set values of view
        Round.cells.get(position).makeView(holder.itemView, top);
    }

    @Override
    public int getItemCount() {
        return Round.cells.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Make new view
        View layout = new FrameLayout(parent.getContext());
        return new RecyclerView.ViewHolder(layout) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }
}
