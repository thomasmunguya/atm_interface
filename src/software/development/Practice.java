package software.development;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.collections.*;
import com.mysql.jdbc.Driver;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Practice extends Application {
    
    @Override
    public void start(Stage primary_stage) throws ClassNotFoundException, SQLException {
        
        //load the appropriate jdbc driver
        Class.forName("com.mysql.jdbc.Driver");
        
        //connection object, acts as a bridge connecting the java program to a DB
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/the_school", "root", "thebestdamnthingever2!");
        
        //create a statement object
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("update challenge set name = 'welcome to github 2' where id = 1");
        
        ResultSet result_set = statement.executeQuery("select * from challenge where id = 1");
        
        result_set.next();
        
        Text text = new Text(result_set.getString("name"));
        
        BorderPane pane = new BorderPane();
        
        pane.setCenter(text);
        
        Scene scene = new Scene(pane);
       
        primary_stage.setScene(scene);
        primary_stage.setTitle("Practice");
        primary_stage.show();
        
        
        
     
        
    }
    
}
