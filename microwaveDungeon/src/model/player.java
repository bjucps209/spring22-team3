package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class player extends entity {
    
    public player(int setHealth, double setSpeed, double setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }


    private String name;
    private String powerup;
    private int roomXCoord, roomYCoord; // Added RoomCoords and floor to show player loc. -EW
    private int floor;
    private characters Character;



    public String getPowerup() {
        return powerup;
    }

    public void setPowerup(String powerup) {
        this.powerup = powerup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomXCoord() {
        return roomXCoord;
    }

    public void setRoomXCoord(int roomXCoord) {
        this.roomXCoord = roomXCoord;
    }

    public int getRoomYCoord() {
        return roomYCoord;
    }

    public void setRoomYCoord(int roomYCoord) {
        this.roomYCoord = roomYCoord;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public characters getCharacter() {
        return Character;
    }

    public void setCharacter(characters character) {
        Character = character;
    }


    // Given an OutputStream, this method saves the player's attributes 
    @Override
    public void save(DataOutputStream output) throws IOException {
        output.writeInt(getId());
        output.writeInt(getHealth());
        output.writeInt(getXcoord());
        output.writeInt(getYcoord());
        output.writeDouble(getSpeed());
        output.writeDouble(getDamage());
        output.writeInt(roomXCoord);
        output.writeInt(roomYCoord);
        output.writeInt(floor);
    }
}
