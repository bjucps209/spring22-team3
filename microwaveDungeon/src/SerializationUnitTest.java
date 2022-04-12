import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.util.ArrayList;
import org.junit.Test;
import model.*;

// Unit test for save and load methods
public class SerializationUnitTest {
    
    // Loads the sample save file and checks if the load worked properly
    @Test
    public void loadTest() {
        Game testGame = new Game(difficulties.MEDIUM, characters.HPOCKET);
        testGame.load("src\\Saves\\SampleSave.txt");
        assertEquals(100, testGame.getScore());
        assertEquals(55, testGame.getTimePassed());
        // TODO: Add assertions for the objects in rooms using testGame.getLevelSet()
    }
}
