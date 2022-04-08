package model;

import java.io.DataOutputStream;

public class player extends entity {
    
    String powerup;

    public player(int setHealth, int setSpeed, int setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }




    


    public String getPowerup() {
        return powerup;
    }







    public void setPowerup(String powerup) {
        this.powerup = powerup;
    }







    // TODO: Given an OutputStream, this method saves the player's attributes 
    public void save(DataOutputStream output) {
    }
}
