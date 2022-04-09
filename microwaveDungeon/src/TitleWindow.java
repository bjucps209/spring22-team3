import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import model.*;

public class TitleWindow {

    @FXML
    Button start;

    @FXML
    Button load;

    @FXML
    Button credits;

    @FXML
    Button leaderboard;

    @FXML
    Button hardDifficulty;

    @FXML
    Button medDifficulty;

    @FXML
    Button easyDifficulty;

    // Checks if there is a save file, if not, disables load button
    @FXML
    void initialize() {}

    // begins the game when the start button is pressed
    @FXML
    public void startGame() {
        throw new RuntimeException("Method not implemented");
    }

    // Uses the save file and loads a game based off the values in that file
    @FXML
    void onLoadClicked(ActionEvent event) {
        throw new RuntimeException("Method not implemented");
    }

    // Identical to onLoadClicked, but returns the level object for testing
    static Level loadSave() {
        throw new RuntimeException("Method not implemented");
    }

    // sets the difficulty of the game in the model depending on the difficulty
    // selected by the player
    @FXML
    public void setDifficulty() {
        throw new RuntimeException("Method not implemented");
    }

    //opens the credit screen when the credits button is pressed
    @FXML
    public void openCredits() {
        throw new RuntimeException("Method not implemented");
    }

    //opens the Leaderboard screen when the leaderboard button is pressed
    @FXML
    public void showLeaderboard() {
        throw new RuntimeException("Method not implemented");
    }

}
