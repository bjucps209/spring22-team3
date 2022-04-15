import java.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PauseMenu {

    @FXML Button saveButton;
    
    // Resumes the game
    @FXML
    void onResumeClicked(ActionEvent e) {
        // TODO: code to unpause once pauseing function is implemented*
        Stage pauseStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        pauseStage.close();
    }

    // TODO: Saves the game
    @FXML
    void onSaveClicked(ActionEvent e) throws IOException {
        File file = new File("microwaveDungeonSaveFile.txt");
        if(file.exists())
            file.delete();
        file.createNewFile();
        try(DataOutputStream writer = new DataOutputStream(new FileOutputStream(file))) {
            try {CharWindow.getGameWindow().getGame().save();
            saveButton.setText("Saved");
            saveButton.setDisable(true); }
            catch (Exception ex) {}
        }
    }

    // Closes game and opens main menu/restarts game by opening the title menu
    @FXML
    void onQuitMainMenuClicked(ActionEvent e) throws IOException {
        CharWindow.getGameStage().close();
        
        var loader = new FXMLLoader(getClass().getResource("TitleWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Microwave Dungeon");

        Stage pauseStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        pauseStage.close();
    }

    // Closes entire game
    @FXML
    void onQuitDesktopClicked(ActionEvent e) {
        CharWindow.getGameStage().close();
        Stage pauseStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        pauseStage.close();
    }
}
