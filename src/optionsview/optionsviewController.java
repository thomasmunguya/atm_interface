package optionsview;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import login.loginController;

public class optionsviewController implements Initializable {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private HBox topHBox;

    @FXML
    private HBox botHBox;

    @FXML
    private Button depositBtn;
    
    @FXML
    private Button cancelBtn;

    private Stage stage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeFadeIn();
    } 
    
    @FXML
    void depositBtn(MouseEvent event) {

    }
    
    @FXML
    void cancelButton(MouseEvent event) {
        makeFadeOutIntoloadLogin();
    }
    
    //This method loads the sign-up page
    private void loadLogin(){
        try {
            BorderPane borderPane = FXMLLoader.<BorderPane>load(getClass().getResource("/login/login.fxml"));
            borderPane.getStylesheets().add(getClass().getResource("/assets/css/login.css").toExternalForm());
            Scene scene = new Scene(borderPane, 650, 400);
            
            stage = (Stage) rootAnchorPane.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Log-in Page");
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //This method loads the sign-up page
    private void loadDeposit(){
        try {
            BorderPane borderPane = FXMLLoader.<BorderPane>load(getClass().getResource("/login/login.fxml"));
            borderPane.getStylesheets().add(getClass().getResource("/assets/css/login.css").toExternalForm());
            Scene scene = new Scene(borderPane, 650, 400);
            
            stage = (Stage) rootAnchorPane.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Log-in Page");
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void makeFadeIn(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        fade.setNode(rootAnchorPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
    
    //A method to test using Transition animations into the sign-up page
    private void makeFadeOutIntoloadLogin(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(250));
        fade.setNode(rootAnchorPane);
        fade.setFromValue(1);
        fade.setToValue(0);
        //Once the transition has been made load the sign-up page
        fade.setOnFinished((ActionEvent event) -> {
                loadLogin();
        });
        fade.play();
    }
    
    //A method to test using Transition animations into the sign-up page
    private void makeFadeOutIntoDeposit(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(250));
        fade.setNode(rootAnchorPane);
        fade.setFromValue(1);
        fade.setToValue(0);
        //Once the transition has been made load the sign-up page
        fade.setOnFinished((ActionEvent event) -> {
                loadLogin();
        });
        fade.play();
    }
}
