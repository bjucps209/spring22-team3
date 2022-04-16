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


    public void onStaircaseReached(){
        
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

    public void setTimePassed(int time) {
        timePassed = time;
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
    
    public void setLevelSet(ArrayList<Level> input) {
        levelSet = input;
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
            switch(character) {
                case HPOCKET:
                    writer.writeInt(1);
                case PIZZA:
                    writer.writeInt(2);
                case RAMEN:
                    writer.writeInt(3);
                case MAC:
                    writer.writeInt(4);
            }
            switch(diff) {
                case EASY:
                    writer.writeInt(1);
                case MEDIUM:
                    writer.writeInt(2);
                case HARD:
                    writer.writeInt(3);
                case NUKE:
                    writer.writeInt(4);
            }
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

    // Factory Method that returns a loaded game
    public static Game load() {
        Game output = null;
        try(DataInputStream input = new DataInputStream(new FileInputStream("src\\Saves\\SavedGame.txt"))) {
            int charInt = input.readInt();
            characters loadCharacter = characters.HPOCKET;
            switch(charInt) {
                case 1:
                    loadCharacter = characters.HPOCKET;
                case 2:
                    loadCharacter = characters.PIZZA;
                case 3:
                    loadCharacter = characters.RAMEN;
                case 4:
                    loadCharacter = characters.MAC;
            }
            int diffInt = input.readInt();
            difficulties loadDiff = difficulties.EASY;
            switch(diffInt) {
                case 1:
                    loadDiff = difficulties.EASY;
                case 2:
                    loadDiff = difficulties.MEDIUM;
                case 3:
                    loadDiff = difficulties.HARD;
                case 4:
                    loadDiff = difficulties.NUKE;
            }
            output = new Game(loadDiff, loadCharacter);
            output.setScore(input.readInt());
            output.setTimePassed(input.readInt());
            ArrayList<Level> loadLevelSet = new ArrayList<Level>();
            for(int i = 0; i < input.readInt(); ++i) {
                loadLevelSet.add(Level.load(input));
            }
            output.setLevelSet(loadLevelSet);
        }
        catch (IOException e) {
            e.printStackTrace();
            Alert a = new Alert(AlertType.ERROR, "There was a problem reading the save file: " + e.getMessage());
            a.show();
        }
        return output;
    }
}