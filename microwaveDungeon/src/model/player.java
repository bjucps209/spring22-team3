package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class player extends entity {
    
    String powerup;
    int roomXCoord, roomYCoord; // Added RoomCoords and floor to show player loc. -EW
    int floor;

    public player(int setHealth, int setSpeed, int setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }




    


    public String getPowerup() {
        return powerup;
    }







    public void setPowerup(String powerup) {
        this.powerup = powerup;
    }







    // Given an OutputStream, this method saves the player's attributes 
    @Override
    public void save(DataOutputStream output) throws IOException {
        output.writeInt(getId());
        output.writeInt(getHealth());
        output.writeInt(getXcoord());
        output.writeInt(getYcoord());
        output.writeInt(getSpeed());
        output.writeInt(getDamage());
        output.writeInt(roomXCoord);
        output.writeInt(roomYCoord);
        output.writeInt(floor);
    }
}
