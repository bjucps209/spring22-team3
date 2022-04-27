//-----------------------------------------------------------
//File:   RoomView.java
//Desc:   This file makes the room view.
//The room view is showed to the user with a red square if the player is viewing the room.
//Otherwise, the room would be shown as a green square.
//----------------------------------------------------------- 
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import model.LevelData;
import model.room;
// This is the class that holds the RoomView.
public class RoomView {
    @FXML
    GridPane grid; // The grid pane that holds the room in a grid.

    @FXML
    void initialize(LevelData levelData, room currentRoom) {
        // add background color to all tiles
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(levelData.findRoom(i, j) != null) {
                    Button b = new Button();
                    b.setPrefSize(65, 45);
                    b.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
                    grid.add(b, i, 4-j);
                    
                    
                }
                //if highlight the current room
                if(currentRoom.getX() == i && currentRoom.getY() == j) {
                    Button b = new Button();
                    b.setPrefSize(65, 45);
                    b.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
                    grid.add(b, i, 4-j);
                }
               
            }
        }
          
            
        
     
   

    }

}
