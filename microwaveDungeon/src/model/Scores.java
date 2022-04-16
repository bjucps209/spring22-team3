package model;

public class Scores {

    private String name;
    private int score;

    // TODO: this will be called after a player dies. Their score and name will be added to the .txt file
    public Scores(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student [score=" + score + ", name=" + name + "]";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    
}

