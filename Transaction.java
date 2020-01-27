import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/* @author Mwansa Gee Phiri
 */
public class Transaction 
{

    public double Transaction_id;
    public String Transaction_type;
    public time transaction_time;
    
    public Transaction( String T, int account_no )
    {
      transaction_time = new time();
      Transaction_type = T;
      Transaction_id = (account_no*100000) + (transaction_time.hour*10000) + (transaction_time.minute* 100) + transaction_time.seconds; 
      
     }
    
    public void store()
    throws SQLException, ClassNotFoundException {
              
                 //transaction object comes here
              
                 Class.forName("com.mysql.jdbc.Driver");
                 Connection connection = DriverManager.getConnection
                 ("jdbc:mysql://localhost/account_db_relation" , "root", "");
                 Statement statement = connection.createStatement(); 
                 
                  statement.executeUpdate
                 ( "insert into transaction values (" + Transaction_id + "," + date + "," + Transaction_type + ")" )
                 connection.close();
    
        
    } 
    
    public String Retreve(int account_no)
    throws SQLException, ClassNotFoundException {
              
                 //transaction object comes here
              
                 Class.forName("com.mysql.jdbc.Driver");
                 Connection connection = DriverManager.getConnection
                 ("jdbc:mysql://localhost/account_db_relation" , "root", "");
                 Statement statement = connection.createStatement();
                 
                 String queryString = "select  * from transaction where  account_no =" + account_no ; 
                 ResultSet bal = statement.executeQuery(queryString);
                 
                 while (resultSet.next())
                 {
                      // you will arrange the result set depending on the database design 
                     System.out.println(resultSet.getInt(1));  
                     
                 }
                 
                 
    }        
}