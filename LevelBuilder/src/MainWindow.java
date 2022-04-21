
//-----------------------------------------------------------
//File:   MainWindow.java
//Desc:   This is the main window for the program.
//----------------------------------------------------------- 

import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.*;

import java.io.*;

public class MainWindow {
    @FXML
    Pane pane;
    @FXML
    Label lblId;
    @FXML
    Label lblLoc;
    @FXML
    TextField txthealth;
    @FXML
    TextField txtSpeed;
    @FXML
    TextField txtDamage;
    @FXML
    Button leftBtn;
    @FXML
    Button rightBtn;
    @FXML
    Button upBtn;
    @FXML
    Button downBtn;
    @FXML
    Button startBtn;
    @FXML
    Button deletestartptBtn;
    ImageView tempImage = new ImageView();
    room tempRoom = new room(0, 0, true);

    @FXML
    void initialize() {

        entity.setHandler(this::updateTable);
        LevelData level = new LevelData(1);
        room r = new room(0, 0, null);
        level.addRoom(r);
        currentRoom = r;
        currentLevel = level;
        checkBounds();
        deletestartptBtn.disableProperty().set(true);
        leftBtn.setUserData(new String("left"));
        rightBtn.setUserData(new String("right"));
        upBtn.setUserData(new String("up"));
        downBtn.setUserData(new String("down"));

    }

    final Image IMG_ROBOT = new Image("images/robot.png");
    final Image IMG_BOMB = new Image("images/bomb.png");
    final Image IMG_WAVE = new Image("images/wave.png");
    final Image IMG_MICROWAVE = new Image("images/microwave.png");
    final Image IMG_OBSTACLE = new Image("images/obstacle.png");
    final Image IMG_STAIRCASE = new Image("images/staircase.png");
    final Image IMG_SPAWN = new Image("images/spawn.png");
    final Image IMG_RED = new Image("images/red.png");
    room currentRoom;
    LevelData currentLevel;
    static ImageView entitySelected;
    Boolean startPlaced = false;

    int roomCounter = 0;

    Boolean checkRoomsAround(String direction) {
        // check if there is a room to the left using findRoom
        Boolean check =  currentLevel.findRoom(currentRoom.getX() + Integer.parseInt(checkDirection(direction).split(",")[0]), currentRoom.getY() +Integer.parseInt(checkDirection(direction).split(",")[1]) ) != null;
        return check;

    }

    @FXML
    void setSpawnPoint(ActionEvent event) {
        startPlaced = true;
        Button source = (Button) event.getSource();
        source.disableProperty().set(true);
        deletestartptBtn.disableProperty().set(false);
        ImageView img = new ImageView(IMG_SPAWN);

        startpt s = new startpt(0, 0, 0, 0, 300, 300);
        currentRoom.setStart(s);
        img.setFitHeight(50);
        img.setFitWidth(50);
        pane.getChildren().add(img);
        img.setUserData(s);
        currentRoom.addImage(img);
        img.relocate(s.getXcoord(), s.getYcoord());
        img.setOnMouseClicked(e -> {
            onCritterClicked(event, img);
        });

    }

    @FXML
    void deleteSpawnPoint(ActionEvent event) {
        Button source = (Button) event.getSource();
        source.disableProperty().set(true);
        startPlaced = false;
        startBtn.disableProperty().set(false);

        currentLevel.getRoomList().forEach(room -> {
            room.getImageList().forEach(image -> {
                if (image.getUserData() instanceof startpt) {
                    tempImage = image;
                    tempRoom = room;

                }
            });
        });
        pane.getChildren().remove(tempImage);
        tempRoom.getImageList().remove(tempImage);
        tempRoom.setStart(null);
    }
    void moveRoom(String dir){
      
        currentRoom = currentLevel.findRoom(currentRoom.getX() + Integer.parseInt(checkDirection(dir).split(",")[0]), currentRoom.getY() +Integer.parseInt(checkDirection(dir).split(",")[1]));
        loadRoom(currentRoom.getX(), currentRoom.getY());
        checkBounds();
    }
    String checkDirection(String dir){
        //get an input like "left" and return something like (-1,0) to signal in what direction to move
        switch (dir){
            case "left":
                return "-1,0";
            case "right":
                return "1,0";
            case "up":
                return "0,1";
            case "down":
                return "0,-1";
            default:
                return "";
        }

    }
    @FXML
    void createRoom(ActionEvent event) {
        clearPane();
        Button button = (Button) event.getSource();
       String direction =(String) button.getUserData();
       Boolean canMove = checkRoomsAround(direction);
         if(canMove){
              moveRoom(direction);
         }
         else{
             
             room r = new room(currentRoom.getX() + Integer.parseInt(checkDirection(direction).split(",")[0]), currentRoom.getY() +Integer.parseInt(checkDirection(direction).split(",")[1]), false);
             currentLevel.addRoom(r);
            currentRoom = r;
            
         }
        checkBounds();



    }

    void checkBounds() {
        // check to the left
        Boolean check = currentRoom.getX() == 0 ? true : false;
        leftBtn.setDisable(check);
        // check to the right
        check = currentRoom.getX() == 4 ? true : false;
        rightBtn.setDisable(check);
        // check to the top
        check = currentRoom.getY() == 4 ? true : false;
        upBtn.setDisable(check);
        // check to the bottom
        check = currentRoom.getY() == 0 ? true : false;
        downBtn.setDisable(check);

    }

    @FXML
    void onObstacleClicked(ActionEvent event) {
        obstacle obstacle = new obstacle(0, 0, 0, 1, new Random().nextInt(780), new Random().nextInt(580));
        currentRoom.addObstacle(obstacle);
        var img = new ImageView(IMG_OBSTACLE);
        img.setFitHeight(50);
        img.setFitWidth(50);
        img.relocate(obstacle.getXcoord(), obstacle.getYcoord());
        pane.getChildren().add(img);
        img.setUserData(obstacle);
        currentRoom.addImage(img);

        img.setOnMouseClicked(e -> {
            onCritterClicked(event, img);
        });

    }

    void clearPane() {
        entitySelected = null;
        // clear the table
        lblId.setText("");
        lblLoc.setText("");
        txthealth.setText("");
        txtSpeed.setText("");
        txtDamage.setText("");
        for (ImageView c : currentRoom.getImageList()) {

            c.getStyleClass().remove("current");

        }
        for (ImageView img : currentRoom.getImageList()) {
            pane.getChildren().remove(img);
        }

    }

    void loadRoom(int x, int y) {
        // find the room, if there is one and load it placing the images on the pane
        // clear all the entity images from the pane

        for (ImageView e : currentLevel.findRoom(x, y).getImageList()) {

            pane.getChildren().add(e);
            e.relocate(((entity) e.getUserData()).getXcoord(), ((entity) e.getUserData()).getYcoord());

        }
    }

    void selectEntity(Node img) {

        if (entitySelected != null) {
            currentRoom.getImageList().forEach(c -> {
                c.getStyleClass().remove("current");
            });
        }
        img.getStyleClass().add("current");
    }

    @FXML
    void onSaveEntityClicked(ActionEvent event) {
        entity entity = (entity) entitySelected.getUserData();
        entity.setHealth(Integer.parseInt(txthealth.getText()));
        entity.setSpeed(Double.parseDouble(txtSpeed.getText()));
        entity.setDamage(Double.parseDouble(txtDamage.getText()));
        currentLevel.save();

    }

    @FXML
    void onCritterClicked(Event event, ImageView img) {

        makeDraggable(img);
        selectEntity(img);
        entitySelected = img;

        // make the crittter selected invoke the updateLoc method
        ((entity) (entitySelected.getUserData())).updateLocationTable();

    }

    @FXML
    void placeEntity(ActionEvent event) {

        // get the button that was clicked
        var img = new ImageView(
                ((ImageView) (((Button) event.getSource())
                .getChildrenUnmodifiable().get(0))).getImage());
        // TODO: add the ability to add an obstacle

        enemy enemy = new enemy(100, 10, 20, 1, new Random().nextInt(780), new Random().nextInt(480));
        currentRoom.addEnemy(enemy);
        img.setFitHeight(100);
        img.setFitWidth(100);
        img.relocate(enemy.getXcoord(), enemy.getYcoord());
        pane.getChildren().add(img);
        img.setUserData(enemy);
        currentRoom.addImage(img);

        img.setOnMouseClicked(e -> {
            onCritterClicked(event, img);
        });

    }

    @FXML
    // when delete button is clicked remove the critter that is currently selected
    void onDeleteClicked(ActionEvent event) {
        if (entitySelected != null) {
            pane.getChildren().remove(entitySelected);
            // check to see what kind of entity it is
            if (entitySelected.getUserData() instanceof enemy) {
                currentRoom.removeEnemy((enemy) entitySelected.getUserData());
                // remove the image from the room
                currentRoom.removeImage(entitySelected);
            } else if (entitySelected.getUserData() instanceof obstacle) {
                currentRoom.removeObstacle((obstacle) entitySelected.getUserData());
                currentRoom.removeImage(entitySelected);
            } // startPoint
            else if (entitySelected.getUserData() instanceof startpt) {
                // currentRoom.removeStartPoint((startpt) entitySelected.getUserData());
                currentRoom.removeImage(entitySelected);
            }

            entitySelected = null;
            // clear the table
            lblId.setText("");
            lblLoc.setText("");
            txthealth.setText("");
            txtSpeed.setText("");
            txtDamage.setText("");
        }
    }

    /**
     * 
     * @param x      the x coordinate of the entity
     * @param y      the y coordinate of the entity
     * @param id     the id of the entity
     * @param health the health of the entity
     * @param speed  the speed of the entity
     * @param damage the damage of the entity
     * 
     * 
     * 
     */
    void updateTable(int x, int y, int id, int health, double speed, double damage) {
        lblId.setText(String.valueOf(id));
        lblLoc.setText(String.valueOf(x + "," + y));
        Boolean isEnemy = entitySelected.getUserData() instanceof enemy;
        txtDamage.disableProperty().set(!isEnemy);
        txthealth.disableProperty().set(!isEnemy);
        txtSpeed.disableProperty().set(!isEnemy);

        txthealth.setText(String.valueOf(((entity) (entitySelected.getUserData())).getHealth()));
        txtSpeed.setText(String.valueOf(((entity) entitySelected.getUserData()).getSpeed()));
        txtDamage.setText(String.valueOf(((entity) entitySelected.getUserData()).getDamage()));

        // txtDamage.setText(String.valueOf(c.getState()));
    }

    // make the entity draggable
    // takes in a node as a parameter
    private void makeDraggable(Node node) {
        final Delta dragDelta = new Delta();

        node.setOnMouseEntered(me -> node.getScene().setCursor(Cursor.HAND));
        node.setOnMouseExited(me -> node.getScene().setCursor(Cursor.DEFAULT));
        node.setOnMousePressed(me -> {
            dragDelta.x = me.getX();
            dragDelta.y = me.getY();
            node.getScene().setCursor(Cursor.MOVE);
            entitySelected = (ImageView) node;
            selectEntity(node);

        });
        node.setOnMouseDragged(me -> {
            node.setLayoutX(node.getLayoutX() + me.getX() - dragDelta.x);
            node.setLayoutY(node.getLayoutY() + me.getY() - dragDelta.y);

        });
        node.setOnMouseReleased(me -> {
            node.getScene().setCursor(Cursor.HAND);
            System.out.println(node.getLayoutX() + ", " + node.getLayoutY());
            entity entity = (entity) node.getUserData();
            entity.setXcoord((int) node.getLayoutX());
            entity.setYcoord((int) node.getLayoutY());

        });

        // Prevent mouse clicks on img from propagating to the pane and
        // resulting in creation of a new image
        node.setOnMouseClicked(me -> me.consume());
    }

    private class Delta {
        public double x;
        public double y;
    }
}
