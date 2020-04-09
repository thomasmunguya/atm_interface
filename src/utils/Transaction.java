package utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ZeRo
 * 
 */
public class Transaction {
    
    private int id;
    private java.sql.Date date;
    private String description;
    private String accNumber;
    private String referenceAccount;
    private double deposit;
    private double withdrawal;
    private double balance;
    
    private static final String TABLE_NAME = "atm.transaction";
    private static final String ID_COL = "id";
    private static final String DATE_COL = "date";
    private static final String DESCRIPTION_COL = "description";
    private static final String ACC_NUMBER_COL = "account_number";
    private static final String REF_ACC_COL = "ref_account";
    private static final String DEPOSIT_COL = "deposit";
    private static final String WITHDRAWAL_COL = "withdrawal";
    private static final String BALANCE_COL = "balance";

    private static Connection con = null;
    private static Statement stmt = null;
    private static PreparedStatement pStmt = null;
    private static ResultSet rs = null;
    private static DateFormat df;
    
    //Default Transaction class constructor
    public Transaction(String accountNumber, String description) throws SQLException{
        this.accNumber = storeAccNumber(accountNumber, description);
        this.description = UpdateDescription(description, id);
        this.referenceAccount = UpdateReferenceAccount(referenceAccount, id);
        this.deposit = UpdateDeposit(deposit, id);
        this.withdrawal = UpdateWithdrawal(withdrawal, id);
        this.balance = UpdateBalance(balance, id);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) throws SQLException {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) throws SQLException {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public String getAccount() {
        return accNumber;
    }

    public void setAccount(String account) throws SQLException {
        this.accNumber = storeAccNumber(account, this.description);
    }
   
    public void setDescription(String description) throws SQLException {
        this.description = UpdateDescription(description, this.id);
    }

    public String getReferenceAccount() {
        return referenceAccount;
    }

    public void setReferenceAccount(String referenceAccount) throws SQLException {
        this.referenceAccount = UpdateReferenceAccount(referenceAccount, this.id);
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) throws SQLException {
        this.deposit = UpdateDeposit(deposit, this.id);
    }

    public double getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(double withdrawal) throws SQLException {
        this.withdrawal = UpdateWithdrawal(withdrawal, this.id);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) throws SQLException {
        this.balance = UpdateBalance(balance, this.id);
    }
    
    //retrieve the property specified by col_name by the account number
    private String retrieveData(String column, int id) throws SQLException {
        con = DatabaseHandler.getConnection();
        
        //Query
        String sql = "SELECT * FROM " +  TABLE_NAME + " WHERE nrc_number = " + id + ";";
        try {
            stmt = con.createStatement();
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if(rs.next() != false) {
                switch(column) {
                    case ID_COL: return Integer.toString(rs.getInt(ID_COL));
                    case DATE_COL: return df.format(rs.getDate(DATE_COL));
                    case DESCRIPTION_COL: return rs.getString(DESCRIPTION_COL);
                    case ACC_NUMBER_COL: return rs.getString(ACC_NUMBER_COL);
                    case REF_ACC_COL: return rs.getString(REF_ACC_COL);
                    case DEPOSIT_COL: return Double.toString(rs.getDouble(DEPOSIT_COL));
                    case WITHDRAWAL_COL: return Double.toString(rs.getDouble(WITHDRAWAL_COL));
                    case BALANCE_COL: return Double.toString(rs.getDouble(BALANCE_COL));
                }
            }
        }catch(SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return column;
    }
    
    //Store the successful account number
    private String storeAccNumber(String accountNumber, String desc) throws SQLException{
        //Create a Connection to be able to insert data into the mysql db
        con = DatabaseHandler.getConnection();
        
        java.util.Date date = new java.util.Date();
	java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        //MySQL Query
        String sql1 = "INSERT INTO " + TABLE_NAME + " (date, description, account_number, ref_account, deposit, withdrawal, balance) VALUES (?,?,?,?,?,?,?)";
        try {
            pStmt = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            pStmt.setDate(1, sqlDate);
            pStmt.setString(2, desc);
            pStmt.setString(3, accountNumber);
            pStmt.setNull(4, Types.VARCHAR);
            pStmt.setDouble(5, 0);
            pStmt.setDouble(6, 0);
            pStmt.setDouble(7, 0);
            pStmt.executeUpdate();
            
            //Since the the transction ids are auto-incremented we need to fetch the dynamically generated id
            rs = pStmt.getGeneratedKeys();
            while(rs.next()){
                //This saves the dynamically generated id to a variable
                setId(rs.getInt(1));
            }
            con.close();
        } catch(SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accountNumber;
    }
    
    //Update the description
    private String UpdateDescription(String description, int id) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        //MySQL Query
        String sql = "UPDATE " + TABLE_NAME + " SET " + DESCRIPTION_COL + " = '" + description + "' WHERE " + ID_COL + " = " + id;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, e);
        }
        return description;
    }
    
    //Update the reference account number column
    private String UpdateReferenceAccount(String refAccInfo, int id) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        //MySQL Query
        String sql = "UPDATE " + TABLE_NAME + " SET " + REF_ACC_COL + " = '" + refAccInfo + "' WHERE " + ID_COL + " = " + id;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, e);
        }
        return refAccInfo;
    }
    
    //Store the deposit column
    private double UpdateDeposit(double deposit, int id) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        //MySQL Query
        String sql = "UPDATE " + TABLE_NAME + " SET " + DEPOSIT_COL + " = " + deposit + " WHERE " + ID_COL + " = " + id;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, e);
        }
        return deposit;
    }
    
    //Update the withdrawl column
    private double UpdateWithdrawal(double withdrawal, int id) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        //MySQL Query
        String sql = "UPDATE " + TABLE_NAME + " SET " + WITHDRAWAL_COL + " = " + withdrawal + " WHERE " + ID_COL + " = " + id;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, e);
        }
        return withdrawal;
    }
    
    //Store or update the account's balance column
    private double UpdateBalance(double balance, int id) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        //MySQL Query
        String sql = "UPDATE " + TABLE_NAME + " SET " + BALANCE_COL + " = " + balance + " WHERE " + ID_COL + " = " + id;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, e);
        }
        return balance;
    }
}
