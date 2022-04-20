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
        Game testGame = Game.load(true);
        assertEquals(difficulties.EASY, testGame.getDiff());
        assertEquals(characters.HPOCKET, testGame.getCharacter());
        assertEquals(100, testGame.getScore());
        assertEquals(55, testGame.getTimePassed());
        assertEquals(1, testGame.getLevelSet().size());
        Level testLevel = testGame.getLevelSet().get(0);
        assertEquals(2, testLevel.getRooms().size());
    }

    @Test
    public void saveAndLoadTest() throws FileNotFoundException, IOException {
        Game testGame = new Game(difficulties.MEDIUM, characters.HPOCKET);
        testGame.setUser(new player(50, 15, 20, 0, 45, 45));
        testGame.save();
        Game testGameTwo = Game.load(false);
        assertEquals(difficulties.MEDIUM, testGameTwo.getDiff());
        assertEquals(characters.HPOCKET, testGameTwo.getCharacter());
        assertEquals(3, testGameTwo.getLevelSet().size());
        assertEquals(4, testGameTwo.getLevelSet().get(0).getRooms().size());
        assertEquals(1, testGameTwo.getLevelSet().get(0).getRooms().get(1).getEnemyList().size());
        assertEquals(50, testGameTwo.getUser().getHealth());
    }
}
