import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.util.ArrayList;
import org.junit.Test;
import model.*;

// Unit test for save and load methods
public class SerializationUnitTest {
    
    // Loads the sample save file and checks if the load worked properly
    //@Test
    public static void loadTest() {
        Game testGame = new Game(difficulties.MEDIUM, characters.HPOCKET);
        //testGame.load("src\\Saves\\SampleSave.txt");
        assertEquals(100, testGame.getScore());
        assertEquals(55, testGame.getTimePassed());
        player loadedPlayer = testGame.getUser();
        assertEquals(120, loadedPlayer.getHealth());
        assertEquals(50, loadedPlayer.getXcoord());
        assertEquals(0, loadedPlayer.getFloor());
        // TODO: Add assertions for the objects in rooms using testGame.getLevelSet()
    }

    @Test
    public void saveAndLoadTest() {
        Game testGame = new Game(difficulties.MEDIUM, characters.HPOCKET);
        testGame.setUser(new player(50, 15, 20, 0, 45, 45));
        testGame.save();
        testGame = new Game(difficulties.MEDIUM, characters.HPOCKET);
        testGame.load();
        assertEquals(50, testGame.getUser().getHealth());
    }
}
