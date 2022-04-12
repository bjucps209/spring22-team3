package model;

import java.util.ArrayList;

public class Game {
    private ArrayList<Level> levelList = new ArrayList<Level>();

    private player player;

    private difficulties diff;

    public Game(){

    }

    public ArrayList<Level> getLevelList() {
        return levelList;
    }

    public void setLevelList(ArrayList<Level> levelList) {
        this.levelList = levelList;
    }

    public player getPlayer() {
        return player;
    }

    public void setPlayer(player player) {
        this.player = player;
    }

    public difficulties getDiff() {
        return diff;
    }

    public void setDiff(difficulties diff) {
        this.diff = diff;
    }

    

}
