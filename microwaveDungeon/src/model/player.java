package model;

import java.io.DataOutputStream;

public class player extends entity {

    String powerup;
    
    player(int setHealth, int setSpeed, int setDamage, int setId) {
        super(setHealth, setSpeed, setDamage, setId);
        //TODO Auto-generated constructor stub
    }

    // TODO: Given an OutputStream, this method saves the player's attributes 
    public void save(DataOutputStream output) {
    }
}
