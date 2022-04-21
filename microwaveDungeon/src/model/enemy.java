package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class enemy extends entity {

    public enemy(int setHealth, double setSpeed, double setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }

    private int updateCount = 0;


    public void updatePosition(Double playerX, Double playerY) {

        double direction2 = 0;

        // if (Xcoord > 710) {
        //     Xcoord -= 710;
        // } else if (Ycoord > 550) {
        //     Ycoord -= 550;
        // } else if (Xcoord < 0) {
        //     Xcoord += 710;
        // } else if (Ycoord < 0) {
        //     Ycoord += 550;
        // }
        // ++updateCount;
        // Random rand = new Random();
        // if (updateCount % 5 == 0) {
        //     direction = rand.nextInt(360);
        // }

        if (playerX < Xcoord && playerY < Ycoord){
            direction = Math.atan((playerY - Ycoord)/(playerX - Xcoord)) *(180 / Math.PI) + 180;
            direction2 = Math.atan((playerY - Ycoord)/(playerX - Xcoord)) *(180 / Math.PI) + 180;
        } else if (playerX < Xcoord && playerY > Ycoord){
            direction = Math.atan((playerY - Ycoord)/(playerX - Xcoord)) *(180 / Math.PI) + 180;
            direction2 = Math.atan((playerY - Ycoord)/(playerX - Xcoord)) *(180 / Math.PI) + 180;
        } else if (playerX > Xcoord && playerY < Ycoord){
            direction = Math.atan((playerY - Ycoord)/(playerX - Xcoord)) *(180 / Math.PI);
            direction2 = Math.atan((playerY - Ycoord)/(playerX - Xcoord)) *(180 / Math.PI);
        } else if (playerX > Xcoord && playerY > Ycoord){
            direction = Math.atan((playerY - Ycoord)/(playerX - Xcoord)) *(180 / Math.PI);
            direction2 = Math.atan((playerY - Ycoord)/(playerX - Xcoord)) *(180 / Math.PI);
        }
         

        

        
        Xcoord += speed * Math.cos(direction * Math.PI / 180);
        Ycoord += speed * Math.sin(direction2 * Math.PI / 180);
        

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
    public void save(PrintWriter output) throws IOException {
        super.save(output);
        output.println(size);
    }
}
