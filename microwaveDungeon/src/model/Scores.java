package model;

public class Scores {

    String name;
    String score;

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
