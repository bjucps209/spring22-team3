//-----------------------------------------------------------
//File:   enemy.java
//Desc:   This file holds the enemy class.
//----------------------------------------------------------- 
package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
//This is the class that holds the data for the enemy.
public class enemy extends entity {

    public enemy(int setHealth, double setSpeed, double setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }

    private int updateCount = 0;



    @Override
    
    public void updatePosition() {
        if (Xcoord > 710) {
            Xcoord -= 710;
        } else if (Ycoord > 550) {
            Ycoord -= 550;
        } else if (Xcoord < 0) {
            Xcoord += 710;
        } else if (Ycoord < 0) {
            Ycoord += 550;
        }
        ++updateCount;
        Random rand = new Random();
        if (updateCount % 5 == 0) {
            direction = rand.nextInt(360);
        }
        Xcoord += speed * Math.cos(direction * Math.PI / 180);
        Ycoord += speed * Math.sin(direction * Math.PI / 180);
        

    }

    int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    // Given an OutputStream, this method saves the enemy's attributes
    @Override
    public void save(DataOutputStream output) throws IOException {
        super.save(output);
        output.writeInt(size);
    }
}
