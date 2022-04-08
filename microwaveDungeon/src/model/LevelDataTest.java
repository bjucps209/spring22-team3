package model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class LevelDataTest {
    @Test
    public void test_addRoom() {
        LevelData level = new LevelData(1);
        assertEquals(1, level.getNumLevel());
        assertEquals(0, level.getRoomList().size());
        level.addRoom(new room(0,0));
        assertEquals(1, level.getRoomList().size());
    }
    // @Test
    // public void test_Load() {
    // LevelData level = new LevelData(1);

    // level.load();
    // assertEquals(2, level.getRoomList().size());

    // }

}
