/*
*** This is the account class for the atm interface program.
*** This class has the following code:
*** a default constructor
*** a constructor that accepts an NRC number as argument and generates a random account number and PIN(
***     the constructor uses the methods for generating the account number and PIN)
*** getter and setter methods for account data fields
*** a private instance method that takes the DB column(attribute) name and account number as arguments for retrieving data from the DB 
*** private instance methods for storing or updating the data fields to the DB(Each of these methods takes as arguments the column(attribute) name
***     account number and returns the value just stored.
*** a private instance method for generating a random 11-digit account number
*** a private instance method for generating a 4-digit PIN
 */
package utils;

import java.sql.*;
import java.util.*;

/**
 *
 * @author MTH2
 */
public class Account {
    
    private String nrc_number;
    private String account_number;
    private String generated_account_number;
    private String pin;
    private String balance;
    private static Account active_account;
    
    //data fields for DB data
    private static final String NRC_COL = "nrc_number";
    private static final String ACC_NUMBER_COL = "account_number";
    private static final String PIN_COL = "pin";
    private static final String BALANCE_COL = "balance";
    private static final String TABLE_NAME = "account";
    private static DatabaseHandler db_handler = new DatabaseHandler();
    
    public Account() {
        
        
    }
    
    
    public Account(String nrc_number) 
            throws SQLException{
        
        generated_account_number = generateAccountNumber();
        this.account_number = storeAccountNumber(generated_account_number);
        this.nrc_number = storeOrUpdateNRCNumber(nrc_number, generated_account_number);
        this.pin = storeOrUpdatePIN(generatePIN(), generated_account_number);
        this.balance = storeOrUpdateBalance("0.00", generated_account_number);
           
    }
    
    public final String getAccountNumber() throws SQLException {
        
        return this.account_number;
    }
    
    public final void setAccountNumber(String account_number) throws SQLException{
        
        this.account_number = storeAccountNumber(account_number);
    }
    
    private void setAccountNumber() throws SQLException {
        
        active_account.account_number = this.getAccountNumber();
    }
    public final String getNRCNumber() throws SQLException {
       
            
        return this.nrc_number;
            
   }
    
    public void setNRCNumber(String nrc_number) throws SQLException {
        
        this.nrc_number = storeOrUpdateNRCNumber(nrc_number, this.account_number);
    }
    
    public final String getPIN() throws SQLException {
        
        return this.pin;
    }
    
    public void setPIN(String pin) throws SQLException {
        
        this.pin = storeOrUpdatePIN(pin, this.account_number);
    }
    
    public final String getBalance() throws SQLException {
        
        return this.balance;
    }
    
    public void setBalance(String balance) throws SQLException {
        
       this.balance = storeOrUpdateBalance(balance, this.account_number);
        
    } 
    
    public static final Account getActiveAccount() {
        
        return active_account;
    }
    
    public static final void setActiveAccount(Account new_active_account) {
        
        active_account = new_active_account;
    }
   
   
 
    /*retrieve the property specified by col_name by the account number*/
    private String retrieveData(String col_name, String account_number)
        throws SQLException {
        
        Connection connection;
        ResultSet retrieved_data;
        
        try {
            

            connection = DatabaseHandler.createConnection();
            
            Statement statement = connection.createStatement();
            
            statement.executeQuery("select * from " + TABLE_NAME + " where "
             + ACC_NUMBER_COL + " = " + account_number + ";");
            
            retrieved_data = statement.getResultSet();
            
            if(retrieved_data.next() != false) {
                
                switch(col_name) {
                    
                    case ACC_NUMBER_COL: return retrieved_data.getString(ACC_NUMBER_COL);
                    case NRC_COL: return retrieved_data.getString(NRC_COL);
                    case PIN_COL: return retrieved_data.getString(PIN_COL);
                    case BALANCE_COL: return retrieved_data.getString(BALANCE_COL);
                    
                }
            }
        
            
        }
        catch(SQLException exception) {
            
        }
        
        return "No match";
    }
    
     /**store or update the account number to the database.
     * Since the account number is a primary key in the database, this method
     * first checks if the account number already exists. If it does, no action
     * is taken otherwise it is stored into the database and returned
     */
    private String storeAccountNumber(String account_number) throws SQLException {
        
        try {
            
            //modify this
            Connection connection = DatabaseHandler.createConnection();
            
            PreparedStatement statement = connection.prepareStatement
                ("insert into " + TABLE_NAME  + " (" + ACC_NUMBER_COL + ", " + NRC_COL
                        + ", " + PIN_COL + ", " + BALANCE_COL +  
                                ") values(?, ?, ?, ?)");
            System.out.println(retrieveData(ACC_NUMBER_COL, account_number).equals("No match"));
            //if the nrc number already exists DO NOT store it
            if(retrieveData(ACC_NUMBER_COL, account_number).equals("No match")) {
                //will be set to retrieve values from their various methods
                statement.setString(1, account_number );
                statement.setNull(2, Types.VARCHAR); //set the nrc number to null
                statement.setNull(3, Types.INTEGER); //set account balance to null
                statement.setNull(4, Types.INTEGER);
                statement.executeUpdate();
                
                connection.close();
                
            }
            
            else {
                
                connection.close();
            }
        }
        catch(SQLException exception) {
            
        }
       
        return account_number;
    }
    
    /*store or update the account's nrc to the database and return it*/
    private String storeOrUpdateNRCNumber(String nrc_number, String account_number) 
            throws SQLException {
        
        try {
            
            try ( //modify this
                    Connection connection = DatabaseHandler.createConnection()) {
                
                
                Statement statement = connection.createStatement();
                
                statement.executeUpdate("update " + TABLE_NAME + " set " + NRC_COL
                        + " = '" + nrc_number + "' where " + ACC_NUMBER_COL + " = " + account_number + ";");
            }
            
        }
        catch(SQLException exception) {
            
        }
        
        return nrc_number;
    }
    
    /*store or update the account pin to the database and return it*/
    private String storeOrUpdatePIN(String pin, String account_number) 
            throws SQLException {
        
        try {
            
            try ( //modify this
                    Connection connection = DatabaseHandler.createConnection()) {
                
                
                Statement statement = connection.createStatement();
                
                statement.executeUpdate("update " + TABLE_NAME + " set " + PIN_COL
                        + " = " + pin + " where " + ACC_NUMBER_COL + " = " + account_number + ";");
            }
            
        }
        catch(SQLException exception) {
            
        }
        
        return pin;
    }
    
    /*store or update the account's nrc to the database and return it*/
    private String storeOrUpdateBalance(String balance, String account_number) 
            throws SQLException {
        
        try {
            
            try ( 
                    Connection connection = DatabaseHandler.createConnection()) {
                
                Statement statement = connection.createStatement();
                
                statement.executeUpdate("update " + TABLE_NAME + " set " + BALANCE_COL
                        + " = " + balance + " where " + ACC_NUMBER_COL + " = " + account_number + ";");
            }
            
        }
        catch(SQLException exception) {
        }
        
        return balance;
    }
    
    //This method generates a random 11-digit account number
    private static String generateAccountNumber(){
        
        Random rnd = new Random();
        String account_number = "";
        for (int i = 0; i < 9; i++) {
            
            int random_number = rnd.nextInt(10);
            account_number += Integer.toString(random_number);
        }
        return account_number;
    }
    
    //This method generates a random 4-digit pin
    private static String generatePIN(){
        
        Random rnd = new Random();
        String pin = "";
        for (int i = 0; i < 4; i++) {
            int random_number = rnd.nextInt(10);
            pin += Integer.toString(random_number);
        }
        return pin;
    }
    
}
