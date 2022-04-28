//-----------------------------------------------------------
//File:   obstacle.java
//Desc:   This file contains the obstacle's information.
//-----------------------------------------------------------

package model;

import java.io.IOException;
import java.io.PrintWriter;

public class obstacle extends entity {




    public obstacle(double setHealth, double setSpeed, double setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }

    @Override
    // Given an OutputStream, this method saves the obstacle's attributes 
    public void save(PrintWriter output) throws IOException {
        output.println(getXcoord());
        output.println(getYcoord());
    }
}
