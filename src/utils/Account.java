package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.loginController;
import signup.signupController;

/*
 * @author DividedByZeRo
 */
public class Account {
    
    private String nrcNumber;
    private String accNumber;
    private String pin;
    private double balance;
    
    private static final String NRC_COL = "holder_nrc_number";
    private static final String ACC_NUMBER_COL = "account_number";
    private static final String PIN_COL = "pin";
    private static final String BALANCE_COL = "balance";
    private static final String TABLE_NAME = "atm.account";
    private static Connection con = null;
    private static Statement stmt = null;
    private static PreparedStatement pStmt = null;
    private static ResultSet rs = null;

    //Default Account constructor
    public Account() throws SQLException{
        String generatedAccNumber = generateAccountNumber();
        this.accNumber = storeAccNumber(generatedAccNumber);
        this.nrcNumber = storeOrUpdateNRCNumber(nrcNumber, generatedAccNumber);
        this.pin = storeOrUpdatePin(generatePin(), generatedAccNumber);
        this.balance = storeOrUpdateBalance(0, generatedAccNumber);
    }
    
    public String getNrcNumber() throws SQLException{
        return nrcNumber;
    }

    public void setNrcNumber(String nrcNumber) throws SQLException{
        this.nrcNumber = storeOrUpdateNRCNumber(nrcNumber, this.accNumber);
    }

    public String getAccNumber() throws SQLException{
        return accNumber;
    }

    public void setAccNumber(String accNumber) throws SQLException{
        this.accNumber = storeAccNumber(accNumber);
    }

    public String getPin() throws SQLException{
        return pin;
    }

    public void setPin(String pin) throws SQLException{
        this.pin = storeOrUpdatePin(pin, this.accNumber);
    }

    public double getBalance() throws SQLException{
        return balance;
    }

    public void setBalance(double balance) throws SQLException{
        this.balance = storeOrUpdateBalance(balance, this.accNumber);
    }
    
    //Retrieve the property specified by column name by the account number
    private String retrieveData(String column, String nrcNumber) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        //Query
        String sql = "SELECT * FROM " +  TABLE_NAME + " WHERE nrc_number = " + nrcNumber + ";";
        try {
            stmt = con.createStatement();
            stmt.executeQuery(sql);
            rs = stmt.getResultSet();
            if(rs.next()){
                switch(column){
                    case NRC_COL: return rs.getString("nrc_number");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return column;
    }
    
    //Store the generated account number in the database
    private String storeAccNumber(String accNumber) throws SQLException{
        //Create a Connection to be able to insert data into the mysql db
        con = DatabaseHandler.getConnection();
        
        //MySQL Query
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE account_number = ?;";
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, accNumber);
            rs = pStmt.executeQuery();
            if(rs.next()){
                //If account number already exists then generate another account number and store it
                accNumber = generateAccountNumber();
            }else{
                String sql1 = "INSERT INTO " + TABLE_NAME + " (account_number, pin, balance, holder_nrc_number) VALUES (?,?,?,?)";
                try {
                    pStmt = con.prepareStatement(sql1);
                    pStmt.setString(1, accNumber);
                    pStmt.setNull(2, Types.VARCHAR);
                    pStmt.setNull(3, Types.DOUBLE);
                    pStmt.setNull(4, Types.VARCHAR);
                    pStmt.executeUpdate();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(signupController.class.getName()).log(Level.SEVERE, null, e);
        }
        return accNumber;
    }
    
    //Store or update the account's nrc to the database and return it
    private String storeOrUpdateNRCNumber(String nrcNumber, String accNumber) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        //MySQL Query
        String sql = "UPDATE " + TABLE_NAME + " SET " + NRC_COL + " = '" + nrcNumber + "' WHERE " + ACC_NUMBER_COL + " = '" + accNumber + "'";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(signupController.class.getName()).log(Level.SEVERE, null, e);
        }
        return nrcNumber;
    }
    
    //Store or update the account's nrc to the database and return it
    private String storeOrUpdatePin(String pin, String accNumber) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        //MySQL Query
        String sql = "UPDATE " + TABLE_NAME + " SET " + PIN_COL + " = '" + pin + "' WHERE " + ACC_NUMBER_COL + " = '" + accNumber + "'";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(signupController.class.getName()).log(Level.SEVERE, null, e);
        }
        return pin;
    }
    
    //Store or update the account's nrc to the database and return it
    private double storeOrUpdateBalance(double balance, String accNumber) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        //MySQL Query
        String sql = "UPDATE " + TABLE_NAME + " SET " + BALANCE_COL + " = " + balance + " WHERE " + ACC_NUMBER_COL + " = '" + accNumber + "'";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(signupController.class.getName()).log(Level.SEVERE, null, e);
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
    private static String generatePin(){
        
        Random rnd = new Random();
        String pin = "";
        for (int i = 0; i < 4; i++) {
            int random_number = rnd.nextInt(10);
            pin += Integer.toString(random_number);
        }
        return pin;
    }
}
