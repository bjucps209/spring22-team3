//-----------------------------------------------------------
//File:   obstacle.java
//Desc:   This file holds the obstacle class.
//----------------------------------------------------------- 
package model;

import java.io.DataOutputStream;
import java.io.IOException;
//This is the class that holds the data for the obstacle.
public class obstacle extends entity {


    public obstacle(int setHealth, double setSpeed, double setDamage, int setId, int xcoord,
            int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }

    @Override
    // Given an OutputStream, this method saves the obstacle's attributes 
    public void save(DataOutputStream output) throws IOException {
        output.writeInt(getXcoord());
        output.writeInt(getYcoord());
    }
}
