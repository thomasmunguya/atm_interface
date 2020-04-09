package balancetransfer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.loginController;
import utils.Account;
import utils.DatabaseHandler;
import utils.Transaction;

/**
 *
 * @author ZeRo
 * 
 */
public class balancetransferController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label lblBalanceTransfer;

    @FXML
    private Label lblAccInfo;

    @FXML
    private Label lblAmount;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField txtRecipientAcc;

    @FXML
    private Label lblAmount1;

    @FXML
    private TextField txtTransferAmount;

    @FXML
    private Label lblAccNo;
    
    Stage stage;
    private Account account;
    private Transaction transaction;
    private loginController login;
    DatabaseHandler dbhandler;
    private Connection con = null;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private ResultSet rs = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setAccountInfo();
        } catch (SQLException ex) {
            Logger.getLogger(balancetransferController.class.getName()).log(Level.SEVERE, null, ex);
        }
        dbhandler = new DatabaseHandler();
        addListeners();
    }    
    
     @FXML
    void confirmBox(MouseEvent event) throws SQLException {
        boolean checkFields = areFieldsEmpty();
        
        if(checkFields == true){
            confirmBtn.setDisable(true);
        }else{
            confirmBtn.setDisable(false);
            updateBalance();
            txtRecipientAcc.clear();
            txtTransferAmount.clear();
        }
    }
    
    @FXML
    void close(MouseEvent event) {
        anchorPane.getScene().getWindow().hide();
    }
    
    //Add listeners to the recipient and amount text fields
    private void addListeners(){
        txtRecipientAcc.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d{0,11}")) {
                txtRecipientAcc.setText(newValue);
            } else {
                txtRecipientAcc.setText(oldValue);
            }
        });  
        
        txtTransferAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d{0,11}([\\.]\\d{0,2})?")) {
                txtTransferAmount.setText(newValue);
            } else {
                txtTransferAmount.setText(oldValue);
            }
        });
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
            Logger.getLogger(balancetransferController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Update the balance information in the account table
    private void updateBalance() throws SQLException{
        con = DatabaseHandler.getConnection();
        
        //Query
        String sql = "SELECT * FROM atm.account WHERE account_number = ?;";
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, txtRecipientAcc.getText().trim());
            rs = pStmt.executeQuery();
            if(rs.next()){
                //Query
                String sql1 = "UPDATE atm.account SET balance = balance - " + txtTransferAmount.getText().trim() + " WHERE account_number = '" + loginController.sucessfulAccountNo + "'";
                try {
                    stmt = con.createStatement();
                    stmt.executeUpdate(sql1);
                    transaction = new Transaction(loginController.sucessfulAccountNo, "TRANSFER");
                    transaction.setReferenceAccount(txtRecipientAcc.getText().trim());
                    transaction.setWithdrawal(Double.parseDouble(txtTransferAmount.getText().trim()));
                    transaction.setBalance(Double.parseDouble(txtTransferAmount.getText().trim()));
                    loginController.transactionNumber = transaction.getId();
                } catch (SQLException ex) {
                    StringWriter sw = new StringWriter();
                    ex.printStackTrace(new PrintWriter(sw));
            
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Your Withdraw Request Caused An Error!");
                    alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(sw.toString())));
                    alert.showAndWait();
                }
        
                //Query
                String sql2 = "UPDATE atm.account SET balance = balance + " + txtTransferAmount.getText().trim() + " WHERE account_number = '" + txtRecipientAcc.getText().trim() + "'";
                try {
                    stmt = con.createStatement();
                    stmt.executeUpdate(sql2);
                    transaction = new Transaction(txtRecipientAcc.getText().trim(), "TRANSFER");
                    transaction.setReferenceAccount(loginController.sucessfulAccountNo);
                    transaction.setDeposit(Double.parseDouble(txtTransferAmount.getText().trim()));
                    transaction.setBalance(Double.parseDouble(txtTransferAmount.getText().trim()));
                    loginController.transactionNumber = transaction.getId();
                    con.close();
                }catch (SQLException ex) {
                    StringWriter sw = new StringWriter();
                    ex.printStackTrace(new PrintWriter(sw));
            
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Your Withdraw Request Caused An Error!");
                    alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(sw.toString())));
                    alert.showAndWait();
                }
            } 
            
            infoBox("Amount Transfered", "Your balance has been successfully transfered!", "You have successfully transfered K" + txtTransferAmount.getText().trim() + " from Account Number : " + loginController.sucessfulAccountNo + " into Account Number : " + txtRecipientAcc.getText().trim());
        } catch (SQLException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Your Balance Transfer Request Caused An Error!");
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(sw.toString())));
            alert.showAndWait();
        }
    }
    
    //Check if fields are empty
    private boolean areFieldsEmpty(){
        return txtRecipientAcc.getText().trim() == null || txtTransferAmount.getText().trim() == null;
    }
    
    //Method to display an alert box to inform the user of confirmation
    private static void infoBox(String title, String header, String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(contentText);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
