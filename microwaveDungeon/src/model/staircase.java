package model;

import java.io.IOException;
import java.io.PrintWriter;

public class staircase extends entity {

    public staircase(int setHealth, double setSpeed, double setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }

    directions dir;



    public directions getDir() {
        return dir;
    }

    public void setDir(directions dir) {
        this.dir = dir;
    }

   @Override
   public void save(PrintWriter output) throws IOException {
       output.println(getXcoord());
       output.println(getYcoord());
   }


}
