package model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class room {
    
    //X and Y coordinate that marks the rooms location in relationship to the other rooms
    private int x;

    private int y;

    //Each direction holds the coordinate of the corresponding room (i.e. North[0] = x, North[1] = y)
    private position North;

    private position South;

    private position East;

    private position West; 
    // List of all entities in the room

    staircase stairs = null; // Added for saving method

    ArrayList<enemy> enemyList = new ArrayList<enemy>();

    ArrayList<obstacle> obstacleList = new ArrayList<obstacle>();

    ArrayList<projectile> bulletList = new ArrayList<projectile>();

    ArrayList<door> doorList = new ArrayList<door>();

    player player;

    public room(int setX, int setY, Boolean isNotForLoad){ // isNotForLoad determines whether if generate() is used as when loading, generate is not nessesary
        x = setX;

        y = setY;
    }
    

    // Given an PrintWriter, this method saves the room's attributes 
    public void save(PrintWriter output) throws IOException {
        output.println(x);
        output.println(y);
        if(stairs != null) {
            output.println(true);
            stairs.save(output);
        }
        else
            output.println(false);
        output.println(obstacleList.size());
        for(obstacle o: obstacleList) {
            o.save(output);
        }
        output.println(enemyList.size());
        for(enemy e: enemyList) {
            e.save(output);
        }
    }

    //Factory Method that builds/loads a room based off a DataInputStream
    public static room load(BufferedReader input) throws IOException {
        String x = input.readLine();
        String y = input.readLine();
        room output = new room(Integer.parseInt(x), Integer.parseInt(y), false);
        if(Boolean.parseBoolean(input.readLine())) 
            output.setStaircase(new staircase(0, 0, 0, 0, Integer.parseInt(input.readLine()), Integer.parseInt(input.readLine()))); // TODO: Parameters for health and such good?
        int obstacleCount = Integer.parseInt(input.readLine());
        for(int i = 0; i < obstacleCount; ++i) {
            output.addObstacle(new obstacle(-1, 0, 0, -1, Integer.parseInt(input.readLine()), Integer.parseInt(input.readLine()))); // TODO: Parameters for health and such good?
        }
        int enemyCount = Integer.parseInt(input.readLine());
        for(int i = 0; i < enemyCount; ++i) {
            enemy e = new enemy(Integer.parseInt(input.readLine()), Double.parseDouble(input.readLine()), Double.parseDouble(input.readLine()), Integer.parseInt(input.readLine()), Integer.parseInt(input.readLine()), Integer.parseInt(input.readLine()));
            e.setSize(Integer.parseInt(input.readLine()));
            output.addEnemy(e);
        }
        return output;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<enemy> getEnemyList() {
        return enemyList;
    }

    public void setEntityList(ArrayList<enemy> enemyList) {
        this.enemyList = enemyList;
    }

    

    public player getPlayer() {
        return player;
    }


    public void setPlayer(player player) {
        this.player = player;
    }


    public position getNorth() {
        return North;
    }


    public void setNorth(position north) {
        North = north;
    }


    public position getSouth() {
        return South;
    }


    public void setSouth(position south) {
        South = south;
    }


    public position getEast() {
        return East;
    }


    public void setEast(position east) {
        East = east;
    }


    public position getWest() {
        return West;
    }


    public void setWest(position west) {
        West = west;
    }

    // added for load method
    public void setStaircase(staircase s) {
        stairs = s;
    }

    public void addEnemy(enemy e) {
        enemyList.add(e);
    }
    public void removeEnemy(enemy e) {
        enemyList.remove(e);
    }

    public void addObstacle(obstacle e) {
        obstacleList.add(e);
    }
    public void removeObstacle(obstacle e) {
        obstacleList.remove(e);
    }

    
    public void addDoor(door d) {
        doorList.add(d);
    }

    public void removeDoor(door d) {
        doorList.remove(d);
    }

    

    
    public ArrayList<door> getDoorList() {
        return doorList;
    }


    public void setDoorList(ArrayList<door> doorList) {
        this.doorList = doorList;
    }


    public ArrayList<projectile> getBulletList() {
        return bulletList;
    }


    public void setBulletList(ArrayList<projectile> bulletList) {
        this.bulletList = bulletList;
    }


    @Override
    public String toString() {
       String toStringEntityList = "";
        for( enemy e : enemyList){
            toStringEntityList += e.toString() + ", ";
            
        }
        //remove the last comma and space
        //check to see if the list is empty
        if(toStringEntityList.length() > 0){
            toStringEntityList = toStringEntityList.substring(0, toStringEntityList.length() - 2);
        }
        toStringEntityList = toStringEntityList.substring(0, toStringEntityList.length()-2);
        return "room [x=" + x + ", y=" + y + ", enemyList=" + toStringEntityList + "]";
    }
    
}
