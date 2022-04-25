import java.io.File;
import java.io.IOException;

import javax.sound.sampled.Clip;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

public class TitleWindow {

    Clip titleMusic = GameWindow.playAudio("src\\audio\\xDeviruchi - The Final of The Fantasy.wav");;

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
        //titleMusic = GameWindow.playAudio("src\\audio\\xDeviruchi - The Final of The Fantasy.wav");
        File file = new File("src\\Saves\\SavedGame.txt");
        if(!file.exists())
            load.setDisable(true);
    }

    // begins the game when the start button is pressed
    @FXML
    public void startGame(ActionEvent e) throws IOException {
        titleMusic.stop();
        beep();
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
        titleMusic.stop();
        beep();
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
        titleMusic.stop();
        beep();
        var loader = new FXMLLoader(getClass().getResource("LeaderboardWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("LeaderBoard");

        Stage titleStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        titleStage.close();
    }

    //opens the credit screen when the credits button is pressed
    @FXML
    public void onHelpClicked(ActionEvent e) throws IOException {
        titleMusic.stop();
        beep();
        var loader = new FXMLLoader(getClass().getResource("HelpWindow.fxml"));
        var scene = new Scene(loader.load());
    
        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Help");
    
        Stage titleStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        titleStage.close();
    }


    // Uses the save file and loads a game based off the values in that file
    @FXML
    void onLoadClicked(ActionEvent e) throws IOException { 
        titleMusic.stop();
        beep();
        var loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        CharWindow.setGameStage(stage);

        Stage titleStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        titleStage.close();

        GameWindow gameWindow = loader.getController();
        CharWindow.setGameWindow(gameWindow);
        gameWindow.load();
    }

    // Closes the title window
    @FXML
    void onQuitClicked(ActionEvent e) {
        beep();
        Stage titleStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        titleStage.close();
    }

    public static void beep() {
        GameWindow.playAudio("src\\audio\\MicrowaveBeep.wav");
    }
}
