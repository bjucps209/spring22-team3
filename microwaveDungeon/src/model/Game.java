//-----------------------------------------------------------
//File:   Game.java
//Desc:   This file contains the game information and is used
//        for basic gamleplay and functionality
//-----------------------------------------------------------

package model;

import java.io.*;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Game {
    private ArrayList<Level> easyLevelList = new ArrayList<Level>(); // list of easy levels

    private ArrayList<Level> mediumLevelList = new ArrayList<Level>(); // list of medium levels

    private ArrayList<Level> hardLevelList = new ArrayList<Level>(); // list of hard levels

    private ArrayList<Level> nukeLevelList = new ArrayList<Level>(); // list of nuke levels
    private ArrayList<Level> customLevelList = new ArrayList<Level>(); // list of custom levels

    private ArrayList<Level> levelSet; // Added to simplify saving so that the Save method can easily iterate through each level

    private int timePassed = 0; // time that has passed (used for scoring)

    private int score = 0; // player's score

    private characters character; // player's current selected character

    private difficulties diff; // the selected difficults

    private player User; // the player's entity (contains health, x & y, damage, etc.)

    private int currentRoom; // the room the player is currently in

    private position currentRoomCoords; // the coords of the player

    private int currentLevel = 0; // Current level

    private double levelBarCap = 100; // determines the threshold for receiving a power up
    
    private double primaryCooldown, abilityCooldown; // player cooldowns for shooting and using ability

    private boolean isNotPaused = true; // paused status


    @FXML
    Pane pane;

    public Game(difficulties setDiff, characters setCharacter){
        diff = setDiff;
        character = setCharacter;
        generateLevels(setDiff);
        KeyFrame keyframe = new KeyFrame(Duration.seconds(1), this::incrementTimePassed);
        Timeline timer = new Timeline(keyframe);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    // increments the time passed each second
    public void incrementTimePassed(ActionEvent e) {
        if(isNotPaused)
            ++timePassed;
    }


    public void onStaircaseReached(){
        
    }

    // creates the 3 levels for each difficult
    public void generateLevels(difficulties diff){

        switch (diff){
            case EASY:
                easyLevelList.add(new Level(difficulties.EASY));
                easyLevelList.add(new Level(difficulties.EASY));
                easyLevelList.add(new Level(difficulties.EASY));
                
                //level 1
                easyLevelList.get(0).addRoom(new room(0, 2, false));
                easyLevelList.get(0).getRooms().get(0).addEnemy(new enemy(5, 10, 3, 1, 300, 250));
                easyLevelList.get(0).getRooms().get(0).addDoor(new door(2147483647, 0, 0, 5, 770, 250));
                easyLevelList.get(0).getRooms().get(0).getDoorList().get(0).setDir(directions.East);

                easyLevelList.get(0).addRoom(new room(1, 2, false));
                easyLevelList.get(0).getRooms().get(1).addDoor(new door(2147483647, 0, 0, 5, 0, 200));
                easyLevelList.get(0).getRooms().get(1).getDoorList().get(0).setDir(directions.West);
                easyLevelList.get(0).getRooms().get(1).addEnemy(new enemy(5, 10, 3, 1, 300, 150));
                easyLevelList.get(0).getRooms().get(1).addEnemy(new enemy(5, 10, 3, 1, 300, 250));
                easyLevelList.get(0).getRooms().get(1).addEnemy(new enemy(5, 10, 3, 1, 300, 500));
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
                break;

            case MEDIUM:
                mediumLevelList.add(new Level(difficulties.MEDIUM));
                mediumLevelList.add(new Level(difficulties.MEDIUM));
                mediumLevelList.add(new Level(difficulties.MEDIUM));
                
                //level 1
                mediumLevelList.get(0).addRoom(new room(0, 2, false));
                mediumLevelList.get(0).getRooms().get(0).addEnemy(new enemy(7, 10, 5, 1, 300, 250));
                mediumLevelList.get(0).getRooms().get(0).addDoor(new door(2147483647, 0, 0, 5, 770, 250));
                mediumLevelList.get(0).getRooms().get(0).getDoorList().get(0).setDir(directions.East);
                mediumLevelList.get(0).addRoom(new room(1, 2, false));
                mediumLevelList.get(0).getRooms().get(1).addEnemy(new enemy(7, 10, 5, 1, 300, 150));
                mediumLevelList.get(0).getRooms().get(1).addEnemy(new enemy(7, 10, 5, 1, 300, 250));
                mediumLevelList.get(0).getRooms().get(1).addEnemy(new enemy(7, 10, 5, 1, 300, 500));
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
                break;

            case HARD:
                hardLevelList.add(new Level(difficulties.HARD));
                hardLevelList.add(new Level(difficulties.HARD));
                hardLevelList.add(new Level(difficulties.HARD));
                
                //level 1
                hardLevelList.get(0).addRoom(new room(0, 2, false));
                hardLevelList.get(0).getRooms().get(0).addEnemy(new enemy(5, 10, 2, 1, 300, 250));
                hardLevelList.get(0).getRooms().get(0).addDoor(new door(2147483647, 0, 0, 5, 770, 250));
                hardLevelList.get(0).getRooms().get(0).getDoorList().get(0).setDir(directions.East);
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
                break;

                
                
            case NUKE:
                nukeLevelList.add(new Level(difficulties.NUKE));
                nukeLevelList.add(new Level(difficulties.NUKE));
                nukeLevelList.add(new Level(difficulties.NUKE));
                
                //level 1
                nukeLevelList.get(0).addRoom(new room(0, 2, false));
                nukeLevelList.get(0).getRooms().get(0).addEnemy(new enemy(5, 10, 2, 1, 300, 250));
                nukeLevelList.get(0).getRooms().get(0).addDoor(new door(2147483647, 0, 0, 5, 770, 250));
                nukeLevelList.get(0).getRooms().get(0).getDoorList().get(0).setDir(directions.East);
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
                break;
            case CUSTOM:
                //loop through the custom level folder and add each level to the list
                File folder = new File("src/Levels");
                File[] listOfFiles = folder.listFiles();
                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        LevelData customLevel = new LevelData(Integer.parseInt(file.getName().split("\\.")[0]));
                       Level level = customLevel.load();
                     
                        customLevelList.add(level);

                        
                    }
                }
                
                levelSet = customLevelList;
                break;
            
        }
        
        
        
    }

    public void loadRoom(int x, int y){
        //for (){
            
        //}
    }

    // getters and setters
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

    public double getLevelBarCap() {
        return levelBarCap;
    }

    public void setLevelBarCap(double levelBarCap) {
        this.levelBarCap = levelBarCap;
    }

    public double getPrimaryCooldown() {
        return primaryCooldown;
    }

    public void setPrimaryCooldown(double primaryCooldown) {
        this.primaryCooldown = primaryCooldown;
    }

    public double getAbilityCooldown() {
        return abilityCooldown;
    }

    public void setAbilityCooldown(double abilityCooldown) {
        this.abilityCooldown = abilityCooldown;
    }
    
    public boolean isNotPaused() {
        return isNotPaused;
    }

    public void setNotPaused(boolean isNotPaused) {
        this.isNotPaused = isNotPaused;
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
        try (var writer = new PrintWriter(new FileWriter(file))) {
            switch(character) {
                case HPOCKET:
                    writer.println(1);
                    break;
                case PIZZA:
                    writer.println(2);
                    break;
                case RAMEN:
                    writer.println(3);
                    break;
                case MAC:
                    writer.println(4);
                    break;
            }
            switch(diff) {
                case EASY:
                    writer.println(1);
                    break;
                case MEDIUM:
                    writer.println(2);
                    break;
                case HARD:
                    writer.println(3);
                    break;
                case NUKE:
                    writer.println(4);
                    break;
                case CUSTOM:
                    writer.println(5);
                    break;
            }
            writer.println(User.getName());
            writer.println(score);
            writer.println(levelBarCap);
            writer.println(primaryCooldown);
            writer.println(abilityCooldown);
            writer.println(timePassed);
            writer.println(levelSet.size());
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
    public static Game load(boolean isTest) {
        String filename;
        if(isTest)
            filename = "src\\Saves\\SampleSave.txt";
        else
            filename = "src\\Saves\\SavedGame.txt";
        Game output = null;
        try(BufferedReader input = new BufferedReader(new FileReader(filename))) {
            characters loadCharacter;
            switch(Integer.parseInt(input.readLine())) {
                case 1:
                    loadCharacter = characters.HPOCKET;
                    break;
                case 2:
                    loadCharacter = characters.PIZZA;
                    break;
                case 3:
                    loadCharacter = characters.RAMEN;
                    break;
                case 4:
                    loadCharacter = characters.MAC;
                    break;
                default:
                    loadCharacter = characters.HPOCKET;
                    break;
            }
            difficulties loadDiff;
            switch(Integer.parseInt(input.readLine())) {
                case 1:
                    loadDiff = difficulties.EASY;
                    break;
                case 2:
                    loadDiff = difficulties.MEDIUM;
                    break;
                case 3:
                    loadDiff = difficulties.HARD;
                    break;
                case 4:
                    loadDiff = difficulties.NUKE;
                    break;
                case 5:
                    loadDiff = difficulties.CUSTOM;
                    break;
                default:
                    loadDiff = difficulties.EASY;
                    break;
            }
            output = new Game(loadDiff, loadCharacter);
            String userName = input.readLine();
            output.setScore(Integer.parseInt(input.readLine()));
            output.setLevelBarCap(Double.parseDouble(input.readLine()));
            output.setPrimaryCooldown(Double.parseDouble(input.readLine()));
            output.setAbilityCooldown(Double.parseDouble(input.readLine()));
            output.setTimePassed(Integer.parseInt(input.readLine()));
            ArrayList<Level> loadLevelSet = new ArrayList<Level>();
            int levelCount = Integer.parseInt(input.readLine());
            for(int i = 0; i < levelCount; ++i) {
                loadLevelSet.add(Level.load(input, output.getDiff()));
            }
            output.setLevelSet(loadLevelSet);
            output.setUser(player.load(input));
            output.getUser().setName(userName);
        }
        catch (IOException e) {
            Alert a = new Alert(AlertType.ERROR, "There was a problem reading the save file: " + e.getMessage());
            a.show();
        }
        return output;
    }
}