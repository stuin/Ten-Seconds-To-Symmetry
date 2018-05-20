package test;

import com.stuin.cleanvisuals.Settings;
import org.junit.Before;
import org.junit.Test;
import junit.framework.Assert;

import com.stuin.tenseconds.Round;


public class RoundTest {

    @Before
    public void before() {
        Settings.load(Mockups.mockPreferences(), new String[]{"Expert"}, false);
    }

    @Test
    public void testSeparate() {
        //Test separating number strings
        Assert.assertEquals("12", Round.separate(12));
        Assert.assertEquals("1,234", Round.separate(1234));
        Assert.assertEquals("123,456", Round.separate(123456));
        Assert.assertEquals("12,345,678", Round.separate(12345678));
    }

    @Test
    public void testRegularLevels() {
        //Test normal level progression
        Settings.set("Expert", false);
        Round.reset();

        for(int i = 0; i < 18; i++) {
            Assert.assertEquals(levelList[i], describeLevel());
            Round.next();
        }
    }

    @Test
    public void testExpertLevels() {
        //Test expert level progression
        Settings.set("Expert", true);
        Round.reset();

        for(int i = 12; i < 30; i++) {
            Assert.assertEquals(levelList[i], describeLevel());
            Round.next();
        }
    }

    //Ordered list of levels
    private String[] levelList = {
            "5:3","5:3","6:3","7:3","8:3","9:3",
            "5:4","5:4","6:4","7:4","8:4","9:4",
            "5:5","5:5","6:5","7:5","8:5","9:5",
            "5:6","5:6","6:6","7:6","8:6","9:6",
            "5:7","5:7","6:7","7:7","8:7","9:7"
    };

    private String describeLevel() {
        return Round.size + ":" + Round.colors;
    }
}
