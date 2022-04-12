package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Game {
    private ArrayList<Level> easyLevelList = new ArrayList<Level>();

    private ArrayList<Level> mediumLevelList = new ArrayList<Level>();

    private ArrayList<Level> hardLevelList = new ArrayList<Level>();

    private ArrayList<Level> nukeLevelList = new ArrayList<Level>();

    private ArrayList<Level> levelSet; // Added to simplify saving so that the Save method can easily iterate through each level

    private int timePassed = 0;

    private int score = 0;

    private characters character;

    private difficulties diff;

    private player User;

    public Game(difficulties setDiff, characters setCharacter){
        diff = setDiff;
        character = setCharacter;
        generateGame(); // Move this method into Constructor?
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
        Level firstLevel = new Level(diff); // Moved this out of Switch for simplification
        Level secondLevel = new Level(diff);
        Level ThirdLevel = new Level(diff);
        //firstLevel.getRooms().add();
        levelSet = new ArrayList<Level>(Arrays.asList(firstLevel, secondLevel, ThirdLevel));
    }

    public int getTimePassed() {
        return timePassed;
    }

    public int getScore() {
        return score;
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

    // Saves the Game
    public void save() {
        File file = new File("Saves/SavedGame.txt");
        if(file.exists())
            file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            Alert a = new Alert(AlertType.ERROR, "There was a problem with creating a file.");
            a.show();
        }
        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(file))) {
            writer.writeInt(score);
            writer.writeInt(timePassed);
            writer.writeInt(levelSet.size());
            for(Level l: levelSet) {
                l.save(writer);
            }
            User.save(writer);
        } catch (IOException e) {
            Alert a = new Alert(AlertType.ERROR, "There was a problem with saving the data.");
            a.show();
        }
    }

    // Loads a Saved Game
    public void load() {
        try(DataInputStream input = new DataInputStream(new FileInputStream("Saves/SavedGame.txt"))) {
            score = input.readInt();
            timePassed = input.readInt();
            levelSet = new ArrayList<Level>();
            for(int i = 0; i < input.readInt(); ++i) {
                levelSet.add(Level.load(input));
            }
            User = player.load(input);
        }
        catch (IOException e) {
            Alert a = new Alert(AlertType.ERROR, "There was a problem reading the save file.");
            a.show();
        }
    }
}