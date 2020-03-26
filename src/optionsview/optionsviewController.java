package optionsview;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.*;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class optionsviewController implements Initializable {

    @FXML
    private AnchorPane rootAnchorPane; 

    @FXML
    private Label lblHello;
    
    @FXML
    private Button btMoneyWithdraw;
    
    private Stage stage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeFadeIn();
    }    
    
    private void makeFadeIn(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        fade.setNode(rootAnchorPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
    
    @FXML
    private void loadWithdraw() throws IOException {
        
        try {
            
           
            VBox main_pane = FXMLLoader.<VBox>load(getClass().getResource("/withdraw/Withdraw.fxml"));
            main_pane.getStylesheets().add(getClass().getResource("/assets/css/withdraw.css").toExternalForm());
            
            Scene scene_withdraw = new Scene(main_pane, 650, 400);
            
            stage = (Stage) rootAnchorPane.getScene().getWindow();
            stage.setScene(scene_withdraw);
            stage.setTitle("Withdraw Page");
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException exception) {
            
            Logger.getLogger(optionsviewController.class.getName()).log(Level.SEVERE, null, exception);
        }
        
        
    }
}
