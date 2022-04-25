import java.io.File;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javax.sound.sampled.*;
import javafx.scene.input.*;
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
    ProgressBar healthBar, shieldBar;

    @FXML
    ProgressIndicator primaryIndicator, abilityIndicator;

    @FXML
    Label scoreLbl, timeLbl;

    private Game game;

    boolean isNotPaused = true; // Value that if false when the pause window is open

    private difficulties diff;

    private characters character;

    private player player;

    private boolean goNorth, goEast, goSouth, goWest;

    private boolean playerModelFlipped = false; // For knowing when to flip the player model img when moving

    private boolean playerIsMoving = false; // true when a movement key is pressed, false when a movement key is not pressed

    private double cursorX;

    private double cursorY;

    private double gunFireCooldown = 0.0; // Time left until player can fire primary again

    private double abilityCooldown = 0.0; // Time left until player can use ability again

    private double abilityTime = 1.0;

    private room room;

    private int enemyCount;
    private int doorCount;

    private Thread moveThread;

    private Thread cooldownThread;

    private KeyFrame kf = new KeyFrame(Duration.millis(10), this::updatePlayer);
    private Timeline timer = new Timeline(kf);

    final Image enemies = new Image("/imgs/microwave2.gif");

    final Image pizza = new Image("/imgs/pizza2.png");

    final Image mac = new Image("/imgs/mac2.png");

    final Image hPocket = new Image("/imgs/hp2.png");

    final Image ramen = new Image("/imgs/ramen2.png");

    final Image bullet = new Image("/imgs/IAMALSOBULLET.png");

    final Image door = new Image("/imgs/door2.png");

    Image playerImage;

    // initializes the view by calling the necesary methods
    public void initialize(difficulties setDiff, characters setCharacter) {
        diff = setDiff;
        character = setCharacter;
        game = new Game(diff, character);
        int roomIndex = game.getCurrentRoom();
        room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);
        generate();
        tickProcessing();
        setmovement();

        enemyCount = room.getEnemyList().size();
        doorCount = room.getDoorList().size();

        cooldownThread = new Thread(() -> {
            var keyframe = new KeyFrame(Duration.millis(50), this::updateCooldowns);
            var timer = new Timeline(keyframe);
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();
        });
        cooldownThread.start();
    }

    void updateCooldowns(ActionEvent e) {
        if (gunFireCooldown > 0)
            gunFireCooldown -= 0.05;
        if (abilityCooldown > 0)
            abilityCooldown -= 0.05;
    }

    @FXML
    public void generate() {
        // on generation, player is always slot 0 on the Gamepane, then enemies, then doors, everything beyond that is projectiles

        player = new player(25, 3, 1, 69, 0, 300, 0);
        game.setUser(player);

        switch (character) {

            case PIZZA:
                playerImage = pizza;
                makeImage(pizza, player);
                abilityTime = 20;
                break;

            case MAC:
                playerImage = mac;
                makeImage(mac, player);
                abilityTime = 40;
                break;

            case RAMEN:
                playerImage = ramen;
                makeImage(ramen, player);
                abilityTime = 10;
                break;

            case HPOCKET:
                playerImage = hPocket;
                makeImage(hPocket, player);
                abilityTime = 2.5;
                break;

        }

        for (int i = 0; i < room.getEnemyList().size(); ++i) {
            makeImage(enemies, room.getEnemyList().get(i));
        }

        for (int j = 0; j < room.getDoorList().size(); ++j) {
            makeImage(door, room.getDoorList().get(j));
        }

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

            KeyFrame collisionkf = new KeyFrame(Duration.millis(300), this::onHit);
            var collisionTimer = new Timeline(collisionkf);
            collisionTimer.setCycleCount(Timeline.INDEFINITE);
            collisionTimer.play();

            KeyFrame doorkf = new KeyFrame(Duration.millis(100), this::onDoor);
            var doorTimer = new Timeline(doorkf);
            doorTimer.setCycleCount(Timeline.INDEFINITE);
            doorTimer.play();

        });
        t.start();
    }

    public void setmovement() {

        // Scene scene = new Scene(Gamepane, Gamepane.widthProperty().get(),
        // Gamepane.heightProperty().get());
        Scene scene = Gamepane.getScene();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        playerIsMoving = true;
                        goNorth = true;
                        break;
                    case S:
                        playerIsMoving = true;
                        goSouth = true;
                        break;
                    case A:
                        playerIsMoving = true;
                        goWest = true;
                        playerModelFlipped = true;
                        break;
                    case D:
                        playerIsMoving = true;
                        goEast = true;
                        playerModelFlipped = false;
                        break;
                    case SHIFT:
                        switch(character) {
                            case HPOCKET:
                                dash();
                                break;
                            case PIZZA:
                                pepShield();
                                break;
                            case RAMEN:
                                // TODO: add homing missle
                                break;
                            case MAC:
                                // TODO: add railgun
                                break;
                        }
                        break; // TODO: Special ability - added basic dash
                    case C: 
                        onCheatClicked(new ActionEvent()); 
                        break;
                    default:
                        break;
                }
                UpdateMove();
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        goNorth = false;
                        break;
                    case S:
                        goSouth = false;
                        break;
                    case A:
                        goWest = false;
                        break;
                    case D:
                        goEast = false;
                        break;
                    case ESCAPE:
                        try {
                            onPauseClicked(new ActionEvent());
                        } catch (IOException e) {
                            Alert a = new Alert(AlertType.ERROR,
                                    "There was a problem with opening the pause menu: " + e.getMessage());
                            a.show();
                        }
                        ;
                        break;
                    default:
                        break;
                }
                timer.stop();
                if (!goNorth && !goSouth && !goWest && !goEast) {
                    playerIsMoving = false;
                } else {
                    UpdateMove();
                }

            }
        });
    }

    // moves the player character when WASD is pressed
    public void UpdateMove() {

        boolean moving = goNorth || goSouth || goWest || goEast;

        double direction = 0;

        if (moving) {
            if (goNorth) {
                direction = 270;
                if (goEast) {
                    direction += 45;
                } else if (goWest) {
                    direction -= 45;
                }
            } else if (goSouth) {
                direction = 90;
                if (goEast) {
                    direction -= 45;
                } else if (goWest) {
                    direction += 45;
                }
            } else if (goWest) {
                direction = 180;
            } else if (goEast) {
                direction = 0;
            }

            if (playerModelFlipped)
                Gamepane.getChildren().get(0).setScaleX(-1);
            else
                Gamepane.getChildren().get(0).setScaleX(1);

            player.setDirection(direction);
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();

        }

    }

    @FXML
    public void setCursor(MouseEvent m) {
        Gamepane.getScene().setCursor(Cursor.CROSSHAIR);
    }

    // updates the view based on changes in the model
    @FXML
    public void updateEnemyPositions(ActionEvent e) {

        if (isNotPaused) {
            var ls = room.getEnemyList();
            int len = ls.size();
            for (int i = 0; i < len; ++i) {
                final int currentI = i;
                ls.get(i).updatePosition(Gamepane.getChildren().get(0).getLayoutX(),
                        Gamepane.getChildren().get(0).getLayoutY());
                Platform.runLater(() -> {
                    if (room.getEnemyList().size() != 0) {
                        Gamepane.getChildren().get(currentI + 1).setLayoutX(ls.get(currentI).getXcoord());
                        Gamepane.getChildren().get(currentI + 1).setLayoutY(ls.get(currentI).getYcoord());
                    }

                });

            }
            Platform.runLater(() -> {
                if(player.getHealth() > 24.9) // Update health, score, & time labels
                    healthBar.setProgress(1.0);
                else
                    healthBar.setProgress(player.getHealth()/25);
                if(healthBar.getProgress() < 0.5) {
                    healthBar.setStyle("-fx-accent: orange;");
                    if(healthBar.getProgress() < 0.25)
                        healthBar.setStyle("-fx-accent: red;"); }
                shieldBar.setProgress(player.getShield() / 10);
                scoreLbl.setText("Score: " + game.getScore());
                int timeLeft = 600 - game.getTimePassed();
                if (timeLeft < 0)
                    timeLeft = 0;
                timeLbl.setText("Time: " + String.valueOf(timeLeft)); // 600 second countdown for scoring purposes
                primaryIndicator.setProgress(1 - (gunFireCooldown / 1));
                abilityIndicator.setProgress(1 - (abilityCooldown / abilityTime));
            });
        }

    }

    // updates entities when collision from a bullet is detected
    @FXML
    public void onHit(ActionEvent e) {
        int enemies = room.getEnemyList().size();
        int doors = room.getDoorList().size();

        for (int i = 1 + enemies + doors; i < Gamepane.getChildren().size(); ++i) { // i = bullet index, j = enemy
                                                                                    // index, player
            // index = 0
            for (int j = 1; j <= enemies; ++j) {
                Double bulletX = Gamepane.getChildren().get(i).getLayoutX();
                Double bulletY = Gamepane.getChildren().get(i).getLayoutY();
                Double entityX = Gamepane.getChildren().get(j).getLayoutX();
                Double entityY = Gamepane.getChildren().get(j).getLayoutY();
                boolean isCollision = (Math
                        .abs(Math.sqrt(Math.pow(bulletX - entityX, 2) + Math.pow(bulletY - entityY, 2))) <= 50.0);
                if (isCollision) {
                    room.getEnemyList().get(j - 1).setHealth(room.getEnemyList().get(j - 1).getHealth() - player.getDamage());
                    if (room.getEnemyList().get(j - 1).getHealth() <= 0) {
                        if (room.getEnemyList().size() != 0) {
                            Gamepane.getChildren().remove(i);
                            Gamepane.getChildren().remove(j);
                            room.getBulletList().remove(i - enemies - 1 - doors);
                            room.getEnemyList().remove(j - 1);
                        }
                    }

                }
            }
        }
    }

    @FXML
    public void onDoor(ActionEvent e) {

        for (int i = 1 + enemyCount; i < 1 + enemyCount + doorCount; ++i) {
            double playerX = Gamepane.getChildren().get(0).getLayoutX();
            double playerY = Gamepane.getChildren().get(0).getLayoutY();
            double doorX = Gamepane.getChildren().get(i).getLayoutX();
            double doorY = Gamepane.getChildren().get(i).getLayoutY();
            boolean isCollision = (Math
                    .abs(Math.sqrt(Math.pow(playerX - doorX, 2) + Math.pow(playerY - doorY, 2))) <= 100.0);
            if (isCollision) {
                System.out.println("check");
                directions d = room.getDoorList().get(i - 1 - enemyCount).getDir();
                if (d == null){
                    return;
                }

                switch(d){

                    case North:
                        game.setCurrentRoom(1);
                        int roomIndex = game.getCurrentRoom();
                        room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);
                        player.setXcoord(0);
                        player.setYcoord(300);
                        Gamepane.getChildren().clear();
                        makeImage(playerImage, player);
                        enemyCount = room.getEnemyList().size();
                        doorCount = room.getDoorList().size();

                        for (int k = 0; k < room.getEnemyList().size(); ++k) {
                            makeImage(enemies, room.getEnemyList().get(k));
                        }
                
                        for (int j = 0; j < room.getDoorList().size(); ++j) {
                            makeImage(door, room.getDoorList().get(j));
                        }
                        
                        break;

                    case South:

                        game.setCurrentRoom(1);
                        roomIndex = game.getCurrentRoom();
                        room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);
                        player.setXcoord(0);
                        player.setYcoord(300);
                        Gamepane.getChildren().clear();
                        makeImage(playerImage, player);
                        enemyCount = room.getEnemyList().size();
                        doorCount = room.getDoorList().size();

                        for (int k = 0; k < room.getEnemyList().size(); ++k) {
                            makeImage(enemies, room.getEnemyList().get(k));
                        }
                
                        for (int j = 0; j < room.getDoorList().size(); ++j) {
                            makeImage(door, room.getDoorList().get(j));
                        }
                        break;

                    case East:

                        game.setCurrentRoom(1);
                        roomIndex = game.getCurrentRoom();
                        room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);
                        player.setXcoord(0);
                        player.setYcoord(300);
                        Gamepane.getChildren().clear();
                        makeImage(playerImage, player);
                        enemyCount = room.getEnemyList().size();
                        doorCount = room.getDoorList().size();

                        for (int k = 0; k < room.getEnemyList().size(); ++k) {
                            makeImage(enemies, room.getEnemyList().get(k));
                        }
                
                        for (int j = 0; j < room.getDoorList().size(); ++j) {
                            makeImage(door, room.getDoorList().get(j));
                        }
                        break;

                    case West:

                        game.setCurrentRoom(1);
                        roomIndex = game.getCurrentRoom();
                        room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);
                        player.setXcoord(0);
                        player.setYcoord(300);
                        Gamepane.getChildren().clear();
                        makeImage(playerImage, player);
                        enemyCount = room.getEnemyList().size();
                        doorCount = room.getDoorList().size();

                        for (int k = 0; k < room.getEnemyList().size(); ++k) {
                            makeImage(enemies, room.getEnemyList().get(k));
                        }
                
                        for (int j = 0; j < room.getDoorList().size(); ++j) {
                            makeImage(door, room.getDoorList().get(j));
                        }
                        break;

                }
            }
        }
    }

    // fires at enemies when the mouse is clicked
    @FXML
    public void openFire(MouseEvent e) {
        if (gunFireCooldown <= 0.0) {
            GameWindow.playAudio("src\\audio\\gunSound.wav"); // Plays gunfire sound effect

            cursorX = e.getX();
            cursorY = e.getY();

            int roomIndex = game.getCurrentRoom();
            room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);
            room.getBulletList().add(new projectile(1000, 10, 1, 5, player.getXcoord(), player.getYcoord()));
            room.getBulletList().get(room.getBulletList().size() - 1).setDirection(cursorX, cursorY);
            makeImage(bullet, room.getBulletList().get(room.getBulletList().size() - 1));
            KeyFrame kf = new KeyFrame(Duration.millis(100), this::movebullet);
            var timer = new Timeline(kf);
            timer.setCycleCount(100);
            timer.play();
            gunFireCooldown = 1; // TODO: Gunfire rate value
        }
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

    @FXML
    public void updatePlayer(ActionEvent e) {
        if(playerIsMoving) {
            player.updatePosition();
            Gamepane.getChildren().get(0).setLayoutX(player.getXcoord());
            Gamepane.getChildren().get(0).setLayoutY(player.getYcoord());
        }
    }

    // Pauses the game and opens the Pause Menu
    @FXML
    void onPauseClicked(ActionEvent event) throws IOException {
        TitleWindow.beep();
        isNotPaused = false;
        var loader = new FXMLLoader(getClass().getResource("PauseMenu.fxml"));
        var scene = new Scene(loader.load());
        Stage stage = new Stage();
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() { // Unpauses the game when ESC is released
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ESCAPE:
                        stage.close();
                        resume();
                        break;
                    default:
                        break;
                }
            }
        });
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Pause Menu");

    }

    // This method is called to call the load method in the game object and initialize the newly loaded game
    public void load() {
        game = game.load(false);
        int roomIndex = game.getCurrentRoom();
        room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);
        player = game.getUser();
        character = game.getCharacter();
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
        tickProcessing();
        Gamepane.requestFocus();
        setmovement();

        cooldownThread = new Thread(() -> {
            var keyframe = new KeyFrame(Duration.seconds(1), this::updateCooldowns);
            var timer = new Timeline(keyframe);
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();
        });
        cooldownThread.start();
    }

    // method for generating images in the Game pane
    ImageView makeImage(Image pic, entity e) {
        var img = new ImageView(pic);
        img.setUserData(e);
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

    // Unpauses the game when called
    public void resume() {
        isNotPaused = true;
        Gamepane.requestFocus();
    }

    // Adds 10,000 health to the player when "Cheat" is clicked
    public void onCheatClicked(ActionEvent e) {
        TitleWindow.beep();
        player.setHealth(player.getHealth() + 10000);
        Gamepane.requestFocus();
    }

    // Uses an audio filepath string and plays it -Note, the file must be in the .wav format
    public static Clip playAudio(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            return clip;
        } catch (Exception e) {
            Alert a = new Alert(AlertType.ERROR, "There was a problem with playing audio: " + e.getMessage());
            a.show();
            return null;
        }
    }

    public void dash() {
        if (abilityCooldown <= 0.0) {
            player.setSpeed((int) player.getSpeed() + 8);
            var keyframe = new KeyFrame(Duration.millis(180), e -> {
                player.setSpeed((int) player.getSpeed() - 8);
            });
            var DashTimer = new Timeline(keyframe);
            DashTimer.play();
            abilityCooldown = 2.5;
        }
    }

    public void pepShield() {
        if (abilityCooldown <= 0.0) {
            player.setShield(10);
            KeyFrame keyframe = new KeyFrame(Duration.seconds(1), e -> {
                if(player.getShield() > 0.0)
                    player.setShield(player.getShield() - 1.0);
            });
            Timeline timer = new Timeline(keyframe);
            timer.setCycleCount(10);
            timer.play();
            abilityCooldown = 20.0;
        }
    }
}
