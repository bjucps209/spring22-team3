package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class entity2 {

    private static int nextId =0;
    //instance variables for storing properties of entities
    private int health;

    private int speed;

    private int id;
    
    private int damage;
    
    private int Xcoord;

    private int Ycoord;
    public static EntityHandler handler;

    public static void setHandler(EntityHandler newHandler) {
        handler = newHandler;
    }
    public void updateLocation() {
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
    @Override
    public String toString() {
        return "entity [health=" + health + ", speed=" + speed + ", id=" + id + ", damage=" + damage + ", Xcoord=" + Xcoord + ", Ycoord=" + Ycoord + "]";
    }

    //constructor
    public entity2(int setHealth, int setSpeed, int setDamage, int setId, int xcoord, int ycoord){
        health = setHealth;
        speed = setSpeed;
        damage = setDamage;
        nextId++;
        id = nextId;
        Xcoord = xcoord;
        Ycoord = ycoord;

    }

    // Given an OutputStream, this method saves the entity's attributes 
    public void save(DataOutputStream output) throws IOException {
            output.writeInt(id);
            output.writeInt(Xcoord);
            output.writeInt(Ycoord);
            output.writeInt(health);
            output.writeInt(speed);
            output.writeInt(damage);
    }
    
}
