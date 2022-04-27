//-----------------------------------------------------------
//File:   GUIApplication.java
//Desc:   This file holds the GUIApplication class to open the TitleWindow.
//----------------------------------------------------------- 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

//This is the class that opens the TitleWindow.
public class GUIApplication extends Application{

    @Override
    public void start(Stage stage) throws Exception {

        // Fix weird font issue in dialog boxes on Macs
        // Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);        
        // com.sun.javafx.css.StyleManager.getInstance().addUserAgentStylesheet("MainWindow.css");
        
        var loader = new FXMLLoader(getClass().getResource("TitleWindow.fxml"));
        var scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setTitle("LevelBuilder"); // Title of main window
        stage.show();
    }

}
