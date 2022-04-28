//-----------------------------------------------------------
//File:   projectile.java
//Desc:   This file contains the projectile information.
//----------------------------------------------------------- 

package model;

public class projectile extends entity {

    public projectile(double setHealth, double setSpeed, double setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        // TODO Auto-generated constructor stub
    }

    private double direction = 0;

    // sets a direction based on the position of the mouse (indicated by cursorX and Y)
    public void setDirection(double cursorX, double cursorY) {
        if (cursorX < Xcoord && cursorY < Ycoord) {
            direction = Math.atan((cursorY - Ycoord) / (cursorX - Xcoord)) * (180 / Math.PI) + 180;
        } else if (cursorX < Xcoord && cursorY > Ycoord) {
            direction = Math.atan((cursorY - Ycoord) / (cursorX - Xcoord)) * (180 / Math.PI) + 180;
        } else if (cursorX > Xcoord && cursorY < Ycoord) {
            direction = Math.atan((cursorY - Ycoord) / (cursorX - Xcoord)) * (180 / Math.PI);
        } else if (cursorX > Xcoord && cursorY > Ycoord) {
            direction = Math.atan((cursorY - Ycoord) / (cursorX - Xcoord)) * (180 / Math.PI);
        }
    }

    // will move the projectile
    public void updatePosition() {
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
