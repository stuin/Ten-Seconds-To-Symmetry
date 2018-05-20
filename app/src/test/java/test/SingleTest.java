package test;

import android.content.SharedPreferences;
import android.widget.TextView;
import com.stuin.tenseconds.Round;
import com.stuin.tenseconds.Scoring.Single;

import junit.framework.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class SingleTest {

    @Test
    public void testWin() {
        //Setup testing object
        TextView text = Mockito.mock(TextView.class);
        Single single = new Single(Mockups.mockPlayer(Mockups.mockPreferences(), text));
        InOrder inOrder = Mockito.inOrder(text);

        //Set starting point
        Round.reset();
        Round.count = 3;

        //Check first win
        single.win(1000, false);
        inOrder.verify(text).setText("+10+");
        inOrder.verify(text).setText("N");
        inOrder.verify(text).setText("L 3 P 10 T -1");

        //Check second win
        single.win(500, false);
        inOrder.verify(text).setText("+15+");
        inOrder.verify(text).setText("N");
        inOrder.verify(text).setText("L 3 P 15 T -1");
    }

    @Test
    public void testDone() {
        //Setup testing object
        TextView text = Mockito.mock(TextView.class);
        SharedPreferences preferences = Mockups.mockPreferences();
        Single single = new Single(Mockups.mockPlayer(preferences, text));
        InOrder inOrder = Mockito.inOrder(text);

        //Set starting point
        Round.reset();

        //Check ending loss
        single.done(false);
        inOrder.verify(text).setText("L0");
        inOrder.verify(text).setText("R");
        Mockito.verify(preferences.edit()).putInt("HighScore", 0);

        //Check ending win
        single.done(true);
        inOrder.verify(text).setText("W0");
        inOrder.verify(text).setText("R");
    }

    @Test
    public void testLoad() {
        //Setup test objects
        SharedPreferences preferences = Mockups.mockPreferences();
        Single single = new Single(Mockups.mockPlayer(preferences));

        //Load blank save file
        Mockito.when(preferences.getString("save", " ")).thenReturn(" ");
        single.load();
        Assert.assertEquals("0:5:3", describeLevel());

        //Load first save file
        Mockito.when(preferences.getString("save", " ")).thenReturn("4:0:0");
        single.load();
        Assert.assertEquals("4:9:3", describeLevel());
        Mockito.verify(preferences.edit()).putString("save", " ");

        //Load second save file
        Mockito.when(preferences.getString("save", " ")).thenReturn("14:0:0");
        single.load();
        Assert.assertEquals("14:7:5", describeLevel());
    }

    @Test
    public void testSave() {
        //Setup testing objects
        SharedPreferences preferences = Mockups.mockPreferences();
        Single single = new Single(Mockups.mockPlayer(preferences));

        //Set base values
        Round.count = 3;
        Round.games = 5;
        Round.loss = false;

        //Try blank save file
        single.save();
        Mockito.verify(preferences.edit()).putInt("Games", 5);

        //Check load and save matching
        Mockito.when(preferences.getString("save", " ")).thenReturn("4:10:5");
        single.load();
        Mockito.verify(preferences.edit()).putString("save", " ");
        single.save();
        Mockito.verify(preferences.edit()).putString("save", "4:10:5");

        //Check save after win
        single.win(500, false);
        single.save();
        Mockito.verify(preferences.edit()).putString("save", "4:19:505");
    }

    private String describeLevel() {
        return Round.count + ":" + Round.size + ":" + Round.colors;
    }
}
