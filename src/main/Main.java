/*This is the class that is first executed in our program*/
package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

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
        window.show();
    }
}
