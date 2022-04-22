package model;

import java.io.IOException;
import java.io.PrintWriter;

public class entity {


    //instance variables for storing properties of entities
    protected int health;

    protected double speed;

    protected int id;

    protected double direction;
    
    protected double damage;
    
    protected int Xcoord;

    protected int Ycoord;
    
    
    //setters/getters for the instance variables
    public int getXcoord() {
        return Xcoord;
    }

    public void setXcoord(int xcoord) {
        Xcoord = xcoord;
    }

    public int getYcoord() {
        return Ycoord;
    }

    public void setYcoord(int ycoord) {
        Ycoord = ycoord;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    @Override
    public String toString() {
        return "entity [health=" + health + ", speed=" + speed + ", id=" + id + ", damage=" + damage + ", Xcoord=" + Xcoord + ", Ycoord=" + Ycoord + "]";
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    //constructor
    public entity(int setHealth, double setSpeed, double setDamage, int setId, int xcoord, int ycoord){
        health = setHealth;
        speed = setSpeed;
        damage = setDamage;
        id = setId;
        Xcoord = xcoord;
        Ycoord = ycoord;

    }


    // Given an OutputStream, this method saves the entity's attributes 
    public void save(PrintWriter output) throws IOException {
            output.println(health);
            output.println(speed);
            output.println(damage); 
            output.println(id);
            output.println(Xcoord);
            output.println(Ycoord);
    }
    
}
