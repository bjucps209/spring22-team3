package model;

import java.util.ArrayList;

public class ScoreList {

    public ArrayList<Scores> scoreList;

    static final String FILENAME = "scores.txt";

    public ScoreList() {
        scoreList = new ArrayList<Scores>();
    }

    // // gets the scores from the .txt file and adds it to the scoreList variable 
    // public void getScores() {
    //     throw new RuntimeException("Method not implemented");
    //     // get scores from txt file and save them as 
    //     // Score objects with loadData().

    //     // scoreList.add(Score_Object);
    //     // sort();
    // }

    // Writes the data from the list to the txt file, is used after getScores()
    public void saveData() {
        throw new RuntimeException("Method not implemented");
        // for (Scores s : scoreList) {
        //    write(s);
        // }
    }

    // Reads the data from the file and places them into the scoreList variable 
    // then sorts the list from highest score to lowest score.
    public void loadData() {
        throw new RuntimeException("Method not implemented");
        // get scores from txt file and save them as 
        // Score objects with loadData().

        // scoreList.add(Score_Object);
        // sort();

    }

    // Sorts the array list from highest score to lowest
    public ArrayList<Scores> sortList() {
        throw new RuntimeException("Method not implemented");
    }

    // adds a score to the list
    public void add(Scores score) {
        this.scoreList.add(score);
    }

    public Scores get(int index) {
        return this.scoreList.get(index);
    }

    // getter method
    public ArrayList<Scores> getScoreList() {
        return scoreList;
    }

}
