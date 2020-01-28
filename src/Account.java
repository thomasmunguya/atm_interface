import java.sql.*;
/*@author Mwansa Gee Phiri*/
public class Account
{
           //since  is on the database the account number is the only the 
           private int account_no;  //also from the account holder class
         
           
           private Account( int account_no)
           {
              this.account_no = account_no;
              
              
           
           }
          // checks the database for the amount   
          public int check_balance()
          throws SQLException, ClassNotFoundException {   
              
                //transaction object comes here
          
                 Class.forName("com.mysql.jdbc.Driver");
                 Connection connection = DriverManager.getConnection
                 ("jdbc:mysql://localhost/account_db_relation" , "root", "");
                 Statement statement = connection.createStatement();
                 String queryString = "select AccountBalance where  account_no =" + account_no ; 
                 ResultSet bala = statement.executeQuery(queryString);
                 int  balance = bala.getInt(1);
                 connection.close();
                 
                 Transaction t = new Transaction("check_balance",account_no);
                 t.store();
                 
                 
                 return balance;
                 
          } 
          
          //updates the amount in the database
          public void withdraw(int amount)
          throws SQLException, ClassNotFoundException { 
              
               //transaction object comes here
              
                 Class.forName("com.mysql.jdbc.Driver");
                 Connection connection = DriverManager.getConnection
                 ("jdbc:mysql://localhost/account_db_relation" , "root", "");
                 Statement statement = connection.createStatement();
                 String queryString = "select AccountBalance where  account_no =" + account_no ; 
                 ResultSet bal = statement.executeQuery(queryString);
                 
                int  balance = bal.getInt(1);
                 
               if( balance > amount){  
                   
                   int newAmount = bal.getInt(1) - amount;
                 
                   statement.executeUpdate( "UPDATE Account SET  AccountBalance = " + newAmount +" where  account_no = " + account_no ); 
                   connection.close();
                   
                     Transaction t = new Transaction("Withdraw", account_no);
                     t.store();
                  
                  }eles{ 
              
                   Transaction t = new Transaction("FAILED WITHDRAW", account_no);
                   t.store();
                     
                   connection.close(); 
                   break; };
              
                   
              
          }
          
          //
          public void deposit( int amount)
          throws SQLException, ClassNotFoundException {
              
                 //transaction object comes here
              
                 Class.forName("com.mysql.jdbc.Driver");
                 Connection connection = DriverManager.getConnection
                 ("jdbc:mysql://localhost/account_db_relation" , "root", "");
                 Statement statement = connection.createStatement();
                 String queryString = "select AccountBalance where  account_no =" + account_no ; 
                 ResultSet bala = statement.executeQuery(queryString);
                 
                 int newAmount = bala.getInt(1) + amount;
                 
                 statement.executeUpdate
                 ("UPDATE Account SET  AccountBalance =" + newAmount +" where  account_no =" + account_no );
                 connection.close();
                 
                  Transaction t = new Transaction("Deposit", account_no);
                     t.store();
    
          }
    
}
