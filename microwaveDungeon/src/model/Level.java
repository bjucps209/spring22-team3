//-----------------------------------------------------------
//File:   Level.java
//Desc:   This file contains the level information and is used
//        to create levels in the different difficulties.
//-----------------------------------------------------------

package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Level {

    private ArrayList<room> Rooms = new ArrayList<room>(); // list of rooms for a level

    private difficulties difficultyLevel; // difficult of the level

    // Constructor -Added for save/load tests. 
    public Level(difficulties difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public ArrayList<room> getRooms() {
        return Rooms;
    }

    public void setRooms(ArrayList<room> rooms) {
        Rooms = rooms;
    }

    // adds a room to the list of rooms
    public void addRoom(room r) { 
        Rooms.add(r);
    }

    public difficulties getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(difficulties difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    // Given an OutputStream, this method saves the Level's attributes 
    public void save(PrintWriter output) throws IOException {
        output.println(Rooms.size());
        for(room r: Rooms) {
            r.save(output);
        }
    }

    //Factory Method that builds/loads a level based off a DataInputStream
    public static Level load(BufferedReader input, difficulties diff) throws IOException {
        Level output = new Level(diff);
        int levelCount = Integer.parseInt(input.readLine());
        for(int i = 0; i < levelCount; ++i) {
            output.addRoom(room.load(input));
        }
        return output;
    }

}
