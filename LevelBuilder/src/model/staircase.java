//-----------------------------------------------------------
//File:   staircase.java
//Desc:   This file holds the staircase class.
//----------------------------------------------------------- 
package model;

import java.io.DataOutputStream;
import java.io.IOException;
// This is the class that holds the data for the staircase.
public class staircase extends entity {
 


    public staircase(int setHealth, double setSpeed, double setDamage, int setId, int xcoord,
            int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }

    directions dir; // Added direction to show staircase direction 



    public directions getDir() {
        return dir;
    }

    public void setDir(directions dir) {
        this.dir = dir;
    }

   @Override
   public void save(DataOutputStream output) throws IOException {
       output.writeInt(getXcoord());
       output.writeInt(getYcoord());
   }


}
