package com.stuin.tenseconds.Drifters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.stuin.cleanvisuals.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Stuart on 7/13/2017.
 */
public class Plane extends RelativeLayout {
    private List<Object> objects = new ArrayList<>();
    private boolean unset = true;
    private int updateTime = 1;
    private int time = 0;

    Stack<Object> waiting = new Stack<>();
    Random rand;

    public boolean on = true;
    public boolean vertical;
    public int length;
    public int width;
    public int start;
    public Range speed;
    public int addTime;
    public Drawable drawable;
    public int objectLength;

    //Various constructors
    public Plane(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public Plane(Context context) {
        super(context);
    }

    //Actual Setup
    public void Setup() {
        rand = new Random();
        unset = false;

        //Set dimensions
        if(vertical) {
            length = getHeight();
            width = getWidth();
        } else {
            length = getWidth();
            width = getHeight();
        }

        if(on) postDelayed(updateRunnable, updateTime);
    }

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            Update();
        }
    };

    public void Update() {
        if(unset) Setup();
        if(on) {
            //Update all objects
            for(Object object : objects) object.Update();
            if(time % addTime == 0) Add();
            time++;
        } else if(waiting.size() < objects.size()) {
            //Hide all objects
            for(Object object : objects) object.Hide();
        }
        postDelayed(updateRunnable, updateTime);
    }

    public Object Add() {
        Object object;
        if(waiting.isEmpty()) {
            object = new Object(this);
            addView(object);
            objects.add(object);
        } else {
            object = waiting.pop();
        }
        object.Start();
        return object;
    }
}
