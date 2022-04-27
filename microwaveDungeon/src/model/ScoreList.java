package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class ScoreList {

    private ArrayList<Scores> scoreList; 
    static final String FILENAME = "..\\microwaveDungeon\\src\\Saves\\scores.txt";

    public ScoreList() {
        scoreList = new ArrayList<Scores>();
    }

    /**
     * Writes the data from the list to the txt file, is used after getScores().
     * @throws IOException
     */
    public void saveData() throws IOException {
        try (var writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (Scores obj : this.scoreList) {
                String name = obj.getName();
                int score = obj.getScore();
                writer.println(name + "," + score);
            }
        }
    }

    /**
     * Reads the data from the file and places them into the scoreList variable
     * then sorts the list from highest score to lowest score.
     * @throws IOException
     */
    public void loadData() throws IOException {
        if (Files.exists(Paths.get(FILENAME))) {
            try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
                String str;
                while ((str = br.readLine()) != null) {
                    String[] strArr = str.split(",");
                    String name = strArr[0];
                    String score = strArr[1];
                    Scores scoreObj = new Scores(name, Integer.parseInt(score));
                    this.scoreList.add(scoreObj);
                }
            }
        }
        sortList();
    }

    /**
     * Sorts the array list from highest score to lowest.
     * @return the sorted ArrayList of scores
     */
    public ArrayList<Scores> sortList() {
        scoreList.sort((o1, o2) -> o2.getScore() - o1.getScore());
        return scoreList;
    }

    /**
     * Adds a score to the list and saves the list.
     * @param score
     * @throws IOException
     */
    public void addScore(Scores score) throws IOException {
        this.scoreList.add(score);
        saveData();
    }
    
    /**
     * Gets a score at a certain index.
     * @param index
     * @return the score at the designated index
     */
    public Scores get(int index) {
        return this.scoreList.get(index);
    }

    /**
     * Gets the score list by ;loading from scores.txt
     * @return the list of scores from scores.txt
     * @throws IOException
     */
    public ArrayList<Scores> getScoreList() throws IOException {
        loadData();
        return this.scoreList;
    }

}
