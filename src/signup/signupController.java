package signup;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import login.loginController;
import utils.DatabaseHandler;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import utils.Account;
import utils.AccountHolder;

public class signupController implements Initializable{

    @FXML
    private BorderPane rootPane;

    @FXML
    private Pane leftSignupPane;

    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXTextField txtNrcNumber;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private DatePicker dob;

    @FXML
    private JFXTextField txtNationality;

    @FXML
    private ImageView saveIcon;

    @FXML
    private Label lblName;
     
    @FXML
    private ImageView cancelIcon;
    
    private Account account;
    private AccountHolder accountHolder;
    private Stage stage;
    private DatabaseHandler dbHandler;
    private Connection con = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbHandler = new DatabaseHandler();
        accountHolder = new AccountHolder();
        try {
            account = new Account();
        } catch (SQLException ex) {
            Logger.getLogger(signupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addListeners();
    }

    @FXML 
    void cancelIconClick(MouseEvent event) {
        makeFadeOutIntoLogin();
    }

    @FXML
    void saveIconClick(MouseEvent event) throws SQLException {
        boolean checkFields = areFieldsEmpty();
        
        if(checkFields == true){
            saveIcon.setDisable(true);
        }else{ 
            saveIcon.setDisable(false);
            accountHolder.setNrcNumber(txtNrcNumber.getText().trim());
            accountHolder.setFirstName(txtFirstName.getText().trim());
            accountHolder.setLastName(txtLastName.getText().trim());
            accountHolder.setEmail(txtEmail.getText().trim());
            accountHolder.setDob(java.sql.Date.valueOf(dob.getValue()));
            accountHolder.setNationality(txtNationality.getText().trim());
            
            account.setNrcNumber(txtNrcNumber.getText().trim());
            infoBox("Information Saved","Your information has been saved.", "Full name : " + accountHolder.getFirstName() + " " + accountHolder.getLastName() + "\nYour Bank Account Number : " + account.getAccNumber() + "\nYour Personal Identification Number : " + account.getPin());
            clearTxtFields();
        }
        
        
    }

    /*Makes the choice box display a list of countries
    private static void setChoiceBoxOptions(){
        ObservableList<String> cities = FXCollections.observableArrayList();
        comboBox = new ComboBox<>(cities);
        
        String[] locale = Locale.getISOCountries();
        for (String countryList : locale) {
            Locale obj = new Locale("", countryList);
            String[] city = { obj.getDisplayCountry() };
            for (String city1 : city) {
                cities.add(obj.getDisplayCountry());
            }
            comboBox.setItems(cities);
        }
    }*/
    
    //A method to test using Transition animations into the login page
    private void makeFadeOutIntoLogin(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(250));
        fade.setNode(rootPane);
        fade.setFromValue(1);
        fade.setToValue(0);
        //Once the transition has been made load the login page
        fade.setOnFinished((ActionEvent event) -> {
            loadLogin();
        });
        fade.play();
    }
    
    //Load the Login form
    private void loadLogin(){
        try {
            BorderPane root = FXMLLoader.<BorderPane>load(getClass().getResource("/login/login.fxml"));
            root.getStylesheets().add(getClass().getResource("/assets/css/login.css").toExternalForm());
            Scene scene = new Scene(root, 650, 400);
            
            stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Log-in Page");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    //Check to see if any of the textfields are empty
    private boolean areFieldsEmpty(){
        return txtNrcNumber.getText().trim() == null || txtFirstName.getText().trim() == null || txtLastName.getText().trim() == null || txtEmail.getText().trim() == null || dob.getValue() == null || txtNationality.getText().trim() == null;
    }
    
    //Adding listeners to all the fields on the sign up page
    private void addListeners(){
        
        txtFirstName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(newValue.matches("([a-zA-Z]*)?")){
                txtFirstName.setText(newValue);
            }else{
                txtFirstName.setText(oldValue);
            }
        });
        
        txtLastName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(newValue.matches("([a-zA-Z]*)?")){
                txtLastName.setText(newValue);
            }else{
                txtLastName.setText(oldValue);
            }
        });
        
        txtNrcNumber.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(newValue.matches("([\\d_\\W]*)?")){
                txtNrcNumber.setText(newValue);
            }else{
                txtNrcNumber.setText(oldValue);
            }
        });
        
        txtEmail.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(newValue.matches("([a-zA-Z_\\d_\\W]*)?")){
                txtEmail.setText(newValue);
            }else{
                txtEmail.setText(oldValue);
            }
        });
        
        txtNationality.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(newValue.matches("([a-zA-Z]*)?")){
                txtNationality.setText(newValue);
            }else{
                txtNationality.setText(oldValue);
            }
        });
    }
   
    //Clear all the fields
    private void clearTxtFields(){
        txtNrcNumber.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        dob.valueProperty().set(null);
        txtNationality.clear();
    }
    
    //Method to display an alert box to inform the user of confirmation
    private static void infoBox(String title, String header, String contentText){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(contentText);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
    
    //Method to display a customised alert box to inform the user
    private static void showAlert(AlertType alertType, Stage window, String message, String title){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initOwner(window);
        alert.show();
    }
}

