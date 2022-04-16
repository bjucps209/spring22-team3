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
import javafx.scene.input.KeyCode;
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

    private KeyFrame kf = new KeyFrame(Duration.millis(10), this::updatePlayer);

    private Timeline timer = new Timeline(kf);

    final Image enemies = new Image("/imgs/microwave2.gif");

    final Image pizza = new Image("/imgs/pizza2.png");

    //initializes the view by calling the necesary methods
    public void initialize(difficulties setDiff, characters setCharacter){

        diff = setDiff;
        character = setCharacter;
        
        game = new Game(diff, character);
        game.generateGame();
        generate();
        tickProcessing();
        Gamepane.requestFocus();
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
    public void updateEnemyPositions(ActionEvent e){
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
        KeyCode dir = k.getCode();
 
        switch (dir) {

            case W :
                player.setDirection(270);
                player.setSpeed(5);
                timer.setCycleCount(Timeline.INDEFINITE);
                timer.play();
                break;

            case A :
                player.setDirection(180);
                player.setSpeed(5);
                timer.setCycleCount(Timeline.INDEFINITE);
                timer.play();
                break;

            case S :
                player.setDirection(90);
                player.setSpeed(5);
                timer.setCycleCount(Timeline.INDEFINITE);
                timer.play();
                break;

            case D :
                player.setDirection(360);
                player.setSpeed(5);
                timer.setCycleCount(Timeline.INDEFINITE);
                timer.play();
                break;

        }
        
        
    
    }

    @FXML
    public void stopMove(KeyEvent k){
        player.setDirection(0);
                player.setSpeed(0);
                timer.stop();
    }

    @FXML
    public void updatePlayer(ActionEvent e){
        player.updatePosition();  
        Gamepane.getChildren().get(Gamepane.getChildren().size() - 1).setLayoutX(player.getXcoord());
        Gamepane.getChildren().get(Gamepane.getChildren().size() - 1).setLayoutY(player.getYcoord());
        
            
        
        

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
        KeyFrame kf = new KeyFrame(Duration.millis(100), this::updateEnemyPositions);
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
        Gamepane.getChildren().get(Gamepane.getChildren().size() - 1).setUserData(e.getClass());
        return img;
    }

    // Added for implementing save function with pause window
    public Game getGame() {
        return game;
    }

}
