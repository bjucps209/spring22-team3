package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class obstacle extends entity {


    public obstacle(int setHealth, double setSpeed, double setDamage, int setId, int xcoord,
            int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
    }

    @Override
    // Given an OutputStream, this method saves the obstacle's attributes 
    public void save(DataOutputStream output) throws IOException {
        output.writeInt(getXcoord());
        output.writeInt(getYcoord());
    }
}
