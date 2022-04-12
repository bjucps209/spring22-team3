import java.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class PauseMenu {
    
    // TODO: Resumes the game
    @FXML
    void onResumeClicked(ActionEvent e) {
    }

    // TODO: Saves the game
    @FXML
    void onSaveClicked(ActionEvent e) throws IOException{
        File file = new File("microwaveDungeonSaveFile.txt");
        if(file.exists())
            file.delete();
        file.createNewFile();
        try(DataOutputStream writer = new DataOutputStream(new FileOutputStream(file))) {
            
        }
    }

    // TODO: Closes game and opens main menu/restarts game
    @FXML
    void onQuitMainMenuClicked(ActionEvent e) {
    }

    // TODO: Closes entire game
    @FXML
    void onQuitDesktopClicked(ActionEvent e) {
    }
}
