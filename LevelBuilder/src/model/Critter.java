//-----------------------------------------------------------
//File:   Critter.java
//Desc:   This is the Critter class that is used to make trackers and wanderers.
//----------------------------------------------------------- 
package model;

import java.util.Random;

public class Critter {

    protected int id;
    protected CritterType critterType;
    protected int x;
    protected int y;
    protected int speed;
    protected int direction; // 0-359

    private static int nextId;
    public static CritterHandler handler;

    public static void setHandler(CritterHandler newHandler) {
        handler = newHandler;
    }

    public Critter(CritterType critterType) {
        this.critterType = critterType;
        var rand = new Random();
        this.x = rand.nextInt(World.instance().getWidth());
        this.y = rand.nextInt(World.instance().getHeight());
        this.speed = rand.nextInt(10) + 1;
        this.direction = rand.nextInt(360);
        this.id = ++nextId;
    }
    //gets the id of the critter
    public int getId() {
        return id;
    }

    public void updatePosition() {
        x += speed * Math.cos(direction * Math.PI / 180);
        y += speed * Math.sin(direction * Math.PI / 180);
    }

    @Override
    public String toString() {
        return "Critter{" +
                "id=" + id +
                ", critterType=" + critterType +
                ", x=" + x +
                ", y=" + y +
                ", speed=" + speed +
                ", direction=" + direction +
                '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    //sets the x position of the critter and updates the table
    public void setX(int x) {
        this.x = x;
        handler.updateTable(this.x, this.y, this.id, 200, this.speed, 100);
    }
    //sets the y position of the critter and updates the table
    public void setY(int y) {
        this.y = y;
        handler.updateTable(this.x, this.y, this.id, 200, this.speed, 100);
    }
    //updates the table
    public void updateLocation() {
        handler.updateTable(this.x, this.y, this.id, 200, this.speed, 100);
    }

    // get heading
    public int getDirection() {
        return direction;
    }

    // get speed
    public int getSpeed() {
        return speed;
    }

}
