package model;

import java.io.DataOutputStream;

public class enemy extends entity {

    int size;
    
    enemy(int setHealth, int setSpeed, int setDamage, int setId) {
        super(setHealth, setSpeed, setDamage, setId);
        //TODO Auto-generated constructor stub
    }

    // TODO: Given an OutputStream, this method saves the enemy's attributes 
    public void save(DataOutputStream output) {
    }
}
 