package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Level {

    private ArrayList<room> Rooms = new ArrayList<room>();

    private difficulties difficultyLevel;

    // Constructor -Added for save/load tests. Feel free to make changes as you see fit
    public Level(difficulties difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public ArrayList<room> getRooms() {
        return Rooms;
    }

    public void setRooms(ArrayList<room> rooms) {
        Rooms = rooms;
    }

    public void addRoom(room r) { // Added this as it might be useful when constructing the games
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
