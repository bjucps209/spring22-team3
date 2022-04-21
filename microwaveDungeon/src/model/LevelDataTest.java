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
        level.addRoom(new room(0, 0, true));
        assertEquals(1, level.getRoomList().size());
    }

    @Test
    public void test_Load() {
        LevelData level = new LevelData(1);

        level.load();
        assertEquals(1, level.getRoomList().size());
        assertEquals(
            "LevelData [numLevel=1, roomList=[room [x=0, y=0, enemyList=entity [health=100, speed=10.0, id=1, damage=20.0, Xcoord=438, Ycoord=438], entity [health=100, speed=10.0, id=2, damage=20.0, Xcoord=446, Ycoord=211], entity [health=100, speed=10.0, id=3, damage=20.0, Xcoord=155, Ycoord=204], entity [health=100, speed=10.0, id=4, damage=20.0, Xcoord=284, Ycoord=16]]]", level.toString());

    }

     @Test
     public void test_AddEntity() {
        LevelData level = new LevelData(1);
         level.addRoom(new room(0, 0, true));
         level.findRoom(0, 0).addEnemy(new enemy(10, 10, 10, 10, 100, 100));
        assertEquals(1, level.findRoom(0, 0).getEnemyList().size());

     }

     @Test
     public void test_Save() throws IOException {

         LevelData level = new LevelData(1);
         level.addRoom(new room(0, 0, true));
         level.findRoom(0, 0).addEnemy(new enemy(10, 10, 10, 10, 100, 100));
         level.save();
         assertEquals(1, level.getRoomList().size());
     }

     @Test
     public void test_findRoom() {
        LevelData level = new LevelData(1);
        level.addRoom(new room(0, 0, true));
         level.findRoom(0, 0).addEnemy(new enemy(10, 10, 10, 10, 100, 100));
         assertEquals(1, level.findRoom(0, 0).getEnemyList().size());

     }

    @Test
    public void test_getRoomList() {
        LevelData level = new LevelData(1);
        // 5x5 grid of rooms
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                level.addRoom(new room(i, j, true));
            }
        }
        assertEquals(25, level.getRoomList().size());
    }

    @Test
    public void test_getNumLevel() {
        LevelData level = new LevelData(1);
        assertEquals(1, level.getNumLevel());
    }

    // @Test
    // public void test_toString() {
    //     LevelData level = new LevelData(1);
    //     level.addRoom(new room(1, 1, true));
    //     level.findRoom(1, 1).addEntity(new enemy(10, 10, 10, 10, 100, 100));
    //     level.findRoom(1, 1).addEntity(new staircase(10, 10, 10, 10, 100, 100));
        
    //     assertEquals(
    //             "LevelData [numLevel=1, roomList=[room [x=1, y=1, entityList=entity [health=10, speed=10, id=10, damage=10, Xcoord=100, Ycoord=100], entity [health=10, speed=10, id=10, damage=10, Xcoord=100, Ycoord=100]]]]",
    //             level.toString());
        

    // }

}
