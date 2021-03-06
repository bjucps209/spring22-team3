//-----------------------------------------------------------
//File:   Game.java
//Desc:   This file holds the game class.
//----------------------------------------------------------- 
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
//holds the levels of the game
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

    private int currentRoom;

    private position currentRoomCoords;

    private int currentLevel = 0;


    @FXML
    Pane pane;

    public Game(difficulties setDiff, characters setCharacter){
        diff = setDiff;
        character = setCharacter;
        generateLevels(setDiff);
    }


 

    public void generateLevels(difficulties diff){

        switch (diff){
            case EASY:
                easyLevelList.add(new Level(difficulties.EASY));
                easyLevelList.add(new Level(difficulties.EASY));
                easyLevelList.add(new Level(difficulties.EASY));
                
                //level 1
                easyLevelList.get(0).addRoom(new room(0, 2, false));
                easyLevelList.get(0).getRooms().get(0).addEnemy(new enemy(5, 10, 2, 1, 300, 250));
                easyLevelList.get(0).addRoom(new room(1, 2, false));
                easyLevelList.get(0).addRoom(new room(2, 2, false));
                
                //level 2
                easyLevelList.get(1).addRoom(new room(2, 2, false));
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
                levelSet = easyLevelList;

            case MEDIUM:
                mediumLevelList.add(new Level(difficulties.MEDIUM));
                mediumLevelList.add(new Level(difficulties.MEDIUM));
                mediumLevelList.add(new Level(difficulties.MEDIUM));
                
                //level 1
                mediumLevelList.get(0).addRoom(new room(0, 2, false));
                mediumLevelList.get(0).getRooms().get(0).addEnemy(new enemy(5, 10, 2, 1, 300, 250));
                mediumLevelList.get(0).addRoom(new room(1, 2, false));
                mediumLevelList.get(0).addRoom(new room(2, 2, false));
                
                //level 2
                mediumLevelList.get(1).addRoom(new room(2, 2, false));
                mediumLevelList.get(1).addRoom(new room(1, 2, false));
                mediumLevelList.get(1).addRoom(new room(2, 1, false));
                mediumLevelList.get(1).addRoom(new room(2, 0, false));
                mediumLevelList.get(1).addRoom(new room(3, 2, false));
                mediumLevelList.get(1).addRoom(new room(2, 3, false));
                mediumLevelList.get(1).addRoom(new room(3, 3, false));

                //level 3
                mediumLevelList.get(2).addRoom(new room(2, 2, false));
                mediumLevelList.get(2).addRoom(new room(1, 2, false));
                mediumLevelList.get(2).addRoom(new room(2, 1, false));
                mediumLevelList.get(2).addRoom(new room(2, 0, false));
                mediumLevelList.get(2).addRoom(new room(3, 2, false));
                mediumLevelList.get(2).addRoom(new room(2, 3, false));
                mediumLevelList.get(2).addRoom(new room(3, 3, false));
                levelSet = mediumLevelList;

            case HARD:
                hardLevelList.add(new Level(difficulties.HARD));
                hardLevelList.add(new Level(difficulties.HARD));
                hardLevelList.add(new Level(difficulties.HARD));
                
                //level 1
                hardLevelList.get(0).addRoom(new room(0, 2, false));
                hardLevelList.get(0).getRooms().get(0).addEnemy(new enemy(5, 10, 2, 1, 300, 250));
                hardLevelList.get(0).addRoom(new room(1, 2, false));
                hardLevelList.get(0).addRoom(new room(2, 2, false));
                
                //level 2
                hardLevelList.get(1).addRoom(new room(2, 2, false));
                hardLevelList.get(1).addRoom(new room(1, 2, false));
                hardLevelList.get(1).addRoom(new room(2, 1, false));
                hardLevelList.get(1).addRoom(new room(2, 0, false));
                hardLevelList.get(1).addRoom(new room(3, 2, false));
                hardLevelList.get(1).addRoom(new room(2, 3, false));
                hardLevelList.get(1).addRoom(new room(3, 3, false));

                //level 3
                hardLevelList.get(2).addRoom(new room(2, 2, false));
                hardLevelList.get(2).addRoom(new room(1, 2, false));
                hardLevelList.get(2).addRoom(new room(2, 1, false));
                hardLevelList.get(2).addRoom(new room(2, 0, false));
                hardLevelList.get(2).addRoom(new room(3, 2, false));
                hardLevelList.get(2).addRoom(new room(2, 3, false));
                hardLevelList.get(2).addRoom(new room(3, 3, false));
                levelSet = hardLevelList;

            case NUKE:
                nukeLevelList.add(new Level(difficulties.NUKE));
                nukeLevelList.add(new Level(difficulties.NUKE));
                nukeLevelList.add(new Level(difficulties.NUKE));
                
                //level 1
                nukeLevelList.get(0).addRoom(new room(0, 2, false));
                nukeLevelList.get(0).getRooms().get(0).addEnemy(new enemy(5, 10, 2, 1, 300, 250));
                nukeLevelList.get(0).addRoom(new room(1, 2, false));
                nukeLevelList.get(0).addRoom(new room(2, 2, false));
                
                //level 2
                nukeLevelList.get(1).addRoom(new room(2, 2, false));
                nukeLevelList.get(1).addRoom(new room(1, 2, false));
                nukeLevelList.get(1).addRoom(new room(2, 1, false));
                nukeLevelList.get(1).addRoom(new room(2, 0, false));
                nukeLevelList.get(1).addRoom(new room(3, 2, false));
                nukeLevelList.get(1).addRoom(new room(2, 3, false));
                nukeLevelList.get(1).addRoom(new room(3, 3, false));

                //level 3
                nukeLevelList.get(2).addRoom(new room(2, 2, false));
                nukeLevelList.get(2).addRoom(new room(1, 2, false));
                nukeLevelList.get(2).addRoom(new room(2, 1, false));
                nukeLevelList.get(2).addRoom(new room(2, 0, false));
                nukeLevelList.get(2).addRoom(new room(3, 2, false));
                nukeLevelList.get(2).addRoom(new room(2, 3, false));
                nukeLevelList.get(2).addRoom(new room(3, 3, false));
                levelSet = nukeLevelList;
        }
        
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

    

    public void setScore(int score) {
        this.score = score;
    }

    public player getUser() {
        return User;
    }

    public void setUser(player user) {
        User = user;
    }

    public position getCurrentRoomCoords() {
        return currentRoomCoords;
    }

    public void setCurrentRoomCoords(position currentRoomCoords) {
        this.currentRoomCoords = currentRoomCoords;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setCurrentRoom(int currentRoom) {
        this.currentRoom = currentRoom;
    }

    public int getCurrentRoom() {
        return currentRoom;
    }

    // Saves the Game
    public void save() {
        File file = new File("src\\Saves\\SavedGame.txt");
        if(file.exists())
            file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            Alert a = new Alert(AlertType.ERROR, "There was a problem with creating a file: \n" + e.getLocalizedMessage());
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
            Alert a = new Alert(AlertType.ERROR, "There was a problem with saving the data: \n" + e.getLocalizedMessage());
            a.show();
        }
    }

    // Loads a Saved Game
    public void load() {
        try(DataInputStream input = new DataInputStream(new FileInputStream("src\\Saves\\SavedGame.txt"))) {
            score = input.readInt();
            timePassed = input.readInt();
            levelSet = new ArrayList<Level>();
            for(int i = 0; i < input.readInt(); ++i) {
                levelSet.add(Level.load(input));
            }
            User = player.load(input);
        }
        catch (IOException e) {
            Alert a = new Alert(AlertType.ERROR, "There was a problem reading the save file: " + e.getMessage());
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
        }
    }
}