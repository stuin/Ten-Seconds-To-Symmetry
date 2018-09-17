package com.stuin.tenseconds.Menu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.stuin.cleanvisuals.Drift.Drifter;
import com.stuin.cleanvisuals.Drift.Engine;
import com.stuin.cleanvisuals.Range;
import com.stuin.cleanvisuals.Settings;
import com.stuin.cleanvisuals.Drift.Plane;
import com.stuin.tenseconds.R;

/**
 * Created by Stuart on 7/23/2017.
 */
public class Background extends Plane {
    public boolean bot;

    public Background(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        //Set animation variables
        side = true;
        objectLength = 120;
        start = -objectLength;
        speed = new Range(2, 8);
        addTime = 18;
        drawable = new ColorDrawable(getResources().getColor(R.color.app_layout));

        //Set for bot background
        bot = !attributeSet.getAttributeBooleanValue(
                "http://schemas.android.com/apk/res-auto","top",false);
        if(bot) speed = new Range(-speed.max, -speed.min);

        post(new Runnable() {
            @Override
            public void run() {
                //Set height of view
                RelativeLayout layout = (RelativeLayout) getParent();
                int height = (layout.getHeight() - layout.findViewById(R.id.Bar_Layout).getHeight()) / 2;
                if(!bot && layout.findViewById(R.id.Bar_Layout).getHeight() % 2 != 0) setMinimumHeight(height + 1);
                else setMinimumHeight(height);
            }
        });
    }

    @Override
    public void setup(Engine engine) {
        //Set dimensions and variables
        super.setup(engine);
        if(bot) start = length;
    }

    @Override
    public void update() {
        //Check on switch
        on = Settings.get("Background");
    }

    @Override
    public Drifter add() {
        //Create new square
        Drifter object = super.add();
        object.setMinimumHeight(objectLength);
        object.setMinimumWidth(objectLength);
        return object;
    }
}
