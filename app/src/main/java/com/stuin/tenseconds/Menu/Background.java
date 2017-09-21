package com.stuin.tenseconds.Menu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.stuin.cleanvisuals.Range;
import com.stuin.cleanvisuals.Settings;
import com.stuin.cleanvisuals.Drift.Object;
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
        addTime = 20;
        drawable = new ColorDrawable(R.color.app_layout);

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

                //Wait to finish setup
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setup();
                    }
                }, 200);
            }
        });
    }

    @Override
    public void setup() {
        //Set dimensions and variables
        super.setup();
        if(bot) start = length;
    }

    @Override
    public void update() {
        super.update();
        //Check on switch
        on = Settings.get("Background");
    }

    @Override
    public Object add() {
        //Create new square
        Object object = super.add();
        object.setMinimumHeight(objectLength);
        object.setMinimumWidth(objectLength);
        return object;
    }
}
