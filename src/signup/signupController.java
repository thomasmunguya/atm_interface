package signup;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;

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
    
    private Stage stage;
    private DatabaseHandler dbHandler;
    private Connection con = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbHandler = new DatabaseHandler();
        addListeners();
    }

    @FXML 
    void cancelIconClick(MouseEvent event) {
        makeFadeOutIntoLogin();
    }

    @FXML
    void saveIconClick(MouseEvent event) {
        boolean checkFields = areFieldsEmpty();
        
        if(checkFields == true){
            saveIcon.setDisable(true);
        }else{ 
            saveIcon.setDisable(false);
            
            String genAccNum = generateBankAccount();
            String pin = generatePin();
            
            boolean savedInfo = insertUserInfo(genAccNum, pin);
            if(savedInfo == true){
                clearTxtFields();
                infoBox("Your Information has been saved! \n Your Bank Account Number is : " + genAccNum + " \n Your Personal Identification Number is : " + pin , "Information Saved");  
            }
            makeFadeOutIntoLogin();
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
 
    private boolean insertUserInfo(String genAccNum, String pin){
        //Create a Connection to be able to insert data into the mysql db
        con = DatabaseHandler.getConnection();

        String TABLE_NAME1 = "atm.account_holder";
        //MySQL Query
        String sql1 = "INSERT INTO " + TABLE_NAME1 + " (nrc_number, first_name, last_name, email, dob, nationality) VALUES (?,?,?,?,?,?)";
        try {
            stmt = con.prepareStatement(sql1);
            stmt.setString(1, txtNrcNumber.getText().trim());
            stmt.setString(2, txtFirstName.getText().trim());
            stmt.setString(3, txtLastName.getText().trim());
            stmt.setString(4, txtEmail.getText().trim());
            stmt.setDate(5, java.sql.Date.valueOf(dob.getValue()));
            stmt.setString(6, txtNationality.getText().trim());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Before we can insert the account number into the db we need to check if the account number has already been generated
        String sql2 = "SELECT * FROM atm.account WHERE account_number = ?;";
        try {
            stmt = con.prepareStatement(sql2);
            stmt.setString(1, genAccNum);
            rs = stmt.executeQuery();
            if(rs.next()){
                //If the generated account number has results then generate another account number
                genAccNum = generateBankAccount();
            }else{
                //Insert the generated account number and pin into the account table
                String TABLE_NAME2 = "atm.account";
                String sql3 = "INSERT INTO " + TABLE_NAME2 + " (account_number, pin, balance, holder_nrc_number) VALUES (?,?,?,?)";
                try {
                    stmt = con.prepareStatement(sql3);
                    stmt.setString(1, genAccNum);
                    stmt.setString(2, pin);
                    stmt.setInt(3, 0);
                    stmt.setString(4, txtNrcNumber.getText().trim());
                    stmt.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            }catch (SQLException ex) {
                 Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
            }    
        return true;
    }
    
    //A method to generate a random 11-digit integer
    private static String generateBankAccount(){
        Random rnd = new Random();
        String accountNumber = "";
        for (int i = 0; i < 11; i++) {
            int randomNum = rnd.nextInt(10);
            accountNumber += Integer.toString(randomNum);
        }
        return accountNumber;
    }
    
    //A method to generate a random 4-digit integer
    private static String generatePin(){
        Random rnd = new Random();
        String pin = "";
        for (int i = 0; i < 4; i++) {
            int randomNum = rnd.nextInt(10);
            pin += Integer.toString(randomNum);
        }
        return pin;
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
    private static void infoBox(String contentText, String title){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(contentText);
        alert.setTitle(title);
        alert.setHeaderText(null);
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

