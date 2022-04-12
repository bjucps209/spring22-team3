//-----------------------------------------------------------
//File:   CritterHandler.java
//Desc:   This is the Custom event handler for the Critter class.
//----------------------------------------------------------- 
package model;

public interface CritterHandler {
    void updateTable(int x, int y, int id, int health, int speed, int damage);
    
}
