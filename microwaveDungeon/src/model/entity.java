package model;

import java.io.DataOutputStream;

public class entity {


    //instance variables for storing properties of entities
    private int health;

    private int speed;

    private int id;
    
    private int damage;
    
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

    public int getSpeed() {
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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    //constructor
    public entity(int setHealth, int setSpeed, int setDamage, int setId, int xcoord, int ycoord){
        health = setHealth;
        speed = setSpeed;
        damage = setDamage;
        id = setId;
        Xcoord = xcoord;
        Ycoord = ycoord;

    }

    // TODO: Given an OutputStream, this method saves the entity's attributes 
    public void save(DataOutputStream output) {
    }
    
}
