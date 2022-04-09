import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    public void onBackClicked() {
        throw new RuntimeException("Method not implemented");
    }
}
