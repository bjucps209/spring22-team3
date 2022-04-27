package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class player extends entity {

    double shield, fireCooldown;

    public player(double setHealth, double setSpeed, double setDamage, int setId, int xcoord, int ycoord, double shield, double fireCooldown) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        this.shield = shield;
        this.fireCooldown = fireCooldown;
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

    public double getShield() {
        return shield;
    }

    public void setShield(double shield) {
        this.shield = shield;
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

    public double getFireCooldown() {
        return fireCooldown;
    }

    public void setFireCooldown(double fireCooldown) {
        this.fireCooldown = fireCooldown;
    }

    public void setCharacter(characters character) {
        Character = character;
    }

    public void upgradeFireRate() {
        fireCooldown = fireCooldown * 0.7;
    }

    public void upgradeShield() {
        shield += 5;
    }

    public void upgradeSpeed() {
        speed += 1.25;
    }

    // Given an OutputStream, this method saves the player's attributes 
    @Override
    public void save(PrintWriter output) throws IOException {
        output.println(getHealth());
        output.println(getSpeed());
        output.println(getDamage());
        output.println(getId());
        output.println(getXcoord());
        output.println(getYcoord());
        output.println(getShield());
        output.println(getFireCooldown());
        output.println(roomXCoord);
        output.println(roomYCoord);
        output.println(floor);
    }

    // Factory Method that builds/loads a player based off a DataInputStream
    public static player load(BufferedReader input) throws IOException {
        player output = new player(Double.parseDouble(input.readLine()), Double.parseDouble(input.readLine()), Double.parseDouble(input.readLine()), Integer.parseInt(input.readLine()), Integer.parseInt(input.readLine()), Integer.parseInt(input.readLine()), Double.parseDouble(input.readLine()), Double.parseDouble(input.readLine()));
        output.setRoomXCoord(Integer.parseInt(input.readLine()));
        output.setRoomYCoord(Integer.parseInt(input.readLine()));
        output.setFloor(Integer.parseInt(input.readLine()));
        return output;
    }
}
