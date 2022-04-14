package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.spec.RC2ParameterSpec;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

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

    private position currentRoom;

    private int currentLevel;


    @FXML
    Pane pane;

    public Game(difficulties setDiff, characters setCharacter){
        diff = setDiff;
        character = setCharacter;
        generateGame(); // Move this method into Constructor?
    }

    public void generateGame(){

        switch(diff){

            case EASY:
            User = new player(25, 10, 2, 0, 0, 0);
            levelSet = easyLevelList;
            currentLevel = 1;
            currentRoom.setX(levelSet.get(0).getRooms().get(0).getX());
            currentRoom.setY(levelSet.get(0).getRooms().get(0).getY());
            break;

            case MEDIUM:
            User = new player(25, 10, 2, 1, 0, 0);
            levelSet = mediumLevelList;
            currentLevel = 1;
            currentRoom.setX(levelSet.get(0).getRooms().get(0).getX());
            currentRoom.setY(levelSet.get(0).getRooms().get(0).getY());
            break;

            case HARD:
            User = new player(20, 10, 2, 1, 0, 0);
            levelSet = hardLevelList;
            currentLevel = 1;
            currentRoom.setX(levelSet.get(0).getRooms().get(0).getX());
            currentRoom.setY(levelSet.get(0).getRooms().get(0).getY());
            break;

            case NUKE:
            User = new player(15, 10, 1.5, 1, 0, 0);
            levelSet = nukeLevelList;
            currentLevel = 1;
            currentRoom.setX(levelSet.get(0).getRooms().get(0).getX());
            currentRoom.setY(levelSet.get(0).getRooms().get(0).getY());
            break;
        }
        Level firstLevel = new Level(diff); // Moved this out of Switch for simplification
        Level secondLevel = new Level(diff);
        Level ThirdLevel = new Level(diff);
        //firstLevel.getRooms().add();
        levelSet = new ArrayList<Level>(Arrays.asList(firstLevel, secondLevel, ThirdLevel));
    }

    public void onStaircaseReached(){
        
    }

    public void generateLevels(difficulties diff){
        easyLevelList.add(new Level(difficulties.EASY));
        easyLevelList.add(new Level(difficulties.EASY));
        easyLevelList.add(new Level(difficulties.EASY));
        
        //level 1
        easyLevelList.get(0).addRoom(new room(0, 2, false));
        easyLevelList.get(0).addRoom(new room(1, 2, false));
        easyLevelList.get(0).addRoom(new room(2, 2, false));
        
        //level 2
        easyLevelList.get(1).addRoom(new room(2, 2, false));
        easyLevelList.get(1).getRooms().get(0).addEntity(new enemy(5, 10, 2, 1, 450, 500));
        easyLevelList.get(1).addRoom(new room(1, 2, false));
        easyLevelList.get(1).addRoom(new room(2, 1, false));
        easyLevelList.get(1).addRoom(new room(2, 0, false));
        easyLevelList.get(1).addRoom(new room(3, 2, false));
        easyLevelList.get(1).addRoom(new room(2, 3, false));
        easyLevelList.get(1).addRoom(new room(3, 3, false));

        //level 3
        easyLevelList.get(2).addRoom(new room(2, 2, false));
        easyLevelList.get(2).addRoom(new room(1, 2, false));
        easyLevelList.get(2).addRoom(new room(2, 1, false));
        easyLevelList.get(2).addRoom(new room(2, 0, false));
        easyLevelList.get(2).addRoom(new room(3, 2, false));
        easyLevelList.get(2).addRoom(new room(2, 3, false));
        easyLevelList.get(2).addRoom(new room(3, 3, false));
    }

    public void onDoorReached(){
        //for (int i = 0; i < levelSet.size() - 1; ++i){
        //    for (int j = 0; j < levelSet.get(i).getRooms().size() -1; ++i){
        //        int x = levelSet.get(i).getRooms().get(j).getX();
        //        int y = levelSet.get(i).getRooms().get(j).getY();
        //        if(x == roomCoord.getX() && y == roomCoord.getY()){
        //            loadRoom(x, y);
        //            roomCoord.setX(x);
        //            roomCoord.setY(y);
        //        }
        //    }
        //}

    }

    public void loadRoom(int x, int y){
        //for (){
            
        //}
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

    // Added for Unit testing
    public ArrayList<Level> getLevelSet() {
        return levelSet;
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

    // Loads a specified saved game file -Made for testing
    public void load(String filename) {
        try(DataInputStream input = new DataInputStream(new FileInputStream(filename))) {
            score = input.readInt();
            timePassed = input.readInt();
            levelSet = new ArrayList<Level>();
            for(int i = 0; i < input.readInt(); ++i) {
                levelSet.add(Level.load(input));
            }
            User = player.load(input);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Problem reading from given filename");
        }
    }
}