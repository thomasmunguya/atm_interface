/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balanceinquiry;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.loginController;
import utils.Account;
import utils.DatabaseHandler;

/**
 *
 * @author ZeRo
 * 
 */
public class balanceinquiryController implements Initializable {

     @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label lblBalanceInquiry;

    @FXML
    private Label lblAccInfo;

    @FXML
    private Label lblAccNo;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblAccBalance;

    @FXML
    private Button cancelBtn;
    
    Stage stage;
    private Account account;
    private loginController login;
    DatabaseHandler dbhandler;
    private Connection con = null;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private ResultSet rs = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayCalendar();
         try {
             setAccountInfo();
             setBalanceInfo();
         } catch (SQLException ex) {
             Logger.getLogger(balanceinquiryController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }    
    
    @FXML
    void close(MouseEvent event) {
        anchorPane.getScene().getWindow().hide();
    }
    
    //This method displays the date
    void displayCalendar(){
        //Display the local time with the format EEEEE, d MMMM yyyy
        LocalDate date = LocalDate.now();
        String strDate = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        lblDate.setText(strDate);
    }
    
    //Set the label text to the account information
    private void setAccountInfo() throws SQLException{
        con = DatabaseHandler.getConnection();
        
        //Query
        String sql = "SELECT * FROM atm.account WHERE account_number = ?;";
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, loginController.sucessfulAccountNo);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                lblAccNo.setText(loginController.sucessfulAccountNo);
            }
        } catch (SQLException ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Your Account Information Request Caused An Error!");
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(sw.toString())));
            alert.showAndWait();
        }
    }
    
    //Set the label text to the balance remaing in the account
    private void setBalanceInfo() throws SQLException{
        con = DatabaseHandler.getConnection();
        
        //Query
        String sql = "SELECT balance FROM atm.account WHERE account_number = ?;";
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, loginController.sucessfulAccountNo);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                lblAccBalance.setText(rs.getString("balance"));
            }
        } catch (SQLException ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Your Balance Inquiry Request Caused An Error!");
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(sw.toString())));
            alert.showAndWait();
        }
    }
}