
//-----------------------------------------------------------
//File:   MainWindow.java
//Desc:   This is the main window for the program.
//----------------------------------------------------------- 
import java.util.ArrayList;
import java.util.Random;

import javafx.beans.binding.IntegerBinding;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Track;

import model.CritterType;
import model.Level;
import model.LevelData;
import model.directions;
import model.enemy;
import model.entity;
import model.obstacle;
import model.room;
import model.staircase;

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
    void initialize() {

        entity.setHandler(this::updateTable);
        LevelData level = new LevelData(1);
        room r = new room(0, 0, null);
        level.addRoom(r);
        currentRoom = r;
        currentLevel = level;
        checkBounds();

    }

    final Image IMG_ROBOT = new Image("images/robot.png");
    final Image IMG_BOMB = new Image("images/bomb.png");
    final Image IMG_WAVE = new Image("images/wave.png");
    final Image IMG_MICROWAVE = new Image("images/microwave.png");
    final Image IMG_OBSTACLE = new Image("images/obstacle.png");
    room currentRoom;
    LevelData currentLevel;
    static ImageView entitySelected;

    int roomCounter = 0;

Boolean checkRoomsAround(String direction) {
        // check if there is a room to the left using findRoom
        switch (direction) {
            case "left":
                if(currentLevel.findRoom(currentRoom.getX() - 1, currentRoom.getY()) == null) {
                    return false;
               
                    
                }
                else{
                    return true;
                }
                
            case "right":

                if(currentLevel.findRoom(currentRoom.getX() + 1, currentRoom.getY()) == null) {
                    return false;
                }
                else{
                    return true;
                }
            case "up":
                if(currentLevel.findRoom(currentRoom.getX(), currentRoom.getY() + 1) == null) {
                    return false;
                }
                else{
                    return true;
                }

               
            case "down":
                if(currentLevel.findRoom(currentRoom.getX(), currentRoom.getY() - 1) == null) {
                    return false;
                }
                else{
                    return true;
                }

                

            default:
                return false;
        }

    }

    @FXML
    void createRoom(ActionEvent event) {

        Object button = event.getSource();
        if (button == leftBtn) {
            if(checkRoomsAround("left")){
                clearPane();
               currentRoom = currentLevel.findRoom(currentRoom.getX() - 1, currentRoom.getY());
               loadRoom(currentRoom.getX(), currentRoom.getY());
            }       
            else{
                clearPane();
                room r = new room(currentRoom.getX() - 1, currentRoom.getY(), false);
                // print coordinates
                
                System.out.println("Room created at: " + r.getX() + "," + r.getY());
                currentLevel.addRoom(r);
                loadRoom(currentRoom.getX() - 1, currentRoom.getY());
                currentRoom = r;
            }
        

        } else if (button == rightBtn) {
            if(checkRoomsAround("right")){
                clearPane();
                currentRoom = currentLevel.findRoom(currentRoom.getX() + 1, currentRoom.getY());
                loadRoom(currentRoom.getX(), currentRoom.getY());
            }
            else{
                clearPane();
                room r = new room(currentRoom.getX() + 1, currentRoom.getY(), false);
                // print coordinates
    
                System.out.println("Room created at: " + r.getX() + "," + r.getY());
                currentLevel.addRoom(r);
                loadRoom(currentRoom.getX() + 1, currentRoom.getY());
                currentRoom = r;
            }
            

        } else if (button == upBtn) {
            if(checkRoomsAround("up")){
                clearPane();
                currentRoom = currentLevel.findRoom(currentRoom.getX(), currentRoom.getY() + 1);
                loadRoom(currentRoom.getX(), currentRoom.getY());
            }
            else{
                clearPane();
                room r = new room(currentRoom.getX(), currentRoom.getY() + 1, false);
                // print coordinates
    
                System.out.println("Room created at: " + r.getX() + "," + r.getY());
                currentLevel.addRoom(r);
                loadRoom(currentRoom.getX(), currentRoom.getY() + 1);
                currentRoom = r;
            }
            
        } else if (button == downBtn) {
            if(checkRoomsAround("down")){
                clearPane();
                currentRoom = currentLevel.findRoom(currentRoom.getX(), currentRoom.getY() - 1);
                loadRoom(currentRoom.getX(), currentRoom.getY());
            }
            else{
                clearPane();
                room r = new room(currentRoom.getX(), currentRoom.getY() - 1, false);
                // print coordinates
    
                System.out.println("Room created at: " + r.getX() + "," + r.getY());
                currentLevel.addRoom(r);
                loadRoom(currentRoom.getX(), currentRoom.getY() - 1);
                currentRoom = r;
            }
           
           
        }
        checkBounds();

    }
   

    void checkBounds() {
        // check to see if the room to the left is not -1 and if it is disable the left
        // button
        if (currentRoom.getX() == 0) {
            leftBtn.setDisable(true);
        } else {
            leftBtn.setDisable(false);
        }
        // check to see if the room to the right is not 5 and if it is disable the right
        // button
        if (currentRoom.getX() == 4) {
            rightBtn.setDisable(true);
        } else {
            rightBtn.setDisable(false);
        }
        // check to see if the room to the top is not 5 and if it is disable the up
        // button
        if (currentRoom.getY() == 4) {
            upBtn.setDisable(true);
        } else {
            upBtn.setDisable(false);
        }
        // check to see if the room to the bottom is not -1 and if it is disable the
        // down button
        if (currentRoom.getY() == 0) {
            downBtn.setDisable(true);
        } else {
            downBtn.setDisable(false);
        }

    }

    @FXML
    void onWaveClicked(ActionEvent event) {
        enemy enemy = new enemy(100, 10, 20, 1, new Random().nextInt(780), new Random().nextInt(480));
        currentRoom.addEnemy(enemy);
        var img = new ImageView(IMG_WAVE);
        img.setFitHeight(100);
        img.setFitWidth(100);
        System.out.println(enemy.getId());

        img.relocate(enemy.getXcoord(), enemy.getYcoord());
        pane.getChildren().add(img);
        img.setUserData(enemy);
        currentRoom.addImage(img);

        img.setOnMouseClicked(e -> {
            onCritterClicked(event, img);
        });

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
    void clearPane(){
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
            for (ImageView c : currentRoom.getImageList()) {

                c.getStyleClass().remove("current");

            }

        }
        img.getStyleClass().add("current");
    }

    @FXML
    void onSaveEntityClicked(ActionEvent event) {
        entity entity = (entity) entitySelected.getUserData();
        entity.setHealth(Integer.parseInt(txthealth.getText()));
        entity.setSpeed(Double.parseDouble(txtSpeed.getText()));
        entity.setDamage(Double.parseDouble(txtDamage.getText()));
        // currentLevel.save();

    }

    @FXML
    void onCritterClicked(Event event, ImageView img) {
        entity enemy = (entity) img.getUserData();

        makeDraggable(img);
        selectEntity(img);
        entitySelected = img;

        // make the crittter selected invoke the updateLoc method
        ((entity) (entitySelected.getUserData())).updateLocationTable();

    }

    @FXML
    void ongunWaveClicked(ActionEvent event) {
        enemy enemy = new enemy(100, 10, 20, 1, new Random().nextInt(780), new Random().nextInt(480));
        var img = new ImageView(IMG_MICROWAVE);
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
            } else if (entitySelected.getUserData() instanceof obstacle) {
                currentRoom.removeObstacle((obstacle) entitySelected.getUserData());
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
        if (entitySelected.getUserData() instanceof obstacle) {
            txtDamage.disableProperty().set(true);
            txthealth.disableProperty().set(true);
            txtSpeed.disableProperty().set(true);
        } else {
            txtDamage.disableProperty().set(false);
            txthealth.disableProperty().set(false);
            txtSpeed.disableProperty().set(false);
        }
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
