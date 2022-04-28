//-----------------------------------------------------------
//File:   pUpWindow.java
//Desc:   This file contains the power-up window information.
//-----------------------------------------------------------

import java.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.player;

public class pUpWindow {

    static player p;

    static void setPlayer(player input) {
        p = input;
    }
    
    // Shield Power-Up
    @FXML
    void onShieldClicked(ActionEvent e) {
        TitleWindow.beep();
        p.upgradeShield();
        CharWindow.getGameWindow().resume();
        Stage pauseStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        pauseStage.close();
    }

    // Speed Power-Up
    @FXML
    void onSpeedClicked(ActionEvent e) throws IOException {
        TitleWindow.beep();
        p.upgradeSpeed();
        CharWindow.getGameWindow().resume();
        Stage pauseStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        pauseStage.close();
    }

    // Firerate Power-up
    @FXML
    void onFRateClicked(ActionEvent e) throws IOException {
        TitleWindow.beep();
        p.upgradeFireRate();
        CharWindow.getGameWindow().resume();
        Stage pauseStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        pauseStage.close();
    }
}
