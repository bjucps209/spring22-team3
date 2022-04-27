package model;

import java.util.*;
import java.io.File;
import java.io.*;

public class LevelData {
    int numLevel;
    ArrayList<room> roomList = new ArrayList<room>();
    Level customLevel = new Level(difficulties.CUSTOM);
    public int doorCount = 0;

    // constructor to create a level
    public LevelData(int numLevel) {
        this.numLevel = numLevel;

    }

    /**
     * It should read the level data one by one and create rooms for that level.
     * The Rooms should hold the location it is relative to the rooms around it.
     */
    public Level load() {

        File dir = new File("src/Levels");
        
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                // only load the level we want
                String[] list = child.toString().split("\\\\");

                if (list[2].equals(numLevel + ".txt")) {

                    try {
                        ;
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
                                room newRoom = new room(x, y, false);
                                roomList.add(newRoom);
                                
                               
                                    
                                 door newDoor = new door(1000000, 0, 0, 0, 770, 250);
                                 newDoor.setDir(directions.East);
                                 newRoom.addDoor(newDoor);
                                 
                         
                               
                                customLevel.addRoom(newRoom);

                                line = rd.readLine();
                            }
                            while (!line.equals("End")) {
                                String[] lineList = line.split(",");
                                String enemy = lineList[0];
                                int id = Integer.parseInt(lineList[1]);
                                int x = Integer.parseInt(lineList[2]);
                                int y = Integer.parseInt(lineList[3]);
                                double health = Double.parseDouble(lineList[4]);
                                double damage = Double.parseDouble(lineList[5]);
                                double speed = Double.parseDouble(lineList[6]);
                                int scale = Integer.parseInt(lineList[7]);
                                int roomId = Integer.parseInt(lineList[8]);
                                // check which entity to create
                                switch (enemy) {
                                    case "enemy":
                                        enemy enemy1 = new enemy(health, speed, damage, id, x, y);
                                
                                        enemy1.setSize(scale);
                                      
                                        customLevel.getRooms().get(roomId - 1).addEnemy(enemy1);
                                        break;
                                    case "obstacle":
                                        obstacle o = new obstacle(health, speed, damage, id, x, y);
                                        roomList.get(roomId - 1).addObstacle(o);

                                        break;
                                    // staircase
                                    case "staircase":
                                        staircase s = new staircase(health, speed, damage, id, x, y);
                                        roomList.get(roomId - 1).setStaircase(s);
                                        break;
                                    case "startpt":
                                        startpt s1 = new startpt(health, speed, damage, id, x, y);
                                        //roomList.get(roomId - 1).setStartpt(s1);
                                        break;
                                    default:
                                        break;
                                }

                                line = rd.readLine();
                            }
                            if (line.equals("End")) {
                                line = rd.readLine();

                            }

                        }
                        
                        rd.close();
                        //remove the last door from the last room
                        customLevel.getRooms().get(customLevel.getRooms().size() - 1).getDoorList().remove(0);
                        return customLevel;

                    } catch (IOException e) {
                        System.out.println("Problem reading file");

                    }
                }

            }
        } else {
            System.out.println("Not a directory");
        }
        return null;

    }

    public room findRoom(int x, int y) {
        // find the room in the roomList with the coordinates
        for (room r : roomList) {
            if (r.getX() == x && r.getY() == y) {
                // load the room
                return r;

            }
        }
        return null;

    }
    @Override
    public String toString() {
        return "LevelData [numLevel=" + numLevel + ", roomList=" + roomList + "]";
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
     * 
     * @return numLevel
     *
     */
    public int getNumLevel() {
        return this.numLevel;
    }

    /**
     * This method will return the room list
     * 
     * @return roomList
     */
    public ArrayList<room> getRoomList() {
        return this.roomList;

    }
  
}
