//-----------------------------------------------------------
//File:   Scores.java
//Desc:   This file holds the Scores class.
//----------------------------------------------------------- 
package model;
//This is the class that holds the data for a score.
public class Scores {

    String name; //This is the name of the player.
    String score; //This is the score of the player.

    // TODO: this will be called after a player dies. Their score and name will be added to the .txt file
    public Scores(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    
}
