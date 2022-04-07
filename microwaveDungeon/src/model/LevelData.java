package model;
import java.util.*;
public class LevelData {
    int numLevel;
    ArrayList<room> roomList = new ArrayList<room>();
    //constructor to create a level
    public LevelData(int numLevel){
        this.numLevel = numLevel;

    }
    /**
     * It should read the level data one by one and create rooms for that level. 
     * The Rooms should hold the location it is relative to the rooms around it.
     */
    public void load(){

    }
    //This method will be in the Level Builder to save all the rooms and entities in those rooms
    //It will loop through all the rooms and save them in order the rooms were created. 
    //It will save the location of all the rooms.
    public void save(){

    }
    //This will add a room to the level.
    public void addRoom(room r){
        roomList.add(r);
    }
    
}
