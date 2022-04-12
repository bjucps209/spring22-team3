package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class staircase extends entity {
 
    directions dir;

    public staircase(int setHealth, double setSpeed, double setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }

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
