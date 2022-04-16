import java.io.IOException;
import javafx.scene.layout.BackgroundImage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

public class GameWindow {
    
    @FXML
    Pane Gamepane;

    @FXML
    VBox MasterVbox;

    private Game game;

    private difficulties diff;

    private characters character;

    private player player;

    final Image enemies = new Image("/imgs/microwave2.gif");

    final Image pizza = new Image("/imgs/pizza.png");

    //initializes the view by calling the necesary methods
    public void initialize(difficulties setDiff, characters setCharacter){

        diff = setDiff;
        character = setCharacter;
        
        game = new Game(diff, character);
        game.generateGame();
        generate();
        tickProcessing();
    }

    @FXML
    public void generate(){
        int roomIndex = game.getCurrentRoom();
        room room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);
        for (int i = 0; i < room.getEnemyList().size(); ++i){
            makeImage(enemies, room.getEnemyList().get(i));
        }
        player = new player(25, 0, 1, 69, 0, 300);
        makeImage(pizza, player);
    }

    //updates the view based on changes in the model
    @FXML
    public void updatePositions(ActionEvent e){
        var ls = game.getLevelSet().get(game.getCurrentLevel()).getRooms().get(game.getCurrentRoom()).getEnemyList();
        int len = ls.size();
        for (int i = 0; i < len; ++i){
            ls.get(i).updatePosition();
            Gamepane.getChildren().get(i).setLayoutX(ls.get(i).getXcoord());
            Gamepane.getChildren().get(i).setLayoutY(ls.get(i).getYcoord());
        }
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
    public void move(KeyEvent k){
        String dir = k.getCharacter();
        switch(dir){
            case "w":
                player.setDirection(1);
                player.setSpeed(0);

        }
        
    }

    //sets the cursor to crosshairs and tracks it on the pane
    @FXML
    public void trackCursor(){
        throw new RuntimeException("Method not implemented");
    }

    @FXML
    void onPauseClicked(ActionEvent event) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("PauseMenu.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Pause Menu");
    }

    @FXML
    public void tickProcessing(){
        //calls updateView(), and trackCursor() every tick, and every time the player moves or shoots.
        //calls updatePosition() on all moving entities in the current loaded room each tick.
        KeyFrame kf = new KeyFrame(Duration.millis(100), this::updatePositions);
        var timer = new Timeline(kf);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    // This method is called to call the load method in the game object
    public void load() {
        game = new Game(difficulties.MEDIUM, characters.HPOCKET); //TODO: Add difficulty and character to save/load
        game.load();
    }

    //method for generating images in the Game pane
    ImageView makeImage(Image pic, entity e){
        var img = new ImageView(pic);
        Gamepane.getChildren().add(img);
        Gamepane.getChildren().get(Gamepane.getChildren().size() - 1).setLayoutX(e.getXcoord());
        Gamepane.getChildren().get(Gamepane.getChildren().size() - 1).setLayoutY(e.getYcoord());

        return img;
    }

    // Added for implementing save function with pause window
    public Game getGame() {
        return game;
    }

}
