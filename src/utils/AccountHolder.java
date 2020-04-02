package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.loginController;

/**
 *
 * @author ZeRo
 */
public class AccountHolder {
    
    private String nrcNumber;
    private String firstName;
    private String lastName;
    private String email;
    private java.sql.Date dob;
    private String nationality;

    private static final String NRC_COL = "nrc_number";
    private static final String FNAME_COL = "first_name";
    private static final String LNAME_COL = "last_name";
    private static final String EMAIL_COL = "email";
    private static final String DOB_COL = "dob";
    private static final String NATIONALITY_COL = "nationality";
    
    private static Connection con = null;
    private static Statement stmt = null;
    private static PreparedStatement pStmt = null;
    private static ResultSet rs = null;
    
    //Deafult AccountHolder constructor
    public AccountHolder(){
    }

    //AccountHolder constructor
    public AccountHolder(String nrcNumber) throws SQLException{
        this.nrcNumber = storeOrUpdateNrcNumber(nrcNumber);
        this.firstName = storeOrUpdateFirstName(firstName, nrcNumber);
        this.lastName = storeOrUpdateLastName(lastName, nrcNumber);
        this.email = storeOrUpdateEmail(email, nrcNumber);
        this.dob = storeOrUpdateDate(dob, nrcNumber);
        this.nationality = storeOrUpdateNationality(nationality, nrcNumber);
    }

    public String getNrcNumber() throws SQLException{
        return nrcNumber;
    }

    public void setNrcNumber(String nrcNumber) throws SQLException{
        this.nrcNumber = storeOrUpdateNrcNumber(nrcNumber);
    }
    
    public String getFirstName() throws SQLException{
        return firstName;
    }

    public void setFirstName(String firstName) throws SQLException{
        this.firstName = storeOrUpdateFirstName(firstName, this.nrcNumber);
    }

    public String getLastName() throws SQLException{
        return lastName;
    }

    public void setLastName(String lastName) throws SQLException{
        this.lastName = storeOrUpdateLastName(lastName, this.nrcNumber);
    }

    public String getEmail() throws SQLException{
        return email;
    }

    public void setEmail(String email) throws SQLException{
        this.email = storeOrUpdateEmail(email, this.nrcNumber);
    }

    public Date getDob() throws SQLException{
        return dob;
    }

    public void setDob(Date dob) throws SQLException{
        this.dob = storeOrUpdateDate((java.sql.Date) dob, this.nrcNumber);
    }

    public String getNationality() throws SQLException{
        return nationality;
    }

    public void setNationality(String nationality) throws SQLException{
        this.nationality = storeOrUpdateNationality(nationality, this.nrcNumber);
    }
    
    //store or update the account holder's nrc to the database
    private String storeOrUpdateNrcNumber(String nrcNumber) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        String TABLE_NAME = "atm.account_holder";
        //MySQL Query
        String sql = "INSERT INTO " + TABLE_NAME + " (nrc_number, first_name, last_name, email, dob, nationality) VALUES (?,?,?,?,?,?)";
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, nrcNumber);
            pStmt.setNull(2, Types.VARCHAR);
            pStmt.setNull(3, Types.VARCHAR);
            pStmt.setNull(4, Types.VARCHAR);
            pStmt.setNull(5, Types.DATE);
            pStmt.setNull(6, Types.VARCHAR);
            pStmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, e);
        }
        return nrcNumber;
    }
    
    //store or update the first name to the database and return it
    private String storeOrUpdateFirstName(String firstName, String nrcNumber) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        String TABLE_NAME = "atm.account_holder";
        //MySQL Query
        String sql = "UPDATE " + TABLE_NAME + " SET " + FNAME_COL + " = '" + firstName + "' WHERE " + NRC_COL + " = '" + nrcNumber + "'";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, e);
        }
        return firstName;
    }
    
    //store or update the last name to the database and return it
    private String storeOrUpdateLastName(String lastName, String nrcNumber) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        String TABLE_NAME = "atm.account_holder";
        //MySQL Query
        String sql = "UPDATE " + TABLE_NAME + " SET " + LNAME_COL + " = '" + lastName + "' WHERE " + NRC_COL + " = '" + nrcNumber + "'";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, e);
        }
        return lastName;
    }
    
    //store or update the email to the database and return it
    private String storeOrUpdateEmail(String email, String nrcNumber) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        String TABLE_NAME = "atm.account_holder";
        //MySQL Query
        String sql = "UPDATE " + TABLE_NAME + " SET " + EMAIL_COL + " = '" + email + "' WHERE " + NRC_COL + " = '" + nrcNumber + "'";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, e);
        }
        return email;
    }
    
    //store or update the date to the database and return it
    private java.sql.Date storeOrUpdateDate(java.sql.Date dob, String nrcNumber) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        String TABLE_NAME = "atm.account_holder";
        //MySQL Query
        String sql = "UPDATE " + TABLE_NAME + " SET " + DOB_COL + " = '" + dob + "' WHERE " + NRC_COL + " = '" + nrcNumber + "'";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, e);
        }
        return dob;
    }
    
    //store or update the nationality to the database and return it
    private String storeOrUpdateNationality(String nationality, String nrcNumber) throws SQLException{
        con = DatabaseHandler.getConnection();
        
        String TABLE_NAME = "atm.account_holder";
        //MySQL Query
        String sql = "UPDATE " + TABLE_NAME + " SET " + NATIONALITY_COL + " = '" + nationality + "' WHERE " + NRC_COL + " = '" + nrcNumber + "'";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, e);
        }
        return nationality;
    }
}
