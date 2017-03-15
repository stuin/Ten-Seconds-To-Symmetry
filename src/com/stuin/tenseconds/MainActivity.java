package com.stuin.tenseconds;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stuin.tenseconds.Views.Grid;
import com.stuin.tenseconds.Views.Player;
import com.stuin.tensecondstosymmetry.R;

/**
 * Created by Stuart on 2/14/2017.
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Round.reset();
    }

    @Override
    protected void onStart() {
        super.onStart();
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.Relative);
        Round.moving = false;

        Round.length = relativeLayout.getHeight() / 2;
        if(relativeLayout.getWidth() > Round.length) Round.length = relativeLayout.getWidth();

        TextView textView = (TextView) findViewById(R.id.TopText);
        textView.setTextSize(Round.length / 25);
        textView.setTranslationY(-Round.length / 2);

        textView = (TextView) findViewById(R.id.BotText);
        textView.setTextSize(Round.length / 30);
        textView.setTranslationY(Round.length / 2);
    }

    public void startGame(View view) {
        if(!Round.moving) {
            Round.generate(this);
            ((Player) findViewById(R.id.PlayerLayout)).start();
        }
    }
}