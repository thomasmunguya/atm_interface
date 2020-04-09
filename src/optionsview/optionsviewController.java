package optionsview;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private Button balanceTransferBtn;
    
    @FXML
    private Button withdrawalBtn;
    
    @FXML
    private Button balanceInquiryBtn;
    
    @FXML
    private Button billingBtn;
    
    @FXML
    private Button cancelBtn;

    private Stage stage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //makeFadeIn();
    } 
    
    @FXML
    void depositBtn(MouseEvent event) throws IOException {
        loadDeposit();
    }
    
    @FXML
    void withdrawalBtn(MouseEvent event) throws IOException {
        loadWithdrawal();
    }
    
    @FXML
    void balanceTransferBtn(MouseEvent event) throws IOException {
        loadBalanceTransfer();
    }
    
    @FXML
    void balanceInquiryBtn(MouseEvent event)  throws IOException {
        loadBalanceInquiry();
    }
    
    @FXML
    void billingBtn(MouseEvent event) throws IOException {
        loadBilling();
    }
    
    @FXML
    void cancelButton(MouseEvent event) {
        makeFadeOutIntoloadLogin();
    }
    
    //This method loads the login page
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
            Logger.getLogger(optionsviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //This method loads the deposit page
    private void loadDeposit() throws IOException{
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        AnchorPane anchorPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/deposit/deposit.fxml"));
        anchorPane.getStylesheets().add(getClass().getResource("/assets/css/deposit.css").toExternalForm());
        Scene scene = new Scene(anchorPane, 600, 400);
            
        window.setScene(scene);
        window.centerOnScreen();
        window.setTitle("Deposit");
        window.setResizable(false);
        window.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Request");
            alert.setHeaderText(null);
            alert.setContentText("Äre you sure you want to exit the window?");
                
            ButtonType okButtonType = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == okButtonType){
                window.close();
            }else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                e.consume();
            }
        });
        window.show();
    }
    
    //This method loads the withdraw page
    private void loadWithdrawal() throws IOException{
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        AnchorPane anchorPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/withdrawal/withdrawal.fxml"));
        anchorPane.getStylesheets().add(getClass().getResource("/assets/css/withdrawal.css").toExternalForm());
        Scene scene = new Scene(anchorPane, 600, 400);
            
        window.setScene(scene);
        window.centerOnScreen();
        window.setTitle("Withdrawal");
        window.setResizable(false);
        window.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Request");
            alert.setHeaderText(null);
            alert.setContentText("Äre you sure you want to exit the window?");
                
            ButtonType okButtonType = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == okButtonType){
                window.close();
            }else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                e.consume();
            }
        });
        window.show();
    }
    
    //This method loads the balance transfer page
    private void loadBalanceTransfer() throws IOException{
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        AnchorPane anchorPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/balancetransfer/balancetransfer.fxml"));
        anchorPane.getStylesheets().add(getClass().getResource("/assets/css/balancetransfer.css").toExternalForm());
        Scene scene = new Scene(anchorPane, 600, 400);
            
        window.setScene(scene);
        window.centerOnScreen();
        window.setTitle("Balance Transfer");
        window.setResizable(false);
        window.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Request");
            alert.setHeaderText(null);
            alert.setContentText("Äre you sure you want to exit the window?");
                
            ButtonType okButtonType = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == okButtonType){
                window.close();
            }else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                e.consume();
            }
        });
        window.show();
    }
    
    //This method loads the balance inquiry page
    private void loadBalanceInquiry() throws IOException{
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        AnchorPane anchorPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/balanceinquiry/balanceinquiry.fxml"));
        anchorPane.getStylesheets().add(getClass().getResource("/assets/css/balanceinquiry.css").toExternalForm());
        Scene scene = new Scene(anchorPane, 600, 400);
            
        window.setScene(scene);
        window.centerOnScreen();
        window.setTitle("Balance Inquiry");
        window.setResizable(false);
        window.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Request");
            alert.setHeaderText(null);
            alert.setContentText("Äre you sure you want to exit the window?");
                
            ButtonType okButtonType = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == okButtonType){
                window.close();
            }else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                e.consume();
            }
        });
        window.show();
    }
    
    //This method loads the billing page
    private void loadBilling() throws IOException{
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        AnchorPane anchorPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/billing/billing.fxml"));
        anchorPane.getStylesheets().add(getClass().getResource("/assets/css/billing.css").toExternalForm());
        Scene scene = new Scene(anchorPane, 600, 400);
            
        window.setScene(scene);
        window.centerOnScreen();
        window.setTitle("Billing");
        window.setResizable(false);
        window.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Request");
            alert.setHeaderText(null);
            alert.setContentText("Äre you sure you want to exit the window?");
                
            ButtonType okButtonType = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == okButtonType){
                window.close();
            }else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                e.consume();
            }
        });
        window.show();
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
            try {
                loadDeposit();
            } catch (IOException ex) {
                Logger.getLogger(optionsviewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        fade.play();
    }
}
