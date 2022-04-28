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
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;

public class GameWindow {

    @FXML
    Pane Gamepane; //the pane that holds the game

    @FXML
    VBox MasterVbox;

    @FXML
    ProgressBar healthBar, shieldBar, levelBar; //the bars for different stats

    @FXML
    ProgressIndicator primaryIndicator, abilityIndicator; //indicators for the primary and ability bars

    @FXML
    Label scoreLbl, timeLbl, powerUpLbl; //labels for the score, time, and power up

    private Game game; //the game object

    boolean isNotPaused = true; // Value that if false when the pause window is open

    private difficulties diff; //the difficulty of the game

    private characters character; //the character of the game

    private player player; //the player object
    
    private boolean goNorth, goEast, goSouth, goWest; //booleans for the wasd keys

    private boolean playerModelFlipped = false; // For knowing when to flip the player model img when moving

    private boolean playerIsMoving = false; // true when a movement key is pressed, false when a movement key is not
                                            // pressed

    private double cursorX; //the x coordinate of the cursor

    private double cursorY; //the y coordinate of the cursor

    private double gunFireCooldown = 0.0; // Time left until player can fire primary again

    private double abilityCooldown = 0.0; // Time left until player can use ability again

    private double abilityTime = 1.0; 

    private double levelBarCap = 100; // Sets the score needed to get a power-up

    private room room; //the room object

    private int roomIndex; //the index of the room

    private int enemyCount; //the number of enemies in the room

    private int doorCount; //the number of doors in the room

    private Thread cooldownThread; // Thread for cooldowns

    private KeyFrame kf = new KeyFrame(Duration.millis(10), this::updatePlayer);
    private Timeline timer = new Timeline(kf);

    final Image enemies = new Image("/imgs/microwave2.gif");

    final Image pizza = new Image("/imgs/pizza2.png");

    final Image mac = new Image("/imgs/mac2.png");

    final Image hPocket = new Image("/imgs/hp2.png");

    final Image ramen = new Image("/imgs/ramen2.png");

    final Image bullet = new Image("/imgs/roundBullet.png");

    final Image hMissle = new Image("/imgs/homingMissle.png");

    final Image railgun = new Image("/imgs/railGun.png");

    final Image door = new Image("/imgs/door2.png");

    Image playerImage;

    // initializes the view by calling the necesary methods
    public void initialize(difficulties setDiff, characters setCharacter, String name) {
        diff = setDiff;
        character = setCharacter;
        game = new Game(diff, character);
        roomIndex = game.getCurrentRoom();
        room = game.getLevelSet().get(0).getRooms().get(roomIndex);
        generate();
        tickProcessing();
        setmovement();
        player.setName(name);

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
        if (gunFireCooldown > 0 && isNotPaused)
            gunFireCooldown -= 0.05;
        if (abilityCooldown > 0 && isNotPaused)
            abilityCooldown -= 0.05;
    }

    @FXML
    public void generate() {
        // on generation, player is always slot 0 on the Gamepane, then enemies, then
        // doors, everything beyond that is projectiles

        player = new player(25, 3, 1, 69, 0, 400, 0, 1.0);
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

        switch (diff){

            case EASY:


            break;

            case MEDIUM:

            break;

            case HARD:

            break;

            case NUKE:

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

            KeyFrame collisionkf = new KeyFrame(Duration.millis(1), this::onHit);
            var collisionTimer = new Timeline(collisionkf);
            collisionTimer.setCycleCount(Timeline.INDEFINITE);
            collisionTimer.play();

            KeyFrame doorkf = new KeyFrame(Duration.millis(100), this::onDoor);
            var doorTimer = new Timeline(doorkf);
            doorTimer.setCycleCount(Timeline.INDEFINITE);
            doorTimer.play();

            KeyFrame dmgkf = new KeyFrame(Duration.millis(200), event -> {
                try {
                    onDamage(event);
                } catch (IOException | InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
            var dmgTimer = new Timeline(dmgkf);
            dmgTimer.setCycleCount(Timeline.INDEFINITE);
            dmgTimer.play();

        });
        t.start();
    }
    
    public void setmovement() {
        // sets the movement of the player
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
                        switch (character) {
                            case HPOCKET:
                                dash();
                                break;
                            case PIZZA:
                                pepShield();
                                break;
                            case RAMEN:
                                homingMissle();
                                break;
                            case MAC:
                                railGun();
                                break;
                        }
                        break; // TODO: Special ability - added basic dash
                    case C:
                        player.setHealth(2147483647);
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
                            Pause();
                        } catch (IOException e) {
                            Alert a = new Alert(AlertType.ERROR,
                                    "There was a problem with opening the pause menu: " + e.getMessage());
                            a.show();
                        }
                        ;
                        break;
                    case P:
                        if (game.getScore() >= levelBarCap) {
                            TitleWindow.beep();
                            pUpWindow.setPlayer(player);
                            levelBarCap *= 2;
                            isNotPaused = false;
                            game.setNotPaused(false);
                            var loader = new FXMLLoader(getClass().getResource("pUpWindow.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(loader.load());
                            } catch (IOException e) {
                                Alert a = new Alert(AlertType.ERROR,
                                        "There was a problem with opeing the Power-Up menu: " + e.getMessage());
                                a.show();
                            }
                            Stage stage = new Stage(StageStyle.UNDECORATED);
                            stage.setScene(scene);
                            stage.show();
                            stage.setTitle("Power-Up Select");
                        }
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
    //sets the cursor to a crosshair for aiming
    public void setCursor(MouseEvent m) {
        Gamepane.getScene().setCursor(Cursor.CROSSHAIR);
    }

    // updates the view based on changes in the model
    @FXML
    public void updateEnemyPositions(ActionEvent e) {
        onHit(new ActionEvent());
        onDoor(new ActionEvent());
        if (isNotPaused) {
            for (int i = 1; i < room.getEnemyList().size() + 1; ++i) {
                final int currentI = i;
                room.getEnemyList().get(i  - 1).updatePosition(Gamepane.getChildren().get(0).getLayoutX(),
                        Gamepane.getChildren().get(0).getLayoutY());
                Platform.runLater(() -> {
                    if (room.getEnemyList().size() != 0) {
                          
                            
                            Gamepane.getChildren().get(currentI).setLayoutX(room.getEnemyList().get(currentI - 1).getXcoord());
                            Gamepane.getChildren().get(currentI).setLayoutY(room.getEnemyList().get(currentI - 1).getYcoord());
                            
                            
                      
                    }

                });

            }
            Platform.runLater(() -> {
                if (player.getHealth() > 24.9) // Update health, score, & time labels
                    healthBar.setProgress(1.0);
                else
                    healthBar.setProgress(player.getHealth() / 25);
                if (player.getHealth() > 25)
                    healthBar.setStyle("-fx-accent: darkgreen;");
                else if (healthBar.getProgress() < 0.5) {
                    healthBar.setStyle("-fx-accent: orange;");
                    if (healthBar.getProgress() < 0.25)
                        healthBar.setStyle("-fx-accent: red;");
                }
                if(player.getShield() > 0)
                    shieldBar.setProgress(player.getShield() / 10);
                else
                    shieldBar.setProgress(0);
                if (player.getShield() > 10)
                    shieldBar.setStyle("-fx-accent: blue;");
                else
                    shieldBar.setStyle("-fx-accent: cornflowerblue;");
                scoreLbl.setText("Score: " + game.getScore());
                int timeLeft = 600 - game.getTimePassed();
                if (timeLeft < 0)
                    timeLeft = 0;
                timeLbl.setText("Time: " + String.valueOf(timeLeft)); // 600 second countdown for scoring purposes
                primaryIndicator.setProgress(1 - (gunFireCooldown / 1));
                abilityIndicator.setProgress(1 - (abilityCooldown / abilityTime));
                if (primaryIndicator.getProgress() > 0.9)
                    primaryIndicator.setStyle("-fx-accent: green;");
                else {
                    if (primaryIndicator.getProgress() < 0.5)
                        primaryIndicator.setStyle("-fx-accent: red;");
                    else
                        primaryIndicator.setStyle("-fx-accent: orange;");
                }
                if (abilityIndicator.getProgress() > 0.9)
                    abilityIndicator.setStyle("-fx-accent: green;");
                else {
                    if (abilityIndicator.getProgress() < 0.5)
                        abilityIndicator.setStyle("-fx-accent: red;");
                    else
                        abilityIndicator.setStyle("-fx-accent: orange;");
                }
                levelBar.setProgress(game.getScore() / levelBarCap);
                if (game.getScore() >= levelBarCap)
                    powerUpLbl.setText("PRESS P TO SELECT A POWER-UP!");
                else
                    powerUpLbl.setText("");
            });
        }

    }

    // updates entities when collision from a bullet is detected
    @FXML
    public boolean onHit(ActionEvent e) {
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
                boolean isCollision = false;
                 //= (Math
                   //     .abs(Math.sqrt(Math.pow(bulletX - entityX, 2) + Math.pow(bulletY - entityY, 2))) <= 50.0);
                
                
                if(entityX < bulletX + Gamepane.getChildren().get(i).getBoundsInParent().getWidth() &&
                        entityX + Gamepane.getChildren().get(j).getBoundsInParent().getWidth() > bulletX &&
                        entityY < bulletY + Gamepane.getChildren().get(i).getBoundsInParent().getHeight() &&
                        Gamepane.getChildren().get(j).getBoundsInParent().getHeight() + entityY > bulletY) {
                    isCollision = true;
                }
                if (isCollision) {
                    room.getEnemyList().get(j - 1)
                            .setHealth(room.getEnemyList().get(j - 1).getHealth() - player.getDamage());
                    Gamepane.getChildren().remove(i);
                    room.getBulletList().remove(i - enemies - 1 - doors );
                    game.setScore(game.getScore() + 5);
                    if (room.getEnemyList().get(j - 1).getHealth() <= 0) {
                        if (room.getEnemyList().size() != 0) {
                            Gamepane.getChildren().remove(j);
                            room.getEnemyList().remove(j - 1);
                            game.setScore(game.getScore() + 20);
                            
                        }
                    }
                    enemyCount = room.getEnemyList().size();

                    return true;

                }
            }
        }
        return false;
    }

    @FXML
    public void onDoor(ActionEvent e) {

        for (int i = 1 + enemyCount; i < 1 + enemyCount + doorCount; ++i) {
            double playerX = Gamepane.getChildren().get(0).getLayoutX();
            double playerY = Gamepane.getChildren().get(0).getLayoutY();
            double doorX = Gamepane.getChildren().get(i).getLayoutX();
            double doorY = Gamepane.getChildren().get(i).getLayoutY();
            boolean isCollision = false;//(Math
                    //.abs(Math.sqrt(Math.pow(playerX - doorX, 2) + Math.pow(playerY - doorY, 2))) <= 100.0);
            if(doorX < playerX + Gamepane.getChildren().get(0).getBoundsInParent().getWidth() && 
                    doorX + Gamepane.getChildren().get(i).getBoundsInParent().getWidth() > playerX &&
                    doorY < playerY + Gamepane.getChildren().get(0).getBoundsInParent().getHeight() &&
                    Gamepane.getChildren().get(i).getBoundsInParent().getHeight() + doorY > playerY) {
                isCollision = true;
            }
            if (isCollision) {
                directions d = game.getLevelSet().get(0).getRooms().get(game.getCurrentRoom()).getDoorList().get(i - enemyCount - 1).getDir();
                if (d == null) {
                    return;
                }

                switch (d) {

                    case East:
                        Gamepane.getChildren().clear();
                        game.setCurrentRoom(game.getCurrentRoom() + 1);
                        int roomIndex = game.getCurrentRoom();
                        room = game.getLevelSet().get(0).getRooms().get(roomIndex);
                        player.setXcoord(0);
                        player.setYcoord(400);
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
                        Gamepane.getChildren().clear();
                        game.setCurrentRoom(game.getCurrentRoom() - 1);
                        roomIndex = game.getCurrentRoom();
                        room = game.getLevelSet().get(0).getRooms().get(roomIndex);
                        player.setXcoord(650);
                        player.setYcoord(300);
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
            room = game.getLevelSet().get(0).getRooms().get(roomIndex);
            room.getBulletList().add(new projectile(1000, 10, 1, 5, player.getXcoord(), player.getYcoord()));
            room.getBulletList().get(room.getBulletList().size() - 1).setDirection(cursorX, cursorY);
            var bShot = makeImage(bullet, room.getBulletList().get(room.getBulletList().size() - 1));
            
            KeyFrame kf = new KeyFrame(Duration.millis(100), this::movebullet);
            var timer = new Timeline(kf);
            timer.setCycleCount(100);
            timer.play();
            gunFireCooldown = player.getFireCooldown();
        }
    }

    @FXML
    public void movebullet(ActionEvent e) {
        if(isNotPaused) {
            int roomIndex = game.getCurrentRoom();
            room room = game.getLevelSet().get(0).getRooms().get(roomIndex);

            for (int i = 0; i < room.getBulletList().size(); ++i) {
                projectile p = room.getBulletList().get(i);
                p.updatePosition();
                Gamepane.getChildren().get(Gamepane.getChildren().size() - i - 1).setLayoutX(p.getXcoord());
                Gamepane.getChildren().get(Gamepane.getChildren().size() - i - 1).setLayoutY(p.getYcoord());

                double bulletX = Gamepane.getChildren().get(Gamepane.getChildren().size() - i - 1).getLayoutX();
                double bulletY = Gamepane.getChildren().get(Gamepane.getChildren().size() - i - 1).getLayoutY();

                if (bulletX > 800 || bulletX < 0){
                    Gamepane.getChildren().remove(Gamepane.getChildren().size() - i - 1);
                    room.getBulletList().remove(i);
                }

                
                if (bulletY > 700 || bulletY < 0){
                    Gamepane.getChildren().remove(Gamepane.getChildren().size() - i - 1);
                    room.getBulletList().remove(i);
                }

            }
        }
    }

    @FXML
    public void updatePlayer(ActionEvent e) {
        if (playerIsMoving) {
            player.updatePosition();
            Gamepane.getChildren().get(0).setLayoutX(player.getXcoord());
            Gamepane.getChildren().get(0).setLayoutY(player.getYcoord());
        }
    }


    @FXML
    public void onDamage(ActionEvent e) throws IOException, InterruptedException {
        if(isNotPaused) {
            for (int i = 1; i < 1 + enemyCount; ++i){
                double playerX = Gamepane.getChildren().get(0).getLayoutX();
                double playerY = Gamepane.getChildren().get(0).getLayoutY();
                double enemyX = Gamepane.getChildren().get(i).getLayoutX();
                double enemyY = Gamepane.getChildren().get(i).getLayoutY();
               // boolean isCollision = (Math
                     //   .abs(Math.sqrt(Math.pow(playerX - enemyX, 2) + Math.pow(playerY - enemyY, 2))) <= 45.0);
                boolean isCollision = false;
                if (playerX + Gamepane.getChildren().get(0).getBoundsInParent().getWidth() > enemyX && playerX < enemyX + Gamepane.getChildren().get(i).getBoundsInParent().getWidth() && playerY + Gamepane.getChildren().get(0).getBoundsInParent().getHeight() > enemyY && playerY < enemyY + Gamepane.getChildren().get(i).getBoundsInParent().getHeight()) {
                    isCollision = true;
                }
                

                if (isCollision){
                    if(player.getShield() > 0)
                        player.setShield(player.getShield() - room.getEnemyList().get(i - 1).getDamage());
                    else
                        player.setHealth(player.getHealth() - room.getEnemyList().get(i - 1).getDamage());
                }

                if (player.getHealth() <= 0 && isNotPaused == true){
                    onDeath(new ActionEvent());
                }

            }
        }
    }

    @FXML
    public void onDeath(ActionEvent e) throws IOException, InterruptedException{
        GameWindow.playAudio("src\\audio\\Wilhelm Sceam.wav");
        isNotPaused = false;
        game.setNotPaused(false);
        var loader = new FXMLLoader(getClass().getResource("DeathWindow.fxml"));
        var scene = new Scene(loader.load());
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("R.I.P.");
        DeathWindow deathWindow = loader.getController();
        deathWindow.initialize(player.getName(), scoreLbl.getText());
        
    }

    // Pauses the game and opens the Pause Menu
    @FXML
    void Pause() throws IOException {
        TitleWindow.beep();
        isNotPaused = false;
        game.setNotPaused(false);
        var loader = new FXMLLoader(getClass().getResource("PauseMenu.fxml"));
        var scene = new Scene(loader.load());
        Stage stage = new Stage(StageStyle.UNDECORATED);
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

    // This method is called to call the load method in the game object and
    // initialize the newly loaded game
    public void load() {
        game = Game.load(false);
        levelBarCap = game.getLevelBarCap();
        gunFireCooldown = game.getPrimaryCooldown();
        abilityCooldown = game.getAbilityCooldown();
        int roomIndex = game.getCurrentRoom();
        room = game.getLevelSet().get(0).getRooms().get(roomIndex);
        player = game.getUser();
        character = game.getCharacter();
        switch (character) {
            case PIZZA:
                makeImage(pizza, player);
                abilityTime = 20;
                break;

            case MAC:
                makeImage(mac, player);
                abilityTime = 40;
                break;

            case RAMEN:
                makeImage(ramen, player);
                abilityTime = 10;
                break;

            case HPOCKET:
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
        tickProcessing();
        Gamepane.requestFocus();
        setmovement();

        cooldownThread = new Thread(() -> {
            var keyframe = new KeyFrame(Duration.millis(50), this::updateCooldowns);
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
        game.setNotPaused(true);
        Gamepane.requestFocus();
    }

    // Uses an audio filepath string and plays it -Note, the file must be in the
    // .wav format
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
            playAudio("src\\audio\\powerUp.wav");
            KeyFrame keyframe = new KeyFrame(Duration.millis(8), e -> {
                player.setShield(player.getShield() + 0.5);
            });
            Timeline timer = new Timeline(keyframe);
            timer.setCycleCount(20);
            timer.play();
            KeyFrame keyframeTwo = new KeyFrame(Duration.seconds(1), e -> {
                if (player.getShield() > 0.0)
                    player.setShield(player.getShield() - 1.0);
            });
            Timeline timerTwo = new Timeline(keyframeTwo);
            timerTwo.setCycleCount(10);
            timerTwo.play();
            abilityCooldown = 20.0;
        }
    }

    public void homingMissle() {
        if(abilityCooldown <= 0.0) {
            playAudio("src\\audio\\missle.wav");
            int roomIndex = game.getCurrentRoom();
            room = game.getLevelSet().get(roomIndex).getRooms().get(roomIndex);
            room.getBulletList().add(new projectile(1000, 10, 1, 5, player.getXcoord(), player.getYcoord()));
            int bulletIndex = room.getBulletList().size() - 1;
            room.getBulletList().get(bulletIndex).setDirection(cursorX, cursorY);
            makeImage(hMissle, room.getBulletList().get(room.getBulletList().size() - 1)); 
            KeyFrame kf = new KeyFrame(Duration.millis(100), this::movebullet);
            var timer = new Timeline(kf);            
            timer.setCycleCount(100);
            timer.play();
            KeyFrame keyframe = new KeyFrame(Duration.millis(100), event -> {
                enemy closestEnemy = null;
                if(room.getEnemyList().size() > 0) {
                    for(enemy e: room.getEnemyList()) {
                        if(closestEnemy == null) 
                            closestEnemy = e;
                        else {
                            if(Math.sqrt(Math.pow(player.getXcoord() - e.getXcoord(), 2) + Math.pow(player.getYcoord() - e.getYcoord(), 2)) < Math.sqrt(Math.pow(player.getXcoord() - closestEnemy.getXcoord(), 2) + Math.pow(player.getYcoord() - closestEnemy.getYcoord(), 2))) // Compare Distances
                                closestEnemy = e;
                        }
                    }
                    if(closestEnemy != null && room.getBulletList().size() > bulletIndex) 
                        room.getBulletList().get(bulletIndex).setDirection(closestEnemy.getXcoord(), closestEnemy.getYcoord());
                    player.setDamage((int) (2 * player.getDamage()));
                    if(onHit(new ActionEvent()))
                        game.setScore(game.getScore() + 5);
                    player.setDamage((int) (player.getDamage() / 2));
            }});
            Timeline timertwo = new Timeline(keyframe);
            timertwo.getKeyFrames().addAll(keyframe);
            timertwo.setCycleCount(100);
            timertwo.play();
            abilityCooldown = 10.0;
        }
    }

    public void railGun() {
        if(abilityCooldown <= 0.0) {
            playAudio("src\\audio\\cannon.wav");
            int roomIndex = game.getCurrentRoom();
            room = game.getLevelSet().get(game.getCurrentLevel()).getRooms().get(roomIndex);
            room.getBulletList().add(new projectile(1000, 10, 1, 5, player.getXcoord(), player.getYcoord()));
            int bulletIndex = room.getBulletList().size() - 1;
            room.getBulletList().get(bulletIndex).setDirection(cursorX, cursorY);
            makeImage(railgun, room.getBulletList().get(room.getBulletList().size() - 1)); 
            KeyFrame kf = new KeyFrame(Duration.millis(50), this::movebullet);
            var timer = new Timeline(kf);            
            timer.setCycleCount(100);
            timer.play();
            enemy closestEnemy = null;
            if (room.getEnemyList().size() > 0) {
                for (enemy e : room.getEnemyList()) {
                    if (closestEnemy == null)
                        closestEnemy = e;
                    else {
                        if (Math.sqrt(Math.pow(player.getXcoord() - e.getXcoord(), 2)
                                + Math.pow(player.getYcoord() - e.getYcoord(), 2)) < Math
                                        .sqrt(Math.pow(player.getXcoord() - closestEnemy.getXcoord(), 2)
                                                + Math.pow(player.getYcoord() - closestEnemy.getYcoord(), 2))) // Compare
                                                                                                               // Distances
                            closestEnemy = e;
                    }
                }
                if(closestEnemy != null && room.getBulletList().size() > bulletIndex) 
                    room.getBulletList().get(bulletIndex).setDirection(closestEnemy.getXcoord(), closestEnemy.getYcoord());
            }
            KeyFrame keyframe = new KeyFrame(Duration.millis(100), event -> {
                    player.setDamage((int) (100 * player.getDamage()));
                    if(onHit(new ActionEvent()))
                        game.setScore(game.getScore() + 30);
                    player.setDamage((int) (player.getDamage() / 100));
            });
            Timeline timertwo = new Timeline(keyframe);
            timertwo.getKeyFrames().addAll(keyframe);
            timertwo.setCycleCount(100);
            timertwo.play();
            }
            else
                playAudio("src\\audio\\powerDown.wav");
            abilityCooldown = 40.0;
        }
}
