//-----------------------------------------------------------
//File:   room.java
//Desc:   This file holds the room class.
//----------------------------------------------------------- 
package model;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
//This is the class that holds the data for the room.
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
    startpt start = null;

    public startpt getStart() {
        return start;
    }


    public void setStart(startpt start) {
        this.start = start;
    }


    ArrayList<obstacle> obstacleList = new ArrayList<obstacle>(); //the list of obstacles in the room
    ArrayList<ImageView> imageList = new ArrayList<ImageView>(); //the list of images in the room
    player player; //the player in the room

    public room(int setX, int setY, Boolean isNotForLoad){ // isNotForLoad determines whether if generate() is used as when loading, generate is not nessesary
        x = setX;

        y = setY;
    }
    
    
    // Given an OutputStream, this method saves the room's attributes 
    public void save(DataOutputStream output) throws IOException {
        output.writeInt(x);
        output.writeInt(y);
        if(stairs != null) {
            output.writeBoolean(true);
            stairs.save(output);
        }
        else
            output.writeBoolean(false);
        output.writeInt(obstacleList.size());
        for(obstacle o: obstacleList) {
            o.save(output);
        }
        output.writeInt(enemyList.size());
        for(enemy e: enemyList) {
            e.save(output);
        }
    }

    //Factory Method that builds/loads a room based off a DataInputStream
    public static room load(DataInputStream input) throws IOException {
        room output = new room(input.readInt(), input.readInt(), false);
        if(input.readBoolean()) 
            output.setStaircase(new staircase(-1, 0, 0, -1, input.readInt(), input.readInt())); // TODO: Parameters for health and such good?
        for(int i = 0; i < input.readInt(); ++i) {
            output.addObstacle(new obstacle(-1, 0, 0, -1, input.readInt(), input.readInt())); // TODO: Parameters for health and such good?
        }
        for(int i = 0; i < input.readInt(); ++i) {
            enemy e = new enemy(input.readInt(), input.readDouble(), input.readDouble(), input.readInt(), input.readInt(), input.readInt());
            e.setSize(input.readInt());
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
    public ArrayList<obstacle> getObstacleList() {
        return obstacleList;
    }

    public void setEntityList(ArrayList<enemy> enemyList) {
        this.enemyList = enemyList;
    }
    public void addImage(ImageView img ) {
        imageList.add(img);
        
    }
    public ArrayList<ImageView> getImageList() {
        return imageList;
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


    public void removeImage(ImageView entitySelected) {
        imageList.remove(entitySelected);
    }


    public staircase getStaircase() {
        return stairs;
    }


  


   

    
    
}
