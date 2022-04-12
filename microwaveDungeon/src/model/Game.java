package model;

import java.util.ArrayList;

public class Game {
    private ArrayList<Level> easyLevelList = new ArrayList<Level>();

    private ArrayList<Level> mediumLevelList = new ArrayList<Level>();

    private ArrayList<Level> hardLevelList = new ArrayList<Level>();

    private ArrayList<Level> nukeLevelList = new ArrayList<Level>();

    private characters character;

    private difficulties diff;

    private player User;

    private enemy Enemies;

    public void initialize(difficulties setDiff, characters setCharacter){
        diff = setDiff;
        character = setCharacter;
    }

    public void generateGame(){
        switch(diff){

            case EASY:
            User = new player(25, 10, 2, 1, 0, 0);
            break;

            case MEDIUM:
            User = new player(25, 10, 2, 1, 0, 0);
            break;

            case HARD:
            User = new player(20, 10, 2, 1, 0, 0);
            break;

            
            case NUKE:
            User = new player(15, 10, 1.5, 1, 0, 0);
            break;


        }

    }

    public characters getCharacter() {
        return character;
    }

    public void setCharacter(characters character) {
        this.character = character;
    }

    public difficulties getDiff() {
        return diff;
    }

    public void setDiff(difficulties diff) {
        this.diff = diff;
    }

    

}
