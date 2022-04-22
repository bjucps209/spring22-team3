import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Game;
import model.characters;
import model.difficulties;

public class CharWindow {

    static Stage gameStage; // Added to allow the pause window to close the game window

    static GameWindow staticGameWindow; // Added to allow saving from pause menu

    @FXML
    Button pizzaCharacter;

    @FXML
    Button hotPocketCharacter;

    @FXML
    Button ramenCharacter;

    @FXML
    Button macCharacter;

    @FXML
    TextField nameInput;

    @FXML
    Label errorLabel;

    private characters character;

    private difficulties diff;

    // sets the character variable to PIZZA which is passed into the gameWindow
    // initialize method
    @FXML
    public void selectPizzaCharacter(ActionEvent e) {
        character = characters.PIZZA;
    }

    // sets the character variable to HPOCKET which is passed into the gameWindow
    // initialize method
    @FXML
    public void selectHPocketCharacter(ActionEvent e) {
        character = characters.HPOCKET;
    }

    // sets the character variable to RAMEN which is passed into the gameWindow
    // initialize method
    @FXML
    public void selectRamenCharacter(ActionEvent e) {
        character = characters.RAMEN;
    }

    // sets the character variable to MAC which is passed into the gameWindow
    // initialize method
    @FXML
    public void selectMacCharacter(ActionEvent e) {
        character = characters.MAC;
    }

    // sets the diff variable to EASY, which is then passed into the GameWindow
    // initialize method
    @FXML
    public void selectEasy(ActionEvent e) {
        diff = difficulties.EASY;
    }

    // sets the diff variable to MEDIUM, which is then passed into the GameWindow
    // initialize method
    @FXML
    public void selectMedium(ActionEvent e) {
        diff = difficulties.MEDIUM;
    }

    // sets the diff variable to HARD, which is then passed into the GameWindow
    // initialize method
    @FXML
    public void selectHard(ActionEvent e) {
        diff = difficulties.HARD;
    }

    // sets the diff variable to NUKE, which is then passed into the GameWindow
    // initialize method
    @FXML
    public void selectNuke(ActionEvent e) {
        diff = difficulties.NUKE;
    }

    @FXML
    void onBackClicked(ActionEvent e) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("TitleWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Microwave Dungeon");

        Stage titleStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        titleStage.close();
    }

    public void onStartClicked(ActionEvent e) throws IOException{
        if ((character != null) && (diff != null)) {
            var loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        gameStage = stage;
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Welcome to the Dungeon!");

        Stage titleStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        titleStage.close();

        GameWindow gameWindow = loader.getController();
        gameWindow.initialize(diff, character);
        staticGameWindow = gameWindow;
        } else{
            errorLabel.setText("Please select a character AND a  difficulty!");
        }
        
    }

    public static Stage getGameStage() {
        return gameStage;
    }

    public static void setGameStage(Stage s) {
        gameStage = s;
    }

    public static GameWindow getGameWindow() {
        return staticGameWindow;
    }

    public static void setGameWindow(GameWindow input) {
        staticGameWindow = input;
    }
}
