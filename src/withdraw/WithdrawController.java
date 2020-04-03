/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package withdraw;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.*;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.util.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import login.loginController;
import utils.Account;
import java.sql.*;


/**
 * FXML Controller class
 *@author MTH2
 * 
 * This is the java controller for the withdraw UI page.
 * 
 * The class has the following methods:
 * **withdraw() which deducts the balance of the account by the amount in the withdraw text field
 * **confirmWithdraw() which asks the user to confirm the withdraw
 * **denyWithdraw() which denies the user of the withdraw when they have insufficient funds
 * **validateTextInput() which validates the input in the  withdraw amount text field so the user can only enter numbers
 * **makeFadeIn() which creates a fading effect into the withdraw UI page
 * **makeFadeOutIntoOptionsView which creates a fading effect when going back to the options view UI page
 * **loadOptionsView() which loads the options view UI page when the user decides to cancel the withdraw
 * **isBlankWithdrawTextField() which checks if the withdraw amount text field is blank or not
 * **disableOrEnableWithdrawButton() which disables the withdraw button when the withdraw amount text field is blank and enables it otherwise
 */
public class WithdrawController implements Initializable {
    
    @FXML
    private VBox root_vbox;
    
    @FXML
    private Label lbl_withdraw_amount;
    
    @FXML
    private TextField tf_withdraw_amount;
    
    @FXML
    private Button bt_withdraw;
    
    @FXML
    private Button bt_cancel;
   
    
    private  Stage stage;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        makeFadeIn();
            
        disableOrEnableWithdrawButton();
        
        validateTextInput();
        
    }
    
    
    
    @FXML
    void withdraw() throws SQLException {
        
        double withdraw_amount = Double.parseDouble(tf_withdraw_amount.getText());
        double current_balance = Double.parseDouble(Account.getActiveAccount().getBalance());
        double new_balance;
        
        if(withdraw_amount <= current_balance ) {
            if(confirmWithdraw()) {
                
                new_balance = current_balance - withdraw_amount;
                Account.getActiveAccount().setBalance("" + new_balance);
                
            }
                
        }
        else
            denyWithdraw(withdraw_amount);
        
    }
    
    /*prompt the user to confirm the withdraw with an alert*/
    @FXML
    private boolean confirmWithdraw() {
        
        Alert alert_confirm_withdraw = new Alert(AlertType.CONFIRMATION);
        
        ButtonType confirm_yes = new ButtonType("Yes");
        ButtonType confirm_no = new ButtonType("No");
        
        alert_confirm_withdraw.setTitle("Confirm Withdraw");
        alert_confirm_withdraw.setHeaderText("Please Confirm your withdraw");
        alert_confirm_withdraw.setContentText("Are you sure you want to withdraw " + tf_withdraw_amount.getText());
        alert_confirm_withdraw.getButtonTypes().setAll(confirm_yes, confirm_no);
        Optional<ButtonType> answer = alert_confirm_withdraw.showAndWait();
        
        if(answer.get() == confirm_yes) return true;
        else makeFadeIn();
        
        return false;
        
    }
    
    /*deny withdraw due to insuffient funds*/
    private void denyWithdraw(double withdraw_amount) {
        
        Alert alert_withdraw_denied = new Alert(AlertType.ERROR);
        
        ButtonType bt_ok = new ButtonType("OK");
        
        alert_withdraw_denied.setTitle("Insuffienct Funds");
        alert_withdraw_denied.setHeaderText("Insuffient Funds");
        alert_withdraw_denied.setContentText("You cannot withdraw " + tf_withdraw_amount + " due to insuffient funds");
        alert_withdraw_denied.getButtonTypes().setAll(bt_ok);
        
        Optional<ButtonType> response = alert_withdraw_denied.showAndWait();
        
        if(response.get() == bt_ok) makeFadeIn();
        
       
    }
    
     private void validateTextInput() {
         
         tf_withdraw_amount.setTextFormatter(new TextFormatter<> (change ->
            (change.getControlNewText().matches("([1-9][0-9]*)?")) ? change: null));
    }
     
    private void makeFadeIn(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        fade.setNode(root_vbox);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
    
    @FXML
    private void makeFadeOutIntoOptionsView(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(250));
        fade.setNode(root_vbox);
        fade.setFromValue(1);
        fade.setToValue(0);
        /*Once the transition has been made load the sign-up page*/
        fade.setOnFinished((ActionEvent event) -> {
                loadOptionsView();
        });
        fade.play();
    }
    
    @FXML
    private void loadOptionsView(){
        try {
           
            AnchorPane rootAnchorPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/optionsview/optionsview.fxml"));
            rootAnchorPane.getStylesheets().add(getClass().getResource("/assets/css/optionsview.css").toExternalForm());
            
            Scene scene = new Scene(rootAnchorPane, 720, 400);

            stage = (Stage) root_vbox.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Options Page");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /*check if the withdraw amount textfield is blank*/
    private boolean isBlankWithdrawTextField() {
    
        return tf_withdraw_amount.getText().equals("");
    
    }
    
    /*this method disables and enables the withdraw button when the withdraw amount
    text field is blank and enables the button when the text field is not blank*/
    private void disableOrEnableWithdrawButton() {
        
        //disable the withdraw button by default
        bt_withdraw.setDisable(true);
        
        StringProperty string = tf_withdraw_amount.textProperty();
        
        string.addListener((Observable ov) -> {
           
            if(isBlankWithdrawTextField()) bt_withdraw.setDisable(true);
            
            else bt_withdraw.setDisable(false);
            
        });
    }
    
}
