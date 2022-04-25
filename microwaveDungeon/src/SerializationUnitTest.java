import static org.junit.Assert.assertEquals;
import java.io.*;
import org.junit.Test;
import model.*;

// Unit test for save and load methods
public class SerializationUnitTest {
    
    // Loads the sample save file and checks if the load worked properly
    @Test
    public void loadTest() {
        Game testGame = Game.load(true);
        assertEquals(difficulties.NUKE, testGame.getDiff());
        assertEquals(characters.RAMEN, testGame.getCharacter());
        assertEquals(100, testGame.getScore());
        assertEquals(55, testGame.getTimePassed());
        assertEquals(3, testGame.getLevelSet().size());
        assertEquals(18, testGame.getUser().getHealth());
        assertEquals(320, testGame.getUser().getXcoord());
    }

    @Test
    public void saveAndLoadTest() throws FileNotFoundException, IOException {
        Game testGame = new Game(difficulties.MEDIUM, characters.HPOCKET);
        testGame.setUser(new player(50, 15, 20, 0, 45, 45, 0));
        testGame.save();
        Game testGameTwo = Game.load(false);
        assertEquals(difficulties.MEDIUM, testGameTwo.getDiff());
        assertEquals(characters.HPOCKET, testGameTwo.getCharacter());
        assertEquals(3, testGameTwo.getLevelSet().size());
        assertEquals(3, testGameTwo.getLevelSet().get(0).getRooms().size());
        assertEquals(0, testGameTwo.getLevelSet().get(0).getRooms().get(1).getEnemyList().size());
        assertEquals(50, testGameTwo.getUser().getHealth());
    }
}
