import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.ScoreList;
import model.Scores;

public class LeaderboardWindow {

    @FXML
    Label score1, score2, score3, score4, score5, score6, score7, score8, score9, score10; // tenetive naming system, seems clunky so this may get simplified

    void initialize() {
        ScoreList List = new ScoreList();
        var scoreList = List.getScoreList();
        // make sure to check to see if the list is right, if it isnt, rewrite the data from the file onto the scoreList
        for ( int i = 0; i < scoreList.size(); ++i ) {
            Scores s = scoreList.get(i);
            // iterate over labels and insert name and score to each
        }
    }

    // goes back to previous screen
    @FXML
    void onBackClicked(ActionEvent event) throws IOException{
        var loader = new FXMLLoader(getClass().getResource("TitleWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Microwave Dungeon");

        Stage titleStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        titleStage.close();
    }
}
