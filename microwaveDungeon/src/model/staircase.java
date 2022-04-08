package model;

public class staircase extends entity {
 
    directions dir;

    public staircase(int setHealth, int setSpeed, int setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }

    public directions getDir() {
        return dir;
    }

    public void setDir(directions dir) {
        this.dir = dir;
    }

   


}
