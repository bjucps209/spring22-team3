import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Game;
import model.characters;
import model.difficulties;

public class CharWindow {
    
    @FXML
    Button pizzaCharacter;

    @FXML
    Button hotPocketCharacter;

    @FXML
    Button ramenCharacter;

    @FXML
    Button macCharacter;

    @FXML
    TextField nameInput;

    private characters character;

    private difficulties diff;

    //sets the character variable to PIZZA which is passed into the gameWindow initialize method
    @FXML
    public void selectPizzaCharacter(ActionEvent e){
        character = characters.PIZZA;
    }

    //sets the character variable to HPOCKET which is passed into the gameWindow initialize method
    @FXML
    public void selectHPocketCharacter(ActionEvent e){
        character = characters.HPOCKET;
    }

    //sets the character variable to RAMEN which is passed into the gameWindow initialize method
    @FXML
    public void selectRamenCharacter(ActionEvent e){
        character = characters.RAMEN;
    }

    //sets the character variable to MAC which is passed into the gameWindow initialize method
    @FXML
    public void selectMacCharacter(ActionEvent e){
        character = characters.MAC;
    }

    //sets the diff variable to EASY, which is then passed into the GameWindow initialize method
    @FXML
    public void selectEasy(ActionEvent e){
        diff = difficulties.EASY;
    }

    //sets the diff variable to MEDIUM, which is then passed into the GameWindow initialize method
    @FXML
    public void selectMedium(ActionEvent e){
        diff = difficulties.MEDIUM;
    }

    //sets the diff variable to HARD, which is then passed into the GameWindow initialize method
    @FXML
    public void selectHard(ActionEvent e){
        diff = difficulties.HARD;
    }

    //sets the diff variable to NUKE, which is then passed into the GameWindow initialize method
    @FXML
    public void selectNuke(ActionEvent e){
        diff = difficulties.NUKE;
    }





    @FXML
    void onBackClicked(ActionEvent e) throws IOException{
        var loader = new FXMLLoader(getClass().getResource("TitleWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();

        Stage titleStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        titleStage.close();
    }

    public void onStartClicked(ActionEvent e) throws IOException{
        var loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();

        Stage titleStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        titleStage.close();

        GameWindow gameWindow = loader.getController();
        gameWindow.initialize(diff, character);
    }
}
