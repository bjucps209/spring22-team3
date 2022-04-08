package model;

import java.util.*;
import java.io.File;
import java.io.*;

public class LevelData {
    int numLevel;
    ArrayList<room> roomList = new ArrayList<room>();

    // constructor to create a level
    public LevelData(int numLevel) {
        this.numLevel = numLevel;

    }

    /**
     * It should read the level data one by one and create rooms for that level.
     * The Rooms should hold the location it is relative to the rooms around it.
     */
    public void load() {

        File dir = new File("microwaveDungeon/src/Levels");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                // only load the level we want
                String[] list = child.toString().split("\\\\");
                
                if (list[3].equals(numLevel + ".txt")) {

                    try {
                        // LevelData level = new LevelData(1);
                        var rd = new BufferedReader(
                                new FileReader(child));

                        String line = rd.readLine();
                        while (line != null) {
                            if (line.startsWith("Room")) {

                                // read the room
                                String[] roomData = line.split("-");
                                int roomId = Integer.parseInt(roomData[1]);
                                String coords = roomData[2];
                                int x = Integer.parseInt(coords.split(",")[0]);
                                int y = Integer.parseInt(coords.split(",")[1]);
                                // create a new roomCoord to hold the room's location

                                // set room coordinates to the room
                                room newRoom = new room();
                                roomList.add(newRoom);

                                line = rd.readLine();
                            }
                            while (!line.equals("End")) {
                                String[] lineList = line.split(",");
                                String enemy = lineList[0];
                                int id = Integer.parseInt(lineList[1]);
                                int x = Integer.parseInt(lineList[2]);
                                int y = Integer.parseInt(lineList[3]);
                                int health = Integer.parseInt(lineList[4]);
                                int damage = Integer.parseInt(lineList[5]);
                                int speed = Integer.parseInt(lineList[6]);
                                int scale = Integer.parseInt(lineList[7]);
                                entity enemy1 = new entity(health, speed, damage, id);
                                // room.addEnemy(enemy1);

                               

                               
                                line = rd.readLine();
                            }
                            if (line.equals("End")) {
                                line = rd.readLine();
                            }

                        }
                        rd.close();

                    } catch (IOException e) {
                        System.out.println("Problem reading file");

                    }
                }

            }
        } else {
            System.out.println("Not a directory");
        }

    }

    // This method will be in the Level Builder to save all the rooms and entities
    // in those rooms
    // It will loop through all the rooms and save them in order the rooms were
    // created.
    // It will save the location of all the rooms.
    public void save() {

    }

    // This will add a room to the level.
    public void addRoom(room r) {
        this.roomList.add(r);
    }
    /**
     * This method will return the level number
     * @return numLevel
     *
     */
    public int getNumLevel() {
        return this.numLevel;
    }
    /**
     * This method will return the room list
     * @return roomList
     */
    public ArrayList<room> getRoomList() {
        return this.roomList;

    }

}
