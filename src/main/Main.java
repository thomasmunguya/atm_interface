/*This is the class that is first executed in our program*/
package main;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    
    private Stage window;
    
     public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        initUI(window);
    }

    /*The method that loads up our first Scene in the program
      This is what the user will first see in our program so it should load the login.fxml file*/
    public void initUI(Stage window) throws IOException{
        BorderPane borderPane = FXMLLoader.<BorderPane>load(getClass().getResource("/login/login.fxml"));
        borderPane.getStylesheets().add(getClass().getResource("/assets/css/login.css").toExternalForm());
        Scene scene = new Scene(borderPane,650, 400);

        window.setScene(scene);
        window.setTitle("Log-in Page");
        window.setResizable(false);
        window.centerOnScreen();
        window.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Request");
            alert.setHeaderText(null);
            alert.setContentText("Äre you sure you want to exit the program?");
                
            ButtonType okButtonType = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == okButtonType){
                window.close();
            }else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                e.consume();
            }
            });
        window.show();
    }
}
