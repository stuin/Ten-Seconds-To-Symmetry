package com.stuin.tenseconds.Game;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.stuin.cleanvisuals.Settings;
import com.stuin.cleanvisuals.Slide.Endings;
import com.stuin.tenseconds.Round;
import com.stuin.cleanvisuals.Slide.Slider;

/**
 * Created by Stuart on 3/12/2017.
 */
public class Grid extends RecyclerView {
	private boolean top;
	private GridLayoutManager gridManager;
	private HexLayoutManager hexManager;
	private GridAdaptor gridAdaptor;
	private boolean hex = false;

	//Parts of grid
	Slider slider = new Slider(this);
	
    public Grid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
		//Get custom value
		top = attributeSet.getAttributeBooleanValue(
				"http://schemas.android.com/apk/res-auto","top",false);

		//Prepare layout managers
		hexManager = new HexLayoutManager();
		gridManager = new GridLayoutManager();
		setLayoutManager(gridManager);

		//Set grid adaptor
		gridAdaptor = new GridAdaptor(top);
		setAdapter(gridAdaptor);

		//add ending to slide
		slider.endings = new Endings() {
			@Override
			public void enter() {
				Round.moving = false;
			}

			@Override
			public void exit() {
                gridAdaptor.notifyDataSetChanged();
				Round.moving = false;
			}
		};
    }

    void enter() {
		//Make sure animation set up
    	if(slider.unSet) {
			int s = Round.length;
			if(top) s = -s;

			slider.setup(false, s, 700);
		}
		
		//Check hex mode and layout manager
		if(Settings.get("Hexmode") != hex) {
			ColorReferences.hex = hex = !hex;
			if(hex)
				setLayoutManager(hexManager);
			else
				setLayoutManager(gridManager);
		}

		//Set new grid
        gridAdaptor.notifyDataSetChanged();
		
		//start animation
		slider.enter();
    }

	//Adaptor to create squares
    private class GridAdaptor extends RecyclerView.Adapter {
        private boolean top;

        GridAdaptor(boolean top) {
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
}
