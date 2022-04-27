import java.io.IOException;
import javax.sound.sampled.Clip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.*;

public class DeathWindow {

    @FXML
    Label scorelbl;

    @FXML
    Label deadNamelbl;

    Clip deadMusic = GameWindow.playAudio("src\\audio\\rip.wav");

    @FXML
    public void initialize(String name, String score){
        deadNamelbl.setText("R.I.P. " + name + ", twas a valiant attempt!");
        scorelbl.setText(score);

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