//-----------------------------------------------------------
//File:   entity.java
//Desc:   This file holds the entity class.
//----------------------------------------------------------- 
package model;

import java.io.DataOutputStream;
import java.io.IOException;

//This is the class that holds the data for the entity.
public class entity {

    private static int nextId =0; // Added to allow for unique IDs 
    //instance variables for storing properties of entities
    protected int health;

    protected double speed;

    protected int id;

    protected int direction;
    
    protected double damage;
    
    protected int Xcoord;

    protected int Ycoord;
    public static EntityHandler handler;

    public static void setHandler(EntityHandler newHandler) {
        handler = newHandler;
    }
    public void updateLocationTable() {
        handler.updateTable(Xcoord, Ycoord, id, health, speed, damage);
    }
    
    //setters/getters for the instance variables
    public int getXcoord() {
        return Xcoord;
    }

    public void setXcoord(int xcoord) {
        Xcoord = xcoord;
        handler.updateTable(Xcoord, Ycoord, id, health, speed, damage);
    }

    public int getYcoord() {
        return Ycoord;
    }

    public void setYcoord(int ycoord) {
        Ycoord = ycoord;
        handler.updateTable(Xcoord, Ycoord, id, health, speed, damage);
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

    public void setSpeed(double speed) {
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

    public void setDamage(double damage) {
        this.damage = damage;
    }
    @Override
    public String toString() {
        return "entity [health=" + health + ", speed=" + speed + ", id=" + id + ", damage=" + damage + ", Xcoord=" + Xcoord + ", Ycoord=" + Ycoord + "]";
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    //constructor
    public entity(int setHealth, double setSpeed, double setDamage, int setId, int xcoord, int ycoord){
        health = setHealth;
        speed = setSpeed;
        damage = setDamage;
        nextId++;
        id = nextId;
        Xcoord = xcoord;
        Ycoord = ycoord;

    }

    public void updatePosition() {
        Xcoord += speed * Math.cos(direction * Math.PI / 180);
        Ycoord += speed * Math.sin(direction * Math.PI / 180);
        if (Xcoord > 710){
            Xcoord -= 710;
        } else if (Ycoord > 550){
            Ycoord -= 550;
        } else if (Xcoord < 0){
            Xcoord += 710;
        } else if (Ycoord < 0){
            Ycoord += 550;
        }

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
