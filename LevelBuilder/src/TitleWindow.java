import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class TitleWindow {
    @FXML ChoiceBox<String> levelCB;
    @FXML 
    void initialize() {
        File dir = new File("../microwaveDungeon/src/Levels");
        int level;
        File[] directoryListing = dir.listFiles();
        for (File file : directoryListing) {
            String[] list = file.toString().split("\\\\");

            level = Integer.parseInt(list[4].split("\\.")[0]);
            levelCB.getItems().add(Integer.toString(level));

        }
        
    }
    @FXML
    void newGame(ActionEvent event) throws IOException {
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = loader.load();
        MainWindow controller = loader.getController();
        controller.initialize(0, "new");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setTitle("LevelBuilder");
    }

    @FXML
    void onLoadClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setHeaderText("Load level " + levelCB.getValue() + "?");
        alert.setTitle("Load Level");

        if (alert.showAndWait().get() == ButtonType.OK) {
            var loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        var scene = new Scene(loader.load());


        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        MainWindow gameWindow = loader.getController();
        int level = Integer.parseInt(levelCB.getValue());
        gameWindow.initialize(level, "old");
            stage.setTitle("LevelBuilder");
            
         

            
            
    
                    
            Stage titleStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            titleStage.close();

        }
    }
}
