//-----------------------------------------------------------
//File:   HelpWindow.java
//Desc:   This file contains the Help Window information.
//-----------------------------------------------------------

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class HelpWindow {

    @FXML
    void onBackClicked(ActionEvent event) throws IOException{
        TitleWindow.beep();
        var loader = new FXMLLoader(getClass().getResource("TitleWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("MicrowaveDungeon");

        Stage titleStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        titleStage.close();
    }
    
}
