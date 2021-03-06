//-----------------------------------------------------------
//File:   PauseMenu.java
//Desc:   This file contains code to run the Pause Menu
//-----------------------------------------------------------
import java.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PauseMenu {

    @FXML Button saveButton;
    
    // Resumes the game
    @FXML
    void onResumeClicked(ActionEvent e) {
        TitleWindow.beep();
        CharWindow.getGameWindow().resume();
        Stage pauseStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        pauseStage.close();
    }

    // Saves the game
    @FXML
    void onSaveClicked(ActionEvent e) throws IOException {
        TitleWindow.beep();
        File file = new File("microwaveDungeonSaveFile.txt");
        if(file.exists())
            file.delete();
        file.createNewFile();
        try(var writer = new PrintWriter(new FileWriter(file))) {
            try {CharWindow.getGameWindow().getGame().save();
                saveButton.setText("Saved");
                saveButton.setDisable(true); 
                CharWindow.getGameWindow().getPlayer().save(writer); }
            catch (Exception ex) { 
                Alert a = new Alert(AlertType.ERROR, "There was a problem saving the game.");
                a.show();
                ex.printStackTrace();}
        }
    }

    // Closes game and opens main menu/restarts game by opening the title menu
    @FXML
    void onQuitMainMenuClicked(ActionEvent e) throws IOException {
        TitleWindow.beep();
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
        TitleWindow.beep();
        CharWindow.getGameStage().close();
        Stage pauseStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        pauseStage.close();
    }
}
