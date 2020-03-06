package optionsview;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class optionsviewController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label lblHello;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeFadeIn();
    }    
    
    private void makeFadeIn(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        fade.setNode(borderPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
}
