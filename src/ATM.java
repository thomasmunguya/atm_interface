package atm_interface.src;
import javafx.application.Application;
import java.sql.*;
import javafx.stage.*;

public class ATM extends Application {
    
    @Override
    public void start(Stage primary_stage) {
        
    }
    
    /*This method connects the program to the atm databaseSince each one is 
    using their own local database, you can fill in the password field in this
    method using your own local password.
    */
    static Connection connectToDB() throws ClassNotFoundException,
            Exception {
        
        try {
            
            //load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            
            System.out.println("Driver loaded");
            
            //establish a connection to the database
            Connection connection = DriverManager.getConnection
            ("jdbc:mysql://localhost/atm", "root", "" );
            
            System.out.println("Database connected");
            
            return connection;
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
        return null;
    }
}
/*AcoountHolder class*/

