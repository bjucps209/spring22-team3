package model;

public class door extends entity {





    public door(int setHealth, double setSpeed, double setDamage, int direction, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, direction, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }

    private directions dir;



    public directions getDir() {
        return dir;
    }

    public void setDir(directions dir) {
        this.dir = dir;
    }
    
    
}
