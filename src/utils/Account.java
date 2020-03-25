/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.*;

/**
 *
 * @author MTH2
 */
public class Account {
    
    private String nrc_number;
    private int account_number;
    private int pin;
    private double balance;
    
    //data fields for DB columns
    private static final String NRC_COL = "nrc_number";
    private static final String ACC_NUMBER_COL = "account_number";
    private static final String PIN_COL = "pin";
    private static final String BALANCE_COL = "balance";
    private static final String TABLE_NAME = "account";
    
   private static DatabaseHandler db_handler = new DatabaseHandler();
    
    public Account() {
        
        
    }
    
    public Account(int account_number, String nrc_number, int pin, double balance) 
            throws ClassNotFoundException, SQLException {
        
        storeAccountNumber(account_number);
        storeOrUpdateNRCNumber(nrc_number, account_number);
        storeOrUpdatePIN(pin, account_number);
        storeOrUpdateBalance(balance, account_number);
        
        
    }
    
    public int getAccountNumber() throws SQLException {
        
        return Integer.parseInt(retrieveData(ACC_NUMBER_COL, this.account_number));
    }
    
    public void setAccountNumber(int account_number) throws SQLException{
        
        storeAccountNumber(account_number);
    }
    
    public String getNRCNumber() throws SQLException {
       
            
            return retrieveData(NRC_COL, this.account_number);
            
   }
    
    public void setNRCNumber(String nrc_number) throws SQLException {
        
        storeOrUpdateNRCNumber(nrc_number, this.account_number);
    }
    
    public int getPIN() throws SQLException {
        
        return Integer.parseInt(retrieveData(PIN_COL, this.account_number));
    }
    
    public void setPIN(int pin) throws SQLException {
        
        storeOrUpdatePIN(pin, this.account_number);
    }
    
    public int getBalance() throws SQLException {
        
        return Integer.parseInt(retrieveData(BALANCE_COL, this.account_number));
    }
    
    public void setBalance(double balance) throws SQLException {
        
        storeOrUpdateBalance(balance, this.account_number);
    } 
   
   
 
    /*retrieve the property specified by col_name by the nrc_number*/
    public String retrieveData(String col_name, int account_number)
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
            
            exception.printStackTrace();
        }
        
        return "No match";
    }
    
     /**store or update the account number to the database.
     * Since the account number is a primary key in the database, this method
     * first checks if the account number already exists. If it does, no action
     * is taken otherwise it is stored into the database 
     */
    public void storeAccountNumber(int account_number) throws SQLException {
        
        try {
            
            //modify this
            Connection connection = DatabaseHandler.createConnection();;
            
            PreparedStatement statement = connection.prepareStatement
                ("insert into " + TABLE_NAME  + " (" + ACC_NUMBER_COL + ", " + NRC_COL
                        + ", " + PIN_COL + ", " + BALANCE_COL +  
                                ") values(?, ?, ?, ?)");
            System.out.println(retrieveData(ACC_NUMBER_COL, account_number).equals("No match"));
            //if the nrc number already exists DO NOT store it
            if(retrieveData(ACC_NUMBER_COL, account_number).equals("No match")) {
                //will be set to retrieve values from their various methods
                statement.setInt(1, account_number );
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
            
            exception.printStackTrace();
        }
        
    }
    
    /*store or update the account's nrc to the database*/
    public void storeOrUpdateNRCNumber(String nrc_number, int account_number) 
            throws SQLException {
        
        try {
            
            //modify this
            Connection connection = DatabaseHandler.createConnection();;
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + NRC_COL
                    + " = '" + nrc_number + "' where " + ACC_NUMBER_COL + " = " + account_number + ";");
            
            connection.close();
            
        }
        catch(SQLException exception) {
            
            exception.printStackTrace();
        }
        
    }
    
    /*store or update the account pin to the database*/
    public void storeOrUpdatePIN(int pin, int account_number) 
            throws SQLException {
        
        try {
            
            //modify this
            Connection connection = DatabaseHandler.createConnection();;
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + PIN_COL
                    + " = " + pin + " where " + ACC_NUMBER_COL + " = " + account_number + ";");
            
            connection.close();
            
        }
        catch(SQLException exception) {
            
            exception.printStackTrace();
        }
        
    }
    
    /*store or update the account's nrc to the database*/
    public void storeOrUpdateBalance(double balance, int account_number) 
            throws SQLException {
        
        try {
            
            //modify this
            Connection connection = DatabaseHandler.createConnection();;
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + BALANCE_COL
                    + " = " + balance + " where " + ACC_NUMBER_COL + " = " + account_number + ";");
            
            connection.close();
            
        }
        catch(SQLException exception) {
            
            exception.printStackTrace();
        }
        
    }
    
  
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        Account account = new Account(242424, "481570611", 2727, 47500);
       
    }
}
