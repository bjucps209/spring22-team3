package model;

import java.io.DataInputStream;
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


    public void updatePosition(){
        Xcoord += speed * Math.cos(direction * Math.PI / 180);
        Ycoord += speed * Math.sin(direction * Math.PI / 180);
    }


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
        output.writeInt(getHealth());
        output.writeDouble(getSpeed());
        output.writeDouble(getDamage());
        output.writeDouble(getId());
        output.writeInt(getXcoord());
        output.writeInt(getYcoord());
        output.writeInt(roomXCoord);
        output.writeInt(roomYCoord);
        output.writeInt(floor);
    }

    // Factory Method that builds/loads a player based off a DataInputStream
    public static player load(DataInputStream input) throws IOException {
        player output = new player(input.readInt(), input.readInt(), input.readInt(), input.readInt(), input.readInt(), input.readInt());
        output.setRoomXCoord(input.readInt());
        output.setRoomYCoord(input.readInt());
        output.setFloor(input.readInt());
        return output;
    }
}
