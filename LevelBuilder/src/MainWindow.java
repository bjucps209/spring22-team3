
//-----------------------------------------------------------
//File:   MainWindow.java
//Desc:   This is the main window for the program.
//----------------------------------------------------------- 
import java.util.ArrayList;
import java.util.Random;

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

import model.enemy;
import model.entity;
import model.room;
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
        room r = new room(0, 0);
        level.addRoom(r);
        currentRoom = r;
        currentLevel = level;

    }

    final Image IMG_ROBOT = new Image("images/robot.png");
    final Image IMG_BOMB = new Image("images/bomb.png");
    final Image IMG_WAVE = new Image("images/wave.png");
    final Image IMG_MICROWAVE = new Image("images/microwave.gif");
    room currentRoom;
    LevelData currentLevel;
    static ImageView entitySelected;
    ArrayList<ImageView> entitiesImages = new ArrayList<ImageView>();
    int roomCounter = 0;
    @FXML
    void createRoom(ActionEvent event) {
        Object button = event.getSource();
        if (button == leftBtn) {
            room r = new room(currentRoom.getX() - 1, currentRoom.getY());
            //print coordinates
            System.out.println("Room created at: " + r.getX() + "," + r.getY());
            currentLevel.addRoom(r);
            currentRoom = r;
        } else if (button == rightBtn) {
            room r = new room(currentRoom.getX() + 1, currentRoom.getY());
            //print coordinates
            System.out.println("Room created at: " + r.getX() + "," + r.getY());
            currentLevel.addRoom(r);
            currentRoom = r;
        } else if (button == upBtn) {
            room r = new room(currentRoom.getX(), currentRoom.getY() + 1);
            //print coordinates
            System.out.println("Room created at: " + r.getX() + "," + r.getY());
            currentLevel.addRoom(r);
            currentRoom = r;
        } else if (button == downBtn) {
            room r = new room(currentRoom.getX(), currentRoom.getY() - 1);
            //print coordinates
            System.out.println("Room created at: " + r.getX() + "," + r.getY());
            currentLevel.addRoom(r);
            currentRoom = r;
        }
      
    }
    @FXML
    void onWaveClicked(ActionEvent event) {
        enemy enemy = new enemy(100, 10, 20, 1, new Random().nextInt(780), new Random().nextInt(480));
        currentRoom.addEntity(enemy);
        var img = new ImageView(IMG_WAVE);
        img.setFitHeight(50);
        img.setFitWidth(50);
        System.out.println(enemy.getId());


        img.relocate(enemy.getXcoord(), enemy.getYcoord());
        pane.getChildren().add(img);
        img.setUserData(enemy);
        entitiesImages.add(img);
        img.setOnMouseClicked(e -> {
            onCritterClicked(event, img);
        });

    }

    void selectEntity(Node img) {

        if (entitySelected != null) {
            for (ImageView c : entitiesImages) {

                c.getStyleClass().remove("current");

            }

        }
        img.getStyleClass().add("current");
    }
    @FXML
    void onSaveEntityClicked(ActionEvent event) {
        entity entity = (entity) entitySelected.getUserData();
        entity.setHealth(Integer.parseInt(txthealth.getText()));
        entity.setSpeed(Integer.parseInt(txtSpeed.getText()));
        entity.setDamage(Integer.parseInt(txtDamage.getText()));
        currentLevel.save();
        
       
    }
    @FXML
    void onCritterClicked(Event event, ImageView img) {
        enemy enemy = (enemy) img.getUserData();

        makeDraggable(img);
        selectEntity(img);
        entitySelected = img;
        // make the crittter selected invoke the updateLoc method
        ((enemy) (entitySelected.getUserData())).updateLocation();

    }

    @FXML
    void ongunWaveClicked(ActionEvent event) {
        enemy enemy = new enemy(100, 10, 20, 1, new Random().nextInt(780), new Random().nextInt(480));
        var img = new ImageView(IMG_MICROWAVE);
        currentRoom.addEntity(enemy);
        img.setFitHeight(50);
        img.setFitWidth(50);
        img.relocate(enemy.getXcoord(), enemy.getYcoord());
        pane.getChildren().add(img);
        img.setUserData(enemy);
        entitiesImages.add(img);
        img.setOnMouseClicked(e -> {
            onCritterClicked(event, img);
        });

    }

    @FXML
    //when delete button is clicked remove the critter that is currently selected
    void onDeleteClicked(ActionEvent event) {
        if (entitySelected != null) {
            pane.getChildren().remove(entitySelected);
            currentRoom.removeEntity((entity) entitySelected.getUserData());

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
     * @param x the x coordinate of the entity
     * @param y the y coordinate of the entity
     * @param id the id of the entity
     * @param health the health of the entity
     * @param speed the speed of the entity
     * @param damage the damage of the entity
     * 
     * 
     * 
     */
    void updateTable(int x, int y, int id, int health, int speed, int damage) {
        lblId.setText(String.valueOf(id));
        lblLoc.setText(String.valueOf(x + "," + y));
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
