import java.io.IOException;
import javafx.scene.layout.BackgroundImage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

public class GameWindow {
    
    @FXML
    Pane GamePane;

    @FXML
    VBox MasterVbox;

    private Game game;

    private difficulties diff;

    private characters character;

    final Image enemies = new Image("/imgs/microwave.gif");

    //initializes the view by calling the necesary methods
    public void initialize(difficulties setDiff, characters setCharacter){

        diff = setDiff;
        character = setCharacter;
        
        game = new Game(diff, character);
        generate();
    }

    @FXML
    public void generate(){
        int roomIndex = game.getCurrentRoom();
        room room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);
        for (int i = 0; i < room.getEnemyList().size() - 1; ++i){
            makeImage(enemies, room.getEnemyList().get(i));
            
            
        }
    }

    //updates the view based on changes in the model
    @FXML
    public void updatePositions(){
        throw new RuntimeException("Method not implemented");
    }

    //updates entities when a collision is detected
    @FXML
    public void findCollision(){
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
    @FXML
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

    public void tickProcessing(){
        //calls updateView(), and trackCursor() every tick, and every time the player moves or shoots.
        //calls updatePosition() on all moving entities in the current loaded room each tick.
    }

    // This method is called to call the load method in the game object
    public void load() {
        game.load();
    }

    //method for generating images in the Game pane
    void makeImage(Image pic, entity e){
        var img = new ImageView(pic);
        img.setTranslateX(e.getXcoord());
        img.setTranslateY(e.getYcoord());
        GamePane.getChildren().add(img);
    }

}
