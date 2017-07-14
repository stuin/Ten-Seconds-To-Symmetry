package com.stuin.tenseconds.Background;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Random;

/**
 * Created by Stuart on 7/13/2017.
 */
public class Plane extends RelativeLayout {
    public List<Object> objects;
    public Random rand;
    public boolean on = true;
    public int updateTime = 1;

    public boolean top;
    public int length;
    public int width;
    public int start;
    public int resetTime;
    public Range speed;
    public int startWith;
    public Drawable drawable;

    //Various constructors
    public Plane(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        post(setup);
    }

    public Plane(Context context) {
        super(context);
        post(setup);
    }

    //Actual Setup
    public Runnable setup = new Runnable() {
        @Override
        public void run() {
            rand = new Random();

            //Set dimensions
            if(top) {
                length = getHeight();
                width = getWidth();
            } else {
                length = getWidth();
                width = getHeight();
            }

            if(on) postDelayed(update, updateTime);
        }
    };

    public Runnable update = new Runnable() {
        @Override
        public void run() {
            if(on) {
                //Update all objects
                for(Object object : objects) object.Update();
                postDelayed(update, updateTime);

                //Create more objects
                while(startWith > 0) {
                    Add();
                    startWith--;
                }
            }
        }
    };

    public void Add() {
        Object object = new Object(this);
        addView(object);
        objects.add(object);
    }
}
