//-----------------------------------------------------------
//File:   door.java
//Desc:   This file contains the door class.
//----------------------------------------------------------- 
package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
//This class is used to create doors to move between rooms.
public class door extends entity {

    public door(double setHealth, double setSpeed, double setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
    }

    private directions dir; 



    public directions getDir() {
        return dir;
    }

    public void setDir(directions dir) {
        this.dir = dir;
    }
    
    // Given a PrintWiter, this method saves the needed data for it to be loaded
    public void save(PrintWriter output) throws IOException {
        switch(dir) {
            case Up:
                output.println(0);
                break;
            case Down:
                output.println(1);
                break;
            case North: 
                output.println(2);
                break;
            case South: 
                output.println(3);
                break;
            case East: 
                output.println(4);
                break;
            case West: 
                output.println(5);
                break;
            default:
                output.println(0);
                break;
        }
        output.println(Xcoord);
        output.println(Ycoord);
    }

    // A factory method that builds a door to return given a BufferedReader
    public static door load(BufferedReader input) throws IOException {
        directions newDir;
        switch(Integer.parseInt(input.readLine())) {
            case 0:
                newDir = directions.Up;
                break;
            case 1:
                newDir = directions.Down;
                break;
            case 2: 
                newDir = directions.North;
                break;
            case 3: 
                newDir = directions.South;
                break;
            case 4: 
                newDir = directions.East;
                break;
            case 5: 
                newDir = directions.West;
                break;
            default:
                newDir = directions.East;
                break;
        }
        door output = new door(2147483647, 0, 0, 5, Integer.parseInt(input.readLine()), Integer.parseInt(input.readLine()));
        output.setDir(newDir);
        return output;
    }    
}
