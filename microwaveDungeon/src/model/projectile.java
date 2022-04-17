package model;

public class projectile extends entity {

    public projectile(int setHealth, double setSpeed, double setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }

    private double direction = 0;
        

    public void updatePosition(){
        Xcoord += speed * Math.cos(direction * Math.PI / 180);
        Ycoord += speed * Math.sin(direction * Math.PI / 180);
    }


    public double getDirection() {
        return direction;
    }


    public void setDirection(double direction) {
        this.direction = direction;
    }

    
}
