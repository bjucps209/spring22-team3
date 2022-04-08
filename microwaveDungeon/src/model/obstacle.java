package model;

import java.io.DataOutputStream;

public class obstacle extends entity {
    
    

    obstacle(int setHealth, int setSpeed, int damage, int id) {
        super(setHealth, setSpeed, damage, id);
        //TODO Auto-generated constructor stub
    }

    // TODO: Given an OutputStream, this method saves the obstacle's attributes 
    public void save(DataOutputStream output) {
    }
}
