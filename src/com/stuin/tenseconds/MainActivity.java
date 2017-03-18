package com.stuin.tenseconds;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.stuin.tenseconds.Animations.SliderSync;
import com.stuin.tenseconds.Views.Player;
import com.stuin.tenseconds.Views.Timer;

/**
 * Created by Stuart on 2/14/2017.
 */
public class MainActivity extends Activity {
    private Player player;
    private RelativeLayout relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Round.reset();

        player = (Player) findViewById(R.id.PlayerLayout);
        player.scoreboard = new Scoreboard(player);
        ((Switch) findViewById(R.id.Colorblind)).setChecked(Round.colorblind);

        relativeLayout = (RelativeLayout) findViewById(R.id.Relative);
        relativeLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                setup((RelativeLayout) view);
            }
        });
        relativeLayout.post(new Runnable() {
            @Override
            public void run() {
                setup(relativeLayout);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Round.moving = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.clear();
    }

    private void setup(RelativeLayout relativeLayout) {
        Round.length = relativeLayout.getHeight() / 2;
        Round.text = Round.length / 40;

        TextView textView = (TextView) findViewById(R.id.TopText);
        textView.setTextSize(Round.text);
        textView.setTranslationY(Round.length / 2.5f);

        textView = (TextView) findViewById(R.id.BotText);
        textView.setTextSize(Round.text);
        textView.setTranslationY(Round.length / -2.5f);

        LinearLayout drawer = (LinearLayout) findViewById(R.id.DrawerLayout);
        FrameLayout icon = (FrameLayout) findViewById(R.id.DrawerButton);

        player.slideDrawer = new SliderSync(drawer, icon);
        player.slideDrawer.setup(true, Round.length, 200, 200);

        for(int i = 0; i < drawer.getChildCount(); i++) {
            if(drawer.getChildAt(i) instanceof TextView) {
                textView = (TextView) drawer.getChildAt(i);
                textView.setTextSize(Round.text);
            } else if(drawer.getChildAt(i) instanceof Switch) {
                Switch view = (Switch) drawer.getChildAt(i);
                view.setChecked(Round.colorblind);
            }
        }
    }


    public void startGame(View view) {
        if(!Round.moving) {
            Round.generate(this);
            player.start();
            findViewById(R.id.DrawerButton).setVisibility(View.GONE);
            player.slideDrawer.hide();
        }
    }

    public void drawer(View view) {
        switch(view.getId()) {
            case R.id.DrawerButton:
                player.slideDrawer.showPrimary();
                break;
            case R.id.DrawerLayout:case R.id.Relative:
                player.slideDrawer.showSecondary();
                break;
            case R.id.Colorblind:
                player.scoreboard.colorblind(view);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(!Round.playing) {
            if(!player.slideDrawer.showSecondary() && player.loss) player.menu();
        } else if(!Round.moving) player.clear();
    }
}
