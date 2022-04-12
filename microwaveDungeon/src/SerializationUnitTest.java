import static org.junit.Assert.assertEquals;
import java.io.*;
import java.util.ArrayList;
import org.junit.Test;
import model.*;

// Unit test for save and load methods
public class SerializationUnitTest {
    
    @Test
    void SaveAndLoadTest() throws FileNotFoundException, IOException {
        File file = new File("microwaveDungeonTestSave.txt");
        if(file.exists())
            file.delete();
        file.createNewFile();
        player p = new player( 5, 5, 5, 0, 0, 0);
        ArrayList<room> testRooms = new ArrayList<room>();
        testRooms.add(0, new room(0, 0));
        testRooms.add(0, new room(1, 0));
        testRooms.add(0, new room(0, 1));
        testRooms.add(0, new room(1, 1));
        Level l = new Level(testRooms, difficulties.MEDIUM);
        try(DataOutputStream writer = new DataOutputStream(new FileOutputStream(file))) {
            l.save(writer);}
        assertEquals(l, TitleWindow.loadSave("microwaveDungeonTestSave.txt"));
    }

    // Tests the load function with SampleSave.txt
    @Test
    void LoadTest() {
        TitleWindow.loadSave("Saves/SampleSave.txt");
        
    }
}
