package model;

public class door extends entity {

    private directions dir;

    public door(int setHealth, int setSpeed, int setDamage, int setId, int xcoord, int ycoord) {
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
