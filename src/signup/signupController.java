/*This is the sign-up controller which links the sign-up gui to its business logic*/
package signup;

import login.loginController;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class signupController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Pane leftSignupPane;

    @FXML
    private ImageView saveIcon;

    @FXML
    private ImageView cancelIcon;

    @FXML
    private ChoiceBox choiceBox;

    private Stage window;

    /*This is the first method to be executed when the class is initialised*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChoiceBoxOptions();
    }

     @FXML
    void handleIconClick(MouseEvent event) {
        makeFadeOutIntoLogin();
    }
    
    /*Load the Login form*/
    void loadLogin(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/login/login.fxml"));
            root.getStylesheets().add(getClass().getResource("/assets/css/login.css").toExternalForm());
            Scene scene = new Scene(root, 650, 400);
            Stage stage = (Stage)borderPane.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Log-in Page");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /*Makes the choice box display a list of countries*/
    private void setChoiceBoxOptions(){
        ObservableList<String> cities = FXCollections.observableArrayList();
        String[] locales1 = Locale.getISOCountries();
        for(String countryList : locales1){
            Locale obj = new Locale("", countryList);
            String[] city = {obj.getDisplayCountry()};
            for (String city1 : city) {
                cities.add(obj.getDisplayCountry());
            }
            choiceBox.setItems(cities);
        }
    }
    
    /*A method to test using Transition animations into the login page*/
    private void makeFadeOutIntoLogin(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(250));
        fade.setNode(borderPane);
        fade.setFromValue(1);
        fade.setToValue(0);
        /*Once the transition has been made load the login page*/
        fade.setOnFinished((ActionEvent event) -> {
            loadLogin();
        });
        fade.play();
    }

}
