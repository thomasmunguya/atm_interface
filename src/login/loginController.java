/*This is the login controller which links the login gui to its business logic*/
package login;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.DatabaseHandler;

public class loginController extends DatabaseHandler implements Initializable{
    @FXML
    private BorderPane rootPane;

    @FXML
    private Pane leftPane;

    @FXML
    private Label lblSlogan;

    @FXML
    private Label lblName;

    @FXML
    private Pane rightPane;

    @FXML
    private PasswordField txtPin;

    @FXML
    private Button signinBtn;

    @FXML
    private Label lblError;

    @FXML
    private Button signupBtn;

    @FXML
    private Label lblSignIn;

    @FXML
    private TextField txtAccNo;

    @FXML
    private Label dateAndTimeLbl;

    private Parent root;
    private Stage stage;
    private DatabaseHandler dbHandler;
    private Connection con = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    /*This is the first method to be executed when the class is initialised*/
    @Override
    public void initialize(URL location, ResourceBundle resources){
        /*When the class is initialised the dbHandler also has to be initialised*/
        dbHandler = new DatabaseHandler();
        displayCalendar();
        //experimental method makeFadeIn();
    }

     @FXML
    void loadSignIn(MouseEvent event) {
        signIn();
    }

    @FXML
    void handleButtonClick(MouseEvent event) {
        makeFadeOutIntoSignUp();
    }
    
    /*Method to signIn into the db or display an error msg if an unsuccessful attempt is made
      The method has to be changed in the final project because this method was only used for testing purposes*/
    private void signIn(){
        con = DatabaseHandler.createConnection();
        String username = txtAccNo.getText();
        String password = txtPin.getText();
        
        //Query
        String sql = "SELECT * FROM user_login WHERE username = ? and password = ?;";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if(rs.next()) {
                makeFadeOutIntoOptions();
            }else{
                lblError.setTextFill(javafx.scene.paint.Color.RED);
                lblError.setText("Incorrect Username/Password...");
            }
        } catch (SQLException ex) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*This method loads the Options View*/
    private void loadOptionsView(){
        try {
            AnchorPane anchorPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/optionsview/optionsview.fxml"));
            anchorPane.getStylesheets().add(getClass().getResource("/assets/css/optionsview.css").toExternalForm());
            Scene scene = new Scene(anchorPane, 720, 400);

            stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Options Page");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /*This method loads the sign-up page*/
    private void loadSignUp(){
        try {
            BorderPane borderPane = FXMLLoader.<BorderPane>load(getClass().getResource("/signup/signup.fxml"));
            borderPane.getStylesheets().add(getClass().getResource("/assets/css/signup.css").toExternalForm());
            Scene scene = new Scene(borderPane, 720, 400);
            
            stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sign-Up Page");
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        }
    }
    
    /*This method displays the calemdar in the top right of the login page*/
    void displayCalendar(){
        //Display the local time with the format EEEEE, d MMMM yyyy
        LocalDate date = LocalDate.now();
        String strDate = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        System.out.println(strDate);
        dateAndTimeLbl.setText(strDate);
    }
    
    /*A method to test using Transition animations*/
    private void makeFadeIn(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        fade.setNode(rootPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
    
    /*A method to test using Transition animations into the sign-up page*/
    private void makeFadeOutIntoSignUp(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(250));
        fade.setNode(rootPane);
        fade.setFromValue(1);
        fade.setToValue(0);
        /*Once the transition has been made load the sign-up page*/
        fade.setOnFinished((ActionEvent event) -> {
                loadSignUp();
        });
        fade.play();
    }
    
    /*A method to test using Transition animations into the options page*/
    private void makeFadeOutIntoOptions(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(250));
        fade.setNode(rootPane);
        fade.setFromValue(1);
        fade.setToValue(0);
        /*Once the transition has been made load the options page*/
        fade.setOnFinished((ActionEvent event) -> {
                loadOptionsView();
        });
        fade.play();
    }
}
