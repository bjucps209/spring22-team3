package model;

import java.io.DataOutputStream;

public class obstacle extends entity {

    obstacle(int setHealth, int setSpeed, int setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }

    // TODO: Given an OutputStream, this method saves the obstacle's attributes 
    public void save(DataOutputStream output) {
    }
}
