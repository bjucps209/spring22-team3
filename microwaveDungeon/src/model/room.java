package model;

import java.util.ArrayList;
import java.io.DataOutputStream;

public class room {
    
    //X and Y coordinate that marks the rooms location in relationship to the other rooms
    private int x;

    private int y;

    //Each direction holds the coordinate of the corresponding room (i.e. North[0] = x, North[1] = y)
    private int[] North = new int[2];

    private int[] South = new int[2];

    private int[] East = new int[2];

    private int[] West = new int[2];
    // List of all entities in the room
    ArrayList<entity> entityList = new ArrayList<entity>();

    public room(int x, int y){
        this.x = x;

        this.y = y;

        generate();
    }
    

    // TODO: Given an OutputStream, this method saves the room's attributes 
    public void save(DataOutputStream output) {
    }

    //generates entity objects depending on the difficulty selected by the player
    private void generate(){

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

    public ArrayList<entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<entity> entityList) {
        this.entityList = entityList;
    }

    public int[] getNorth() {
        return North;
    }

    public void setNorth(int[] north) {
        North = north;
    }

    public int[] getSouth() {
        return South;
    }

    public void setSouth(int[] south) {
        South = south;
    }

    public int[] getEast() {
        return East;
    }

    public void setEast(int[] east) {
        East = east;
    }

    public int[] getWest() {
        return West;
    }

    public void setWest(int[] west) {
        West = west;
    }


    public void addEntity(entity e) {
        entityList.add(e);
    }
    @Override
    public String toString() {
       String toStringEntityList = "";
        for( entity e : entityList){
            toStringEntityList += e.toString() + ", ";
            
        }
        //remove the last comma and space
        //check to see if the list is empty
        if(toStringEntityList.length() > 0){
            toStringEntityList = toStringEntityList.substring(0, toStringEntityList.length() - 2);
        }
        //toStringEntityList = toStringEntityList.substring(0, toStringEntityList.length()-2);
        return "room [x=" + x + ", y=" + y + ", entityList=" + toStringEntityList + "]";
    }
    
}
