import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
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
    void initialize() {

    }



    //Hey Daniel, I went ahead and made the buttons work so that they load the other windows




    // begins the game when the start button is pressed
    @FXML
    public void startGame(ActionEvent e) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("CharWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Character select");

                
        Stage titleStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        titleStage.close();
    }

    //opens the credit screen when the credits button is pressed
    @FXML
    public void onCreditsClicked(ActionEvent e) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("CreditsWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Credits");

        Stage titleStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        titleStage.close();
    }

    //opens the Leaderboard screen when the leaderboard button is pressed
    @FXML
    public void onLeaderboardClicked(ActionEvent e) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("LeaderboardWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("LeaderBoard");

        Stage titleStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        titleStage.close();
    }


    // Uses the save file and loads a game based off the values in that file
    @FXML
    void onLoadClicked(ActionEvent e) {
        throw new RuntimeException("Method not implemented");
    }

    // Identical to onLoadClicked, but returns the level object for testing
    static Level loadSave(String fileName) {
        throw new RuntimeException("Method not implemented");
    }



}
