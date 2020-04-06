package withdraw;

import deposit.depositController;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import login.loginController;
import utils.Account;
import utils.DatabaseHandler;

/**
 *
 * @author DividedByZeRo
 * 
 */
public class withdrawController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label lblWithdraw;

    @FXML
    private Label lblAccInfo;

    @FXML
    private Label lblAccNo;

    @FXML
    private Label lblAmount;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField txtWithdrawAmount;

    private Account account;
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
            Logger.getLogger(withdrawController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            txtWithdrawAmount.clear();
        }
    }
    
    @FXML
    void close(MouseEvent event) {
        anchorPane.getScene().getWindow().hide();
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
            Logger.getLogger(withdrawController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Update the balance information in the account table
    private void updateBalance() throws SQLException{
        con = DatabaseHandler.getConnection();
        
        //Query
        String sql = "UPDATE atm.account SET balance = balance - " + txtWithdrawAmount.getText().trim() + " WHERE account_number = '" + loginController.sucessfulAccountNo + "'";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(withdrawController.class.getName()).log(Level.SEVERE, null, ex);
        }
        infoBox("Ammount Withdrawn", "Your withdraw has been succesful!", "You have successfully withdrawn K" + txtWithdrawAmount.getText().trim() + " into Account Number : " + loginController.sucessfulAccountNo);
    }
    
    //Add listeners to the withdraw text field
    private void addListeners(){
        txtWithdrawAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("([1-9][0-9]*)?")) {
                txtWithdrawAmount.setText(newValue);
            } else {
                txtWithdrawAmount.setText(oldValue);
            }
        });    
    }    
    
    //Check if fields are empty
    private boolean areFieldsEmpty(){
        return txtWithdrawAmount.getText().trim() == null;
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
