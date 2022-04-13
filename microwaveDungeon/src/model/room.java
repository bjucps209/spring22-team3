package model;

import java.util.ArrayList;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
    ArrayList<entity> entityList = new ArrayList<entity>();

    public room(int x, int y, Boolean isNotForLoad){ // isNotForLoad determines whether if generate() is used as when loading, generate is not nessesary
        this.x = x;

        this.y = y;

        if(isNotForLoad)
            generate();
    }
    

    // Given an OutputStream, this method saves the room's attributes 
    public void save(DataOutputStream output) throws IOException {
        output.writeInt(x);
        output.writeInt(y);
        ArrayList<enemy> enemies = new ArrayList<enemy>();
        ArrayList<obstacle> obstacles = new ArrayList<obstacle>();
        staircase s = null;
        for(entity e: entityList) {
            if(e instanceof enemy)
                enemies.add((enemy) e);
            else if(e instanceof obstacle)
                obstacles.add((obstacle) e);
            else if(e instanceof staircase)
                s = (staircase) e;
        }
        if(s != null) {
            output.writeBoolean(true);
            s.save(output);
        }
        else
            output.writeBoolean(false);
        output.writeInt(obstacles.size());
        for(obstacle o: obstacles) {
            o.save(output);
        }
        output.writeInt(enemies.size());
        for(enemy e: enemies) {
            e.save(output);
        }
    }

    // Factory Method that builds/loads a room based off a DataInputStream
    public static room load(DataInputStream input) throws IOException {
        room output = new room(input.readInt(), input.readInt(), false);
        if(input.readBoolean()) 
            output.addEntity(new staircase(-1, 0, 0, -1, input.readInt(), input.readInt())); // TODO: Parameters for health and such good?
        for(int i = 0; i < input.readInt(); ++i) {
            output.addEntity(new obstacle(-1, 0, 0, -1, input.readInt(), input.readInt())); // TODO: Parameters for health and such good?
        }
        for(int i = 0; i < input.readInt(); ++i) {
            enemy e = new enemy(input.readInt(), input.readDouble(), input.readDouble(), input.readInt(), input.readInt(), input.readInt());
            e.setSize(input.readInt());
            output.addEntity(e);
        }
        return output;
    }

    //generates entity objects depending on the difficulty selected by the player
    private void generate(){
        for (int i = 0; i < entityList.size() - 1; ++i){
            entityList.get(i);
        }

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


    public void addEntity(entity e) {
        entityList.add(e);
    }
    public void removeEntity(entity e) {
        entityList.remove(e);
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
