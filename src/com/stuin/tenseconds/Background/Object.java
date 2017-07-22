package com.stuin.tenseconds.Background;

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

        Start();
    }

    public void Start() {
        //Set new variables
        offset = plane.rand.nextInt(plane.width);
        speed = plane.speed.GetInt(plane.rand);
        distance = plane.start;

        //Set new position
        if(plane.top) setTranslationX(offset);
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
            if(distance > plane.length) {
                shown = false;
                setVisibility(View.GONE);
                plane.waiting.push(this);
            }
        }
    }

    private void Shift() {
        //Position object
        if(plane.top) setTranslationY(distance);
        else setTranslationX(distance);
    }
}
