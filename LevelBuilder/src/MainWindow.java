
//-----------------------------------------------------------
//File:   MainWindow.java
//Desc:   This is the main window for the LevelBuilder.
//----------------------------------------------------------- 

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

public class MainWindow {
    @FXML
    Pane pane; // the pane that holds the game
    @FXML
    Label lblId; // the label that displays the entity's id
    @FXML
    Label lblLoc; // the label that displays the entity's location
    @FXML
    TextField txthealth; // the text field that displays the entity's health
    @FXML
    TextField txtSpeed; // the text field that displays the entity's speed
    @FXML
    TextField txtDamage; // the text field that displays the entity's damage
    @FXML
    Button leftBtn; // the button that moves to the room on the left
    @FXML
    Button rightBtn; // the button that moves to the room on the right
    @FXML
    Button upBtn; // the button that moves to the room above
    @FXML
    Button downBtn; // the button that moves to the room below
    @FXML
    Button startBtn; // the button that adds the startpoint to the room
    @FXML
    Label lblRoomCoord; // the label that displays the room's coordinates
    @FXML
    Button saveBtn; // the button that saves the Level
    @FXML
    Button setStairsBtn; // the button that sets the stairs
    @FXML
    Button removeStairsBtn; // the button that removes the stairs

    @FXML
    Button deletestartptBtn; // the button that deletes the startpoint
    ImageView tempImage = new ImageView(); // the temporary image that is used to hold a reference to the image that is
                                           // going to be deleted
    room tempRoom = new room(0, 0, true); // the temporary room that is used to hold a reference to the room that is
                                          // going to delete a entity from it
    String type = ""; // the type if it is a new level or a loaded level
    int numLevel = 0; // the number of the level

    @FXML
    void initialize(int setLevel, String setType) {
        // assigns the level number and the type
        // sets all the buttons to their default state
        leftBtn.setUserData(new String("left"));
        rightBtn.setUserData(new String("right"));
        upBtn.setUserData(new String("up"));
        downBtn.setUserData(new String("down"));
        entity.setHandler(this::updateTable);
        deletestartptBtn.disableProperty().set(true);
        type = setType;
        saveBtn.disableProperty().set(true);
        removeStairsBtn.disableProperty().set(true);

        if (setLevel == 0) {
            // loop through all the levels in the directory
            File dir = new File("../microwaveDungeon/src/Levels");
            int level;
            File[] directoryListing = dir.listFiles();
            for (File file : directoryListing) {
                String[] list = file.toString().split("\\\\");

                level = Integer.parseInt(list[4].split("\\.")[0]);

                numLevel = level + 1;
            }

            LevelData newlevel = new LevelData(numLevel);
            room r = new room(0, 0, null);
            newlevel.addRoom(r);
            currentRoom = r;
            currentLevel = newlevel;
            checkBounds();
        } else {
            loadLevel(setLevel, new ActionEvent());

        }
        displayCoord();

    }

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

    void displayCoord() {
        lblRoomCoord.setText("(" + currentRoom.getX() + "," + currentRoom.getY() + ")");

    }

    @FXML
    void setStairs(ActionEvent event) {
        // sets the stairs

        setStairsBtn.disableProperty().set(true);
        removeStairsBtn.disableProperty().set(false);
        ImageView img = new ImageView(IMG_STAIRCASE);

        staircase s = new staircase(0, 0, 0, 0, 300, 300);
        currentRoom.setStaircase(s);
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
    void removeStairs(ActionEvent event) {
        // removes the stairs

        removeStairsBtn.disableProperty().set(true);

        setStairsBtn.disableProperty().set(false);

        currentLevel.getRoomList().forEach(room -> {
            room.getImageList().forEach(image -> {
                if (image.getUserData() instanceof staircase) {
                    tempImage = image;
                    tempRoom = room;

                }
            });
        });
        pane.getChildren().remove(tempImage);
        tempRoom.getImageList().remove(tempImage);
        tempRoom.setStart(null);
    }

    Boolean checkRoomsAround(String direction) {
        // check if there is a room to the left using findRoom
        Boolean check = currentLevel.findRoom(
                currentRoom.getX() + Integer.parseInt(checkDirection(direction).split(",")[0]),
                currentRoom.getY() + Integer.parseInt(checkDirection(direction).split(",")[1])) != null;
        return check;

    }

    void loadLevel(int levelnum, ActionEvent event) {
        // loads the level from the file
        LevelData level = new LevelData(levelnum);
        level.load();
        currentLevel = level;
        currentRoom = currentLevel.findRoom(0, 0);
        loadRoom(0, 0);

        checkBounds();
        displayCoord();
        for (room r : currentLevel.getRoomList()) {
            for (ImageView i : r.getImageList()) {
                i.setOnMouseClicked(e -> {
                    onCritterClicked(event, i);

                });
                if (i.getUserData() instanceof startpt) {
                    deletestartptBtn.disableProperty().set(false);
                    startBtn.disableProperty().set(true);

                }
                if (i.getUserData() instanceof staircase) {
                    setStairsBtn.disableProperty().set(true);
                    removeStairsBtn.disableProperty().set(false);
                }
            }

        }

    }

    @FXML
    void setSpawnPoint(Event event) {
        // sets the spawn point
        startPlaced = true;

        startBtn.disableProperty().set(true);
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
        // deletes the spawn point
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

    @FXML
    void seeLayout(ActionEvent event) {
        // shows the layout of the level in a new window
        // open RoomView.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomView.fxml"));
            Parent root = loader.load();
            RoomView controller = loader.getController();
            controller.initialize(currentLevel, currentRoom);
            Stage stage = new Stage();
            stage.setTitle("Room Layout");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void moveRoom(String dir) {
        // moves the room in the direction specified

        currentRoom = currentLevel.findRoom(currentRoom.getX() + Integer.parseInt(checkDirection(dir).split(",")[0]),
                currentRoom.getY() + Integer.parseInt(checkDirection(dir).split(",")[1]));
        loadRoom(currentRoom.getX(), currentRoom.getY());
        checkBounds();

    }

    void checkifOutofBounds(ImageView e) {
        // check to see if the entity is out of bounds and if so, move it back in bounds
        entity e1 = (entity) e.getUserData();
        if (e1.getXcoord() > 700) {
            e1.setXcoord(700);
            e.relocate(e1.getXcoord(), e1.getYcoord());
        }
        if (e1.getYcoord() > 480) {
            e1.setYcoord(480);
            e.relocate(e1.getXcoord(), e1.getYcoord());
        }
        if (e1.getXcoord() < 0) {
            e1.setXcoord(0);
            e.relocate(e1.getXcoord(), e1.getYcoord());
        }
        if (e1.getYcoord() < 0) {
            e1.setYcoord(0);
            e.relocate(e1.getXcoord(), e1.getYcoord());
        }

    }

    String checkDirection(String dir) {
        // get an input like "left" and return something like (-1,0) to signal in what
        // direction to move
        switch (dir) {
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
        // creates a new room
        clearPane();
        Button button = (Button) event.getSource();
        String direction = (String) button.getUserData();
        Boolean canMove = checkRoomsAround(direction);
        if (canMove) {
            moveRoom(direction);
        } else {

            room r = new room(currentRoom.getX() + Integer.parseInt(checkDirection(direction).split(",")[0]),
                    currentRoom.getY() + Integer.parseInt(checkDirection(direction).split(",")[1]), false);
            currentLevel.addRoom(r);
            currentRoom = r;

        }
        checkBounds();
        displayCoord();

    }

    void checkBounds() {
        // check to the left
        Boolean check = currentRoom.getX() == 0 ? true : false;
        leftBtn.setDisable(check);
        // check to the right
        //TODO: This is hardcoded for now, need to fix
        check = currentRoom.getX() == 4 ? true : false;
        rightBtn.setDisable(check);
        // check to the top
        // TODO: This is hardcoded for now, need to fix
        check = currentRoom.getY() == 0 ? true : false;
        upBtn.setDisable(check);
        // check to the bottom
        check = currentRoom.getY() == 0 ? true : false;
        downBtn.setDisable(check);

    }

    @FXML
    void onObstacleClicked(ActionEvent event) {
        // creates an obstacle
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
        // clears the pane that holds the stats of an entity
        entitySelected = null;
        // clear the table
        lblId.setText("");
        lblLoc.setText("");
        txthealth.setText("");
        txtSpeed.setText("");
        txtDamage.setText("");
        saveBtn.disableProperty().set(true);

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
        displayCoord();
    }

    void selectEntity(Node img) {

        // selects a specific entity and highlights it
        if (entitySelected != null) {
            currentRoom.getImageList().forEach(c -> {
                c.getStyleClass().remove("current");
            });
        }
        img.getStyleClass().add("current");
    }

    @FXML
    void onSaveEntityClicked(ActionEvent event) {
        // saves the entity to be saved to the file
        entity entity = (entity) entitySelected.getUserData();
        entity.setHealth(Integer.parseInt(txthealth.getText()));
        entity.setSpeed(Double.parseDouble(txtSpeed.getText()));
        entity.setDamage(Double.parseDouble(txtDamage.getText()));

    }

    @FXML
    void onSaveLevelClicked(ActionEvent event) {
        // saves the level to a file
        currentLevel.save(type);
    }

    @FXML
    void onCritterClicked(Event event, ImageView img) {
        // selects a specific entity and invokes the update stats method

        makeDraggable(img);
        selectEntity(img);
        entitySelected = img;

        // make the crittter selected invoke the updateLoc method
        ((entity) (entitySelected.getUserData())).updateLocationTable();

    }

    @FXML
    void placeEntity(ActionEvent event) {
        // places an entity on the pane


        // get the button that was clicked
        var img = new ImageView(
                ((ImageView) (((Button) event.getSource())
                        .getChildrenUnmodifiable().get(0))).getImage());
       

        enemy enemy = new enemy(5, 10, 10, 1, new Random().nextInt(780), new Random().nextInt(480));
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
                currentRoom.setStart(null);
                currentRoom.removeImage(entitySelected);
                startBtn.setDisable(false);
                deletestartptBtn.setDisable(true);

            } else if (entitySelected.getUserData() instanceof staircase) {
                // currentRoom.removeStaircase((staircase) entitySelected.getUserData());
                currentRoom.removeImage(entitySelected);
                currentRoom.setStaircase(null);
                setStairsBtn.setDisable(false);
                removeStairsBtn.setDisable(true);

            }

            entitySelected = null;
            // clear the table
            lblId.setText("");
            lblLoc.setText("");
            txthealth.setText("");
            txtSpeed.setText("");
            txtDamage.setText("");
            saveBtn.disableProperty().set(true);
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
        saveBtn.disableProperty().set(false);
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
            checkifOutofBounds((ImageView) node);

        });

        // Prevent mouse clicks on img from propagating to the pane and
        // resulting in creation of a new image
        node.setOnMouseClicked(me -> me.consume());
    }
    //this class is used to store the x and y coordinates of the mouse
    private class Delta {
        public double x;
        public double y;
    }
}
