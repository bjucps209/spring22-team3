package model;

import java.util.ArrayList;

public class Level {

    private ArrayList<room> Rooms = new ArrayList<room>();

    private int score = 0;

    private difficulties difficultyLevel;

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

    

}
