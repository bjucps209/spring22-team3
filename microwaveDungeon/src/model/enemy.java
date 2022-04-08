package model;

import java.io.DataOutputStream;

public class enemy extends entity {
    
    int size;


    public enemy(int setHealth, int setSpeed, int setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }




    


    public int getSize() {
        return size;
    }







    public void setSize(int size) {
        this.size = size;
    }







    // TODO: Given an OutputStream, this method saves the enemy's attributes 
    public void save(DataOutputStream output) {
    }
}
 