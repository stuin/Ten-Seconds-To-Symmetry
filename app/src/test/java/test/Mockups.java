package test;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stuin.tenseconds.Game.Player;
import com.stuin.tenseconds.R;
import org.mockito.Mockito;

import static org.mockito.Matchers.*;

public class Mockups {

    static SharedPreferences mockPreferences() {
        SharedPreferences sharedPreferences = Mockito.mock(SharedPreferences.class);
        SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);

        Mockito.when(sharedPreferences.getBoolean(anyString(), eq(true))).thenReturn(true);
        Mockito.when(sharedPreferences.getBoolean(anyString(), eq(false))).thenReturn(false);
        Mockito.when(sharedPreferences.getInt(anyString(), anyInt())).thenReturn(-1);

        Mockito.when(sharedPreferences.edit()).thenReturn(editor);
        Mockito.when(editor.putBoolean(anyString(), anyBoolean())).thenReturn(editor);
        Mockito.when(editor.putInt(anyString(), anyInt())).thenReturn(editor);
        Mockito.when(editor.putString(anyString(), anyString())).thenReturn(editor);
        return sharedPreferences;
    }

    static Player mockPlayer(SharedPreferences preferences) {
        return mockPlayer(preferences, Mockito.mock(TextView.class));
    }

    static Player mockPlayer(SharedPreferences preferences, TextView text) {
        Player player = Mockito.mock(Player.class);
        Resources resources = Mockito.mock(Resources.class);
        RelativeLayout layout = Mockito.mock(RelativeLayout.class);
        String[] labels = new String[5];

        Mockito.when(resources.getStringArray(R.array.app_labels)).thenReturn(labels);
        Mockito.when(resources.getString(R.string.drawer_stats)).thenReturn("L %1$d P %2$d T %3$d");
        Mockito.when(player.getResources()).thenReturn(resources);

        Mockito.when(layout.getChildAt(anyInt())).thenReturn(text);
        Mockito.when(layout.findViewById(anyInt())).thenReturn(text);
        Mockito.when(player.getParent()).thenReturn(layout);

        player.sharedPreferences = preferences;
        return player;
    }
}
