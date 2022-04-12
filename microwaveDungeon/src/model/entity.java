package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class entity {


    //instance variables for storing properties of entities
    private int health;

    private double speed;

    private int id;
    
    private double damage;
    
    private int Xcoord;

    private int Ycoord;
    
    
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
    public void save(DataOutputStream output) throws IOException {
            output.writeInt(health);
            output.writeDouble(speed);
            output.writeDouble(damage); //changed these from int to Double for more specific numbers
            output.writeInt(id);
            output.writeInt(Xcoord);
            output.writeInt(Ycoord);
    }
    
}
