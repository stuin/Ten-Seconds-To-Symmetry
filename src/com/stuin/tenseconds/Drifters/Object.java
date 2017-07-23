package com.stuin.tenseconds.Drifters;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by Stuart on 7/13/2017.
 */
public class Object extends ImageView {
    public Plane plane;

    public int offset;
    public int distance;
    public int speed;
    public boolean shown;

    public Object(Plane plane) {
        super(plane.getContext());
        this.plane = plane;
        setImageDrawable(plane.drawable);
    }

    public void Start() {
        //Set new variables
        offset = plane.rand.nextInt(plane.width);
        speed = plane.speed.getInt(plane.rand);
        distance = plane.start;

        //Set new position
        if(plane.vertical) setTranslationX(offset);
        else setTranslationY(offset);
        Shift();

        //Start movement
        shown = true;
        setVisibility(View.VISIBLE);
    }

    public void Update() {
        //Move once
        if(shown) {
            distance += speed;
            Shift();

            //When reached end
            if(Math.abs(distance - plane.start) > plane.length + plane.objectLength) {
                Hide();
            }
        }
    }

    public void Hide() {
        shown = false;
        setVisibility(View.GONE);
        plane.waiting.push(this);
    }

    private void Shift() {
        //Position object
        if(plane.vertical) setTranslationY(distance);
        else setTranslationX(distance);
    }
}
