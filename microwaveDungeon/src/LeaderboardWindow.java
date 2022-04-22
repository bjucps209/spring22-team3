import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.ScoreList;
import model.Scores;

public class LeaderboardWindow {

    ArrayList<Scores> scoreList;

    @FXML
    Label score0, score1, score2, score3, score4, score5, score6, score7, score8, score9;

    @FXML
    Label name0, name1, name2, name3, name4, name5, name6, name7, name8, name9;

    @FXML
    void initialize() throws IOException {
        ScoreList List = new ScoreList();
        scoreList = List.getScoreList();

        // System.out.println(scoreList.size());
        // for (Scores sc : scoreList) {
        //     System.out.println(sc.getName());
        // }
        
        for (int i = 0; i < scoreList.size(); ++i) {
            Scores s = scoreList.get(i);
            if (i > scoreList.size()) {
                return;
            }
            if (i == 0) {
                name0.setText("\t" + s.getName());
                score0.setText(s.getScore() + "");
            } else if (i == 1) {
                name1.setText("\t" + s.getName());
                score1.setText(s.getScore() + "");
            } else if (i == 2) {
                name2.setText("\t" + s.getName());
                score2.setText(s.getScore() + "");
            } else if (i == 3) {
                name3.setText("\t" + s.getName());
                score3.setText(s.getScore() + "");
            } else if (i == 4) {
                name4.setText("\t" + s.getName());
                score4.setText(s.getScore() + "");
            } else if (i == 5) {
                name5.setText("\t" + s.getName());
                score5.setText(s.getScore() + "");
            } else if (i == 6) {
                name6.setText("\t" + s.getName());
                score6.setText(s.getScore() + "");
            } else if (i == 7) {
                name7.setText("\t" + s.getName());
                score7.setText(s.getScore() + "");
            } else if (i == 8) {
                name8.setText("\t" + s.getName());
                score8.setText(s.getScore() + "");
            } else if (i == 9) {
                name9.setText("\t" + s.getName());
                score9.setText(s.getScore() + "");
            }
        }
    }

    // goes back to previous screen
    @FXML
    void onBackClicked(ActionEvent event) throws IOException {
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
