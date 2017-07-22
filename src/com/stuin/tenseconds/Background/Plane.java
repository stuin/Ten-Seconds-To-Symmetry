package com.stuin.tenseconds.Background;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.stuin.cleanvisuals.Range;

import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Stuart on 7/13/2017.
 */
public class Plane extends RelativeLayout {
    public List<Object> objects;
    public Stack<Object> waiting;
    public Random rand;
    public boolean on = true;
    public int updateTime = 1;
    public int time = 0;

    public boolean top;
    public int length;
    public int width;
    public int start;
    public Range speed;
    public int addTime;
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
                if(time % addTime == 0) Add();

                postDelayed(update, updateTime);
            }
        }
    };

    public void Add() {
        if(waiting.isEmpty()) {
            Object object = new Object(this);
            addView(object);
            objects.add(object);
        } else {
            waiting.pop().Start();
        }
    }
}
