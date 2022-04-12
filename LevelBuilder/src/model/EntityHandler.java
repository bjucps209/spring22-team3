//-----------------------------------------------------------
//File:   CritterHandler.java
//Desc:   This is the Custom event handler for the Critter class.
//----------------------------------------------------------- 
package model;

public interface EntityHandler {
    void updateTable(int x, int y, int id, int health, int speed, int damage);
    
}
