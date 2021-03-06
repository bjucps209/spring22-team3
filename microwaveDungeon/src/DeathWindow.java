//-----------------------------------------------------------
//File:   DeathWindow.java
//Desc:   This file contains the death window information.
//-----------------------------------------------------------

import java.io.IOException;
import javax.sound.sampled.Clip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ScoreList;
import model.Scores;
import javafx.scene.control.*;

public class DeathWindow {

    @FXML
    Label scorelbl;

    @FXML
    Label deadNamelbl;

    Clip deadMusic = GameWindow.playAudio("src\\audio\\rip.wav");

    @FXML
    // initialize method, also adds the player's score to the ScoreList (and the leaderboards if it is in the top 10)
    public void initialize(String name, String score) throws IOException {
        deadNamelbl.setText("R.I.P. " + name + ", twas a valiant attempt!");
        scorelbl.setText(score);

        var lst = new ScoreList();
        lst.loadData();
        var s = new Scores(name, Integer.parseInt(score.split(" ")[1]));
        lst.addScore(s);
    }

    @FXML
    public void onReturn(ActionEvent e) throws IOException{
        deadMusic.stop();
        TitleWindow.beep();
        CharWindow.getGameStage().close();

        var loader = new FXMLLoader(getClass().getResource("TitleWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Microwave Dungeon");

        Stage titleStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        titleStage.close();
    }

    @FXML
    public void onQuitGame(ActionEvent e){
        deadMusic.stop();
        TitleWindow.beep();
        System.exit(1);
    }

}