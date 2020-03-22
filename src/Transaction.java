import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* @author Mwansa Gee Phiri
this class is used collect and store records of transactions that occured using 
the application. Using a store() method can save the data and can be used to present them in a clean
ordeded way using a retreive() method 
 */
public class Transaction 
{

    public double Transaction_id;
    public String Transaction_type;
    public time transaction_time;
    public int Transaction_amount;
    
    /*
    the transsaction object will collect the string T which will represent the type
    of transaction, the account number and the amount used in this transaction a.
    The transaction id is made up of the account number, transaction hours, minutes and 
    seconds
    */
    
    public Transaction( String T, int account_no, int a )
    {
        int tr =1;
        
        switch(T)
        {
            case "Withdraw" : int Type = 0;
                             break;
            case "check_balance" :  tr = 1;
                             break;
            case "Deposit" :  tr = 2;
                             break;
            case "transfer" :  tr = 2;
                             break;
                
            default : tr = 3;  
        }
        
      transaction_time = new time();
      Transaction_type = T;
      Transaction_id = (tr *1000000000)+(transaction_time.month*100000000)+(transaction_time.day*1000000)+(transaction_time.hour*10000) + (transaction_time.minute* 100) + transaction_time.seconds; 
      Transaction_amount = a;
     }
    
     /*
    this method is used to store the transaction records in the database using the
    data fields of the database which are the 
    */
    
    public void store()
    throws SQLException, ClassNotFoundException {
              
                 connection = ATM.connectToDB();
                 Statement statement = connection.createStatement(); 
                 
                  statement.executeUpdate
                 ( "insert into transaction values (" + Transaction_id + "," + transaction_time.year "/"transaction_time.month "/"transaction_time.day " " transaction_time.hour ":" transaction_time.minute ":" transaction_time.second + "," + Transaction_type + "," + Transaction_amount +  ")" );
                 connection.close();
    
        
    } 
    
     /*
    this method is used to return records of transactions in case we make a GUI to show
    all the past transactions
    */
    
    
    public String Retreve(int account_no)
    throws SQLException, ClassNotFoundException {
              
              
                 Connection connection = ATM.connectToDB();
                 Statement statement = connection.createStatement();
                 
                 String queryString = "select  * from transaction where  account_no =" + account_no ; 
                 ResultSet bal = statement.executeQuery(queryString);
                 
                 while (resultSet.next())
                 {
                      // this returns the data in a sentence like on the day a withdraw transaction was made with the amount of 200
                     
                     System.out.print(resultSet.getInt(1));
                     System.out.println("On "+ resultSet.getdate(2) + " A " + resultSet.getString(3) + " Transacton was made with the amount of " +resultSet.getInt(4) );
                     
                 }
                 
                 
    }  
    
    
    
}