package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Level {

    private ArrayList<room> Rooms = new ArrayList<room>();

    private int timePassed = 0;

    private int score = 0;

    private difficulties difficultyLevel;

    // Constructor -Added for save/load tests. Feel free to make changes as you see fit
    public Level(difficulties difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    // Will generate room objects depending on the level of difficulty
    // chosen by the player
    public void makeRooms() {
        throw new RuntimeException("Method not implemented");
    }

    public ArrayList<room> getRooms() {
        return Rooms;
    }

    public void setRooms(ArrayList<room> rooms) {
        Rooms = rooms;
    }

    public int getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(int timePassed) {
        this.timePassed = timePassed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public difficulties getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(difficulties difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    // Given an OutputStream, this method saves the Level's attributes 
    public void save(DataOutputStream output) throws IOException {
        output.writeInt(Rooms.size());
        for(room r: Rooms) {
            r.save(output);
        }
    }

}
