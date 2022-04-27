//-----------------------------------------------------------
//File:   position.java
//Desc:   This file holds the position class.
//----------------------------------------------------------- 
package model;
//This is the class that holds a position by a x and y coordinate.
public class position {
    private int x;

    private int y;

    position(int x, int y){
        this.x = x;

        this.y = y;

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    
}
