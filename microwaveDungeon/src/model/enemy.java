package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class enemy extends entity {
    
    public enemy(int setHealth, double setSpeed, double setDamage, int setId, int xcoord, int ycoord) {
        super(setHealth, setSpeed, setDamage, setId, xcoord, ycoord);
        //TODO Auto-generated constructor stub
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
 