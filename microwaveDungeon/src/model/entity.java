package model;

import java.io.DataOutputStream;

public class entity {

    private int health;

    private int speed;

    private int id;
    
    private int damage;


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

    entity(int setHealth, int setSpeed, int setDamage, int setId){
        health = setHealth;
        speed = setSpeed;
        damage = setDamage;
        id = setId;

    }

    // TODO: Given an OutputStream, this method saves the entity's attributes 
    public void save(DataOutputStream output) {
    }
    
}
