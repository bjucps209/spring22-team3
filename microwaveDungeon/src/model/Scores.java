package model;

public class Scores {

    private String name;
    private int score;

    public Scores(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return "\t" + name + " || Score: " + score;
    }

    // getters and setters
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

