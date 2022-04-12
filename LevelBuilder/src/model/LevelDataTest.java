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
        level.addRoom(new room(0, 0));
        assertEquals(1, level.getRoomList().size());
    }

    @Test
    public void test_Load() {
        LevelData level = new LevelData(1);

        level.load();
        assertEquals(2, level.getRoomList().size());
        assertEquals(
            "LevelData [numLevel=1, roomList=[room [x=0, y=0, entityList=entity [health=100, speed=10, id=1, damage=20, Xcoord=400, Ycoord=300]], room [x=1, y=0, entityList=entity [health=11, speed=32, id=1, damage=12, Xcoord=43, Ycoord=32], entity [health=33, speed=33, id=2, damage=33, Xcoord=24, Ycoord=54]]]]", level.toString());

    }

    @Test
    public void test_AddEntity() {
        LevelData level = new LevelData(1);
        level.addRoom(new room(0, 0));
        level.findRoom(0, 0).addEntity(new enemy(10, 10, 10, 10, 100, 100));
        assertEquals(1, level.findRoom(0, 0).getEntityList().size());

    }

    @Test
    public void test_Save() throws IOException {

        LevelData level = new LevelData(1);
        level.addRoom(new room(0, 0));
        level.findRoom(0, 0).addEntity(new enemy(10, 10, 10, 10, 100, 100));
        level.save();
        assertEquals(1, level.getRoomList().size());
    }

    @Test
    public void test_findRoom() {
        LevelData level = new LevelData(1);
        level.addRoom(new room(0, 0));
        level.findRoom(0, 0).addEntity(new enemy(10, 10, 10, 10, 100, 100));
        assertEquals(1, level.findRoom(0, 0).getEntityList().size());

    }

    @Test
    public void test_getRoomList() {
        LevelData level = new LevelData(1);
        // 5x5 grid of rooms
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                level.addRoom(new room(i, j));
            }
        }
        assertEquals(25, level.getRoomList().size());
    }

    @Test
    public void test_getNumLevel() {
        LevelData level = new LevelData(1);
        assertEquals(1, level.getNumLevel());
    }

    @Test
    public void test_toString() {
        LevelData level = new LevelData(1);
        level.addRoom(new room(1, 1));
        level.findRoom(1, 1).addEntity(new enemy(10, 10, 10, 10, 100, 100));
        level.findRoom(1, 1).addEntity(new staircase(10, 10, 10, 10, 100, 100));
        
        assertEquals(
                "LevelData [numLevel=1, roomList=[room [x=1, y=1, entityList=entity [health=10, speed=10, id=1, damage=10, Xcoord=100, Ycoord=100], entity [health=10, speed=10, id=2, damage=10, Xcoord=100, Ycoord=100]]]]",
                level.toString());
        

    }

}
