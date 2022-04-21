import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.layout.BackgroundImage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

public class GameWindow {

    @FXML
    Pane Gamepane;

    @FXML
    VBox MasterVbox;

    @FXML
    Label healthLbl, scoreLbl, timeLbl;

    private Game game;

    private difficulties diff;

    private characters character;

    private player player;

    private Thread moveThread;

    private KeyFrame kf = new KeyFrame(Duration.millis(10), this::updatePlayer);

    private Timeline timer = new Timeline(kf);

    final Image enemies = new Image("/imgs/microwave2.gif");

    final Image pizza = new Image("/imgs/pizza2.png");

    final Image mac = new Image("/imgs/mac2.png");

    final Image hPocket = new Image("/imgs/hp2.png");

    final Image ramen = new Image("/imgs/ramen2.png");

    final Image bullet = new Image("/imgs/IAMALSOBULLET.png");

    // initializes the view by calling the necesary methods
    public void initialize(difficulties setDiff, characters setCharacter) {

        diff = setDiff;
        character = setCharacter;

        game = new Game(diff, character);
        generate();
        tickProcessing();
        Gamepane.requestFocus();
    }

    @FXML
    public void generate() {
        int roomIndex = game.getCurrentRoom();
        room room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);

        player = new player(25, 0, 1, 69, 0, 300);
        game.setUser(player);

        switch (character) {

            case PIZZA:
                makeImage(pizza, player);
                break;

            case MAC:
                makeImage(mac, player);
                break;

            case RAMEN:
                makeImage(ramen, player);
                break;

            case HPOCKET:
                makeImage(hPocket, player);
                break;

        }

        for (int i = 0; i < room.getEnemyList().size(); ++i) {
            makeImage(enemies, room.getEnemyList().get(i));
        }

    }

    @FXML
    public void setCursor(MouseEvent m) {
        Gamepane.getScene().setCursor(Cursor.CROSSHAIR);
    }

    // updates the view based on changes in the model
    @FXML
    public void updateEnemyPositions(ActionEvent e) {
        var ls = game.getLevelSet().get(game.getCurrentLevel()).getRooms().get(game.getCurrentRoom()).getEnemyList();
        int len = ls.size();
        for (int i = 0; i < len; ++i) {
            final int currentI = i;
            ls.get(i).updatePosition(Gamepane.getChildren().get(0).getLayoutX(),Gamepane.getChildren().get(0).getLayoutY());
            Platform.runLater(() -> {
                Gamepane.getChildren().get(currentI + 1).setLayoutX(ls.get(currentI).getXcoord());
                Gamepane.getChildren().get(currentI + 1).setLayoutY(ls.get(currentI).getYcoord());
            });

        }
        Platform.runLater(() -> {
            healthLbl.setText("Health: " + player.getHealth()); // Update health, score, & time labels
            scoreLbl.setText("Score: " + game.getScore());
            timeLbl.setText("Time: " + game.getTimePassed());
        });

    }

    // updates entities when a collision is detected
    @FXML
    public void findCollision(ActionEvent e) {
        int enemies = game.getLevelSet().get(game.getCurrentLevel()).getRooms().get(game.getCurrentRoom()).getEnemyList().size();

        
        for (int i = 1 + enemies; i < Gamepane.getChildren().size(); ++i){
            for (int j = 1; j < enemies; ++j){
                boolean xAxis = (Gamepane.getChildren().get(i).getLayoutX() == Gamepane.getChildren().get(j).getLayoutX());
                boolean yAxis = (Gamepane.getChildren().get(i).getLayoutY() == Gamepane.getChildren().get(j).getLayoutY());
                if (xAxis && yAxis){
                    Gamepane.getChildren().remove(i);
                    Gamepane.getChildren().remove(j);
                }
            }
        }
    }

    // fires at enemies when the mouse is clicked
    @FXML
    public void openFire(MouseEvent e) {
        int roomIndex = game.getCurrentRoom();
        room room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);
        room.getBulletList().add(new projectile(1000, 15, 1, 5, player.getXcoord(), player.getYcoord()));
        makeImage(bullet, room.getBulletList().get(room.getBulletList().size() - 1)); 
        KeyFrame kf = new KeyFrame(Duration.millis(100), this::movebullet);
        var timer = new Timeline(kf);
        timer.setCycleCount(100);
        timer.play();

    }

    @FXML
    public void movebullet(ActionEvent e) {
        int roomIndex = game.getCurrentRoom();
        room room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);

        for (int i = 0; i < room.getBulletList().size(); ++i) {
            projectile p = room.getBulletList().get(i);
            p.updatePosition();
            Gamepane.getChildren().get(Gamepane.getChildren().size() - i - 1).setLayoutX(p.getXcoord());
            Gamepane.getChildren().get(Gamepane.getChildren().size() - i - 1).setLayoutY(p.getYcoord());

        }

    }

    // moves the player character when WASD is pressed
    @FXML
    public void move(KeyEvent k) {
        KeyCode dir = k.getCode();

        switch (dir) {

            case W:
                player.setDirection(270);
                player.setSpeed(5);
                timer.setCycleCount(Timeline.INDEFINITE);
                timer.play();
                break;

            case A:
                player.setDirection(180);
                player.setSpeed(5);
                timer.setCycleCount(Timeline.INDEFINITE);
                timer.play();
                break;

            case S:
                player.setDirection(90);
                player.setSpeed(5);
                timer.setCycleCount(Timeline.INDEFINITE);
                timer.play();
                break;

            case D:
                player.setDirection(360);
                player.setSpeed(5);
                timer.setCycleCount(Timeline.INDEFINITE);
                timer.play();
                break;

        }

    }

    @FXML
    public void stopMove(KeyEvent k) {
        player.setDirection(0);
        player.setSpeed(0);
        timer.stop();
    }

    @FXML
    public void updatePlayer(ActionEvent e) {
        player.updatePosition();
        Gamepane.getChildren().get(0).setLayoutX(player.getXcoord());
        Gamepane.getChildren().get(0).setLayoutY(player.getYcoord());

    }

    // sets the cursor to crosshairs and tracks it on the pane
    @FXML
    public void trackCursor() {
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
    public void tickProcessing() {
        // calls updateView(), and trackCursor() every tick, and every time the player
        // moves or shoots.
        // calls updatePosition() on all moving entities in the current loaded room each
        // tick.
        Thread t = new Thread(() -> {
            KeyFrame enemykf = new KeyFrame(Duration.millis(100), this::updateEnemyPositions);
            var enemytimer = new Timeline(enemykf);
            enemytimer.setCycleCount(Timeline.INDEFINITE);
            enemytimer.play();

            KeyFrame collisionkf = new KeyFrame(Duration.millis(100), this::findCollision);
            var collisionTimer = new Timeline(collisionkf);
            collisionTimer.setCycleCount(Timeline.INDEFINITE);
            collisionTimer.play();
        });
        t.start();
    }

    // This method is called to call the load method in the game object
    public void load() {
        game = game.load();
        try (DataInputStream input = new DataInputStream(new FileInputStream("src\\Saves\\SavedGame.txt"))) {
            player = player.load(input);
            game.setUser(player);
        } catch (IOException e) {
            e.printStackTrace();
            Alert a = new Alert(AlertType.ERROR, "There was a problem reading the save file: " + e.getMessage());
            a.show();
        }
        character = game.getCharacter();
        switch (character){
            case PIZZA:
                makeImage(pizza, player);
                break;

            case MAC:
                makeImage(mac, player);
                break;

            case RAMEN:
                makeImage(ramen, player);
                break;

            case HPOCKET:
                makeImage(hPocket, player);
                break;
        }
        int roomIndex = game.getCurrentRoom();
        room room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);
        for (int i = 0; i < room.getEnemyList().size(); ++i){
            makeImage(enemies, room.getEnemyList().get(i));
        }
        tickProcessing();
        Gamepane.requestFocus();
    }

    // method for generating images in the Game pane
    ImageView makeImage(Image pic, entity e) {
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

    public player getPlayer() {
        return player;
    }
}
