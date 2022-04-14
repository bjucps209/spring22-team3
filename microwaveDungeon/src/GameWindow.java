import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;

public class GameWindow {
    
    @FXML
    Pane GamePane;

    private Game game;

    private difficulties diff;

    private characters character;

    //initializes the view by calling the necesary methods
    public void initialize(difficulties setDiff, characters setCharacter){

        diff = setDiff;
        character = setCharacter;
        
        game = new Game(diff, character);
    }

    @FXML
    public void generate(){
        throw new RuntimeException("Method not implemented");
    }

    //updates the view based on changes in the model
    @FXML
    public void update(){
        throw new RuntimeException("Method not implemented");
    }

    //fires at enemies when the mouse is clicked
    @FXML
    public void openFire(){
        throw new RuntimeException("Method not implemented");
    }

    //moves the player character when WASD is pressed
    @FXML
    public void move(){
        throw new RuntimeException("Method not implemented");
    }

    //sets the cursor to crosshairs and tracks it on the pane
    public void trackCursor(){
        throw new RuntimeException("Method not implemented");
    }

    void onPauseClicked(ActionEvent event) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("PauseMenu.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Pause Menu");
    }

    // This method is called to call the load method in the game object
    public void load() {
        game.load();
    }

}
