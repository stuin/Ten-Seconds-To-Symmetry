package test;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stuin.tenseconds.Game.Player;
import com.stuin.tenseconds.Game.Timer;
import com.stuin.tenseconds.R;
import org.mockito.Mockito;

import static org.mockito.Matchers.*;

class Mockups {

    static SharedPreferences mockPreferences() {
        //Create mocking objects
        SharedPreferences sharedPreferences = Mockito.mock(SharedPreferences.class);
        SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);

        //Set retrieving returns
        Mockito.when(sharedPreferences.getBoolean(anyString(), eq(true))).thenReturn(true);
        Mockito.when(sharedPreferences.getBoolean(anyString(), eq(false))).thenReturn(false);
        Mockito.when(sharedPreferences.getInt(anyString(), anyInt())).thenReturn(-1);

        //Set editing returns
        Mockito.when(sharedPreferences.edit()).thenReturn(editor);
        Mockito.when(editor.putBoolean(anyString(), anyBoolean())).thenReturn(editor);
        Mockito.when(editor.putInt(anyString(), anyInt())).thenReturn(editor);
        Mockito.when(editor.putString(anyString(), anyString())).thenReturn(editor);

        return sharedPreferences;
    }

    static Player mockPlayer(SharedPreferences preferences) {
        //Mock with plain textview
        return mockPlayer(preferences, Mockito.mock(TextView.class));
    }

    static Player mockPlayer(SharedPreferences preferences, TextView text) {
        //Objects being mocked
        Player player = Mockito.mock(Player.class);
        Resources resources = Mockito.mock(Resources.class);
        RelativeLayout layout = Mockito.mock(RelativeLayout.class);
        Timer timer = Mockito.mock(Timer.class);

        //Set resource strings
        String[] labels = new String[]{"L", "W", "", "", "N", "R"};
        Mockito.when(resources.getStringArray(R.array.app_labels)).thenReturn(labels);
        Mockito.when(resources.getString(R.string.drawer_stats)).thenReturn("L %1$d P %2$d T %3$d");
        Mockito.when(player.getResources()).thenReturn(resources);

        //Set views and objects
        Mockito.when(player.getParent()).thenReturn(layout);
        Mockito.when(player.getChildAt(1)).thenReturn(timer);
        Mockito.when(layout.getChildAt(anyInt())).thenReturn(text);
        Mockito.when(layout.findViewById(anyInt())).thenReturn(text);

        player.sharedPreferences = preferences;
        return player;
    }
}
