import java.io.IOException;

import javax.accessibility.AccessibleRelation;
import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Game;
import model.characters;

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

    Game game = new Game();

    //is called when the pizzaChar button is pressed
    @FXML
    public void selectPizzaCharacter(ActionEvent e){
        game.getPlayer().setCharacter(characters.PIZZA);
    }

    //is called when the HotPocketChar button is pressed
    @FXML
    public void selectHPocketCharacter(ActionEvent e){
        game.getPlayer().setCharacter(characters.HPOCKET);
    }

    //is called when the ramenChar button is pressed
    @FXML
    public void selectRamenCharacter(ActionEvent e){
        game.getPlayer().setCharacter(characters.RAMEN);
    }

    //is called when the macChar button is pressed
    @FXML
    public void selectMacCharacter(ActionEvent e){
        game.getPlayer().setCharacter(characters.MAC);
    }



    @FXML
    void onBackClicked(ActionEvent event) throws IOException{
        var loader = new FXMLLoader(getClass().getResource("TitleWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setScene(scene);
        stage.show();

        Stage titleStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        titleStage.close();
    }

    @FXML
    public void onStartClicked(){
        
    }
}
