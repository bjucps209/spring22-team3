package model;

import java.util.*;
import java.util.stream.Stream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.*;
//This is the class that holds the data for a level to be saved or loaded.
public class LevelData {
    int numLevel;
    int numLevelOld;
    ArrayList<room> roomList = new ArrayList<room>();

    // constructor to create a level
    public LevelData(int numLevel) {
        this.numLevel = numLevel;
        this.numLevelOld = numLevel;

    }

    /**
     * It should read the level data one by one and create rooms for that level.
     * The Rooms should hold the location it is relative to the rooms around it.
     */
    public void load() {

        File dir = new File("../microwaveDungeon/src/Levels");
        
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                // only load the level we want
                String[] list = child.toString().split("\\\\");

                if (list[4].equals(numLevel + ".txt")) {

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
                                room newRoom = new room(x, y, true);
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
                                double damage = Double.parseDouble(lineList[5]);
                                double speed = Double.parseDouble(lineList[6]);
                                int scale = Integer.parseInt(lineList[7]);
                                int roomId = Integer.parseInt(lineList[8]);
                                // check which entity to create
                                switch (enemy) {
                                    case "enemy":
                                        enemy enemy1 = new enemy(health, speed, damage, id, x, y);
                                        ImageView enemyImage = new ImageView(new Image("images/microwave.png"));
                                        enemyImage.setFitHeight(100);
                                        enemyImage.setFitWidth(100);
                                        // add the entity to the room using the roomId
                                        enemy1.setSize(scale);
                                        roomList.get(roomId - 1).addEnemy(enemy1);
                                       
                                        roomList.get(roomId - 1).addImage(enemyImage);
                                        enemyImage.setUserData(enemy1);
                                        
                                        break;
                                    case "obstacle":
                                        obstacle o = new obstacle(health, speed, damage, id, x, y);
                                        ImageView obstacleImage = new ImageView(new Image("images/obstacle.png"));
                                        obstacleImage.setFitHeight(50);
                                        obstacleImage.setFitWidth(50);
                                        roomList.get(roomId - 1).addObstacle(o);
                                        roomList.get(roomId - 1).addImage(obstacleImage);
                                        obstacleImage.setUserData(o);
                                        
                                        break;
                                    // staircase
                                    case "staircase":
                                        staircase s = new staircase(health, speed, damage, id, x, y);
                                        ImageView stairsImage = new ImageView(new Image("images/staircase.png"));
                                        stairsImage.setFitHeight(50);
                                        stairsImage.setFitWidth(50);
                                       
                                        roomList.get(roomId - 1).setStaircase(s);
                                        roomList.get(roomId - 1).addImage(stairsImage);
                                        stairsImage.setUserData(s);

                                        break;
                                    case "startpt":
                                        startpt s1 = new startpt(health, speed, damage, id, x, y);
                                        ImageView startptImage = new ImageView(new Image("images/spawn.png"));
                                        //roomList.get(roomId - 1).setStartpt(s1);
                                        roomList.get(roomId - 1).setStart(s1);
                                        startptImage.setFitHeight(50);
                                        startptImage.setFitWidth(50);
                                        startptImage.setUserData(s1);
                                        roomList.get(roomId -1 ).addImage(startptImage);
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

                    } catch (IOException e) {
                        System.out.println("Problem reading file");

                    }
                }

            }
        } else {
            System.out.println("Not a directory");
        }

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
    public void save(String type) {

        try {

            
            FileWriter fw = new FileWriter("../microwaveDungeon/src/Levels/" + numLevel + ".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            int id = 1;
            for (room r : roomList) {

                bw.write("Room-" + id + "-" + r.getX() + "," + r.getY() + "\n");
                for (enemy e : r.getEnemyList()) {
                    bw.write(e.getClass().getSimpleName() + "," + e.getId() + "," + e.getXcoord() + "," + e.getYcoord()
                            + "," + e.getHealth() + "," + e.getDamage() + "," + e.getSpeed() + "," + 1 + "," + id
                            + "\n");
                }
                for (obstacle o : r.getObstacleList()) {
                    bw.write(o.getClass().getSimpleName() + "," + o.getId() + "," + o.getXcoord() + "," + o.getYcoord()
                            + "," + o.getHealth() + "," + o.getDamage() + "," + o.getSpeed() + "," + 1 + "," + id
                            + "\n");
                }
                if (r.getStart() != null) {
                    bw.write(r.getStart().getClass().getSimpleName() + "," + r.getStart().getId() + "," + r.getStart().getXcoord() + "," + r.getStart().getYcoord()
                            + "," + r.getStart().getHealth() + "," + r.getStart().getDamage() + "," + r.getStart().getSpeed() + "," + 1 + "," + id
                            + "\n");
                }
                if (r.getStaircase() != null) {
                    bw.write(r.getStaircase().getClass().getSimpleName() + "," + r.getStaircase().getId() + "," + r.getStaircase().getXcoord() + "," + r.getStaircase().getYcoord()
                            + "," + r.getStaircase().getHealth() + "," + r.getStaircase().getDamage() + "," + r.getStaircase().getSpeed() + "," + 1 + "," + id
                            + "\n");
                }
                

                bw.write("End\n");
                id++;
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Problem writing file");
        }

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
