package atm_interface.src;
import java.sql.*;


/**
 *
 * @author MTH2
 * This is the account holder class for the ATM program. The class has methods
 * for retrieving and storing or updating the various properties of an account
 * holder including the nrc number, the first name, middle name, last name, 
 * nationality etc. The various properties are stored and retrieved based on
 * the nrc number that the user enters into a text field hence all retrieve
 * methods take the nrc number as argument. On the other hand, all the store
 * methods take a particular property value and an nrc number as arguments
 * since there are stored on the basis of the nrc number as alluded to earlier.
 * All methods in this class are static so there's no need for instantiation.
 * There's also a connectToDB() that connects to the database and returns a
 * Connection object to avoid redundant code in the methods. 
 * Since each one is using their own local database, you can fill in the 
 * password field in the connectToDB() method using your own local password.
 */
class AccountHolder {
   
    private static final String NRC_COL = "nrc_number";
    private static final String FNAME_COL = "first_name";
    private static final String MIDNAME_COL = "middle_name";
    private static final String LNAME_COL = "last_name";
    private static final String NATIONALITY_COL = "nationality";
    private static final String DOB_COL = "dob";
    private static final String OCCUPATION_COL = "occupation";
    private static final String TABLE_NAME = "account_holder";
    
    
    /*retrieve the account holder's nrc number from the database*/
    public static String retrieveNRCNumber(String nrc_number) throws ClassNotFoundException, SQLException {
        
        ResultSet retrieved_nrc_number = null;
        
        Connection connection;
        
        try {
            
            connection = connectToDB();
            
            Statement statement = connection.createStatement();
            statement.executeQuery("select " + NRC_COL + " from "
                    + TABLE_NAME + " where " + NRC_COL + " = " + nrc_number);
            
             retrieved_nrc_number = statement.getResultSet();
            
             
             if(retrieved_nrc_number.next() != false)
                 return retrieved_nrc_number.getString("nrc_number");
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
        return "No match";
        
    }
    
    /**store or update the account holder's nrc number to the database.
     * Since the nrc number is a primary key in the database, this method
     * first checks if the nrc number already exists. If it does, no action
     * is taken otherwise it is stored into the database 
     */
    public static void storeNRCNumber(String nrc_number) throws 
            ClassNotFoundException, Exception {
        
        try {
            
            Connection connection = connectToDB();
            
            PreparedStatement statement = connection.prepareStatement
                ("insert into " + TABLE_NAME  + "(" + NRC_COL + ", " + FNAME_COL
                        + ", " + MIDNAME_COL + ", " + LNAME_COL + ", " + 
                                NATIONALITY_COL + ", " + DOB_COL + ", " + 
                                OCCUPATION_COL + ") values(?, ?, ?, ?, ?, ?, ?)");
            
            //if the nrc number already exists DO NOT store it
            if(!retrieveNRCNumber(nrc_number).equals("No Match")) {
                
                connection.close();
                
                return;
            }
            
            //will be set to retrieve values from their various methods
            statement.setString(1, nrc_number);
            statement.setNull(2, Types.VARCHAR);
            statement.setNull(3, Types.VARCHAR);
            statement.setNull(4, Types.VARCHAR);
            statement.setNull(5, Types.VARCHAR);
            statement.setNull(6, Types.DATE);
            statement.setNull(7, Types.VARCHAR);
            statement.executeUpdate();
            
            connection.close();
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
    }
    
    /*retrieve the account holder's first name from the database*/
     public static String retrieveFirstName(String nrc_number) 
             throws ClassNotFoundException, SQLException {
        
        ResultSet retrieved_first_name = null;
        
        Connection connection;
        
        try {
            
            connection = connectToDB();
            
            Statement statement = connection.createStatement();
            statement.executeQuery("select " + FNAME_COL + " from "
                    + TABLE_NAME + " where " + NRC_COL + " = " + nrc_number);
            
             retrieved_first_name = statement.getResultSet();
            
             
             if(retrieved_first_name.next() != false)
                 return retrieved_first_name.getString("first_name");
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
        return "No match";
      
    }
     
    /*store or update the account holder's first name to the database*/
    public static void storeOrUpdateFirstName(String first_name, String nrc_number) 
            throws ClassNotFoundException, SQLException {
        
        try {
            
            Connection connection = connectToDB();
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + FNAME_COL
                    + " = '" + first_name + "' where " + NRC_COL + " = '" + nrc_number + "'");
            
            connection.close();
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
    }
     
    /*retrieve the account holder's middle name from the database*/
     public static String retrieveMiddleName(String nrc_number) 
             throws ClassNotFoundException, SQLException {
        
        ResultSet retrieved_mid_name = null;
        
        Connection connection;
        
        try {
            
            connection = connectToDB();
            
            Statement statement = connection.createStatement();
            statement.executeQuery("select " + MIDNAME_COL + " from "
                    + TABLE_NAME + " where " + NRC_COL + " = " + nrc_number);
            
             retrieved_mid_name = statement.getResultSet();
            
             
             if(retrieved_mid_name.next() != false)
                 return retrieved_mid_name.getString(MIDNAME_COL);
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
        return "No match";
      
    }
    
    /*store or update the account holder's middle name to the database*/
    public static void storeOrUpdateMiddleName(String middle_name, String nrc_number)
            throws ClassNotFoundException, SQLException {
        
        try {
            
            Connection connection = connectToDB();
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + MIDNAME_COL
                    + " = '" + middle_name + "' where " + NRC_COL + " = '" + nrc_number + "'");
            
            connection.close();
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
    }
    
    /*retrieve the account holder's last name from the database*/ 
    public static String retrieveLastName(String nrc_number) 
            throws ClassNotFoundException, SQLException {
        
        ResultSet retrieved_last_name = null;
        
        Connection connection;
        
        try {
            
            connection = connectToDB();
            
            Statement statement = connection.createStatement();
            statement.executeQuery("select " + LNAME_COL + " from "
                    + TABLE_NAME + " where " + NRC_COL + " = " + nrc_number);
            
             retrieved_last_name = statement.getResultSet();
            
             
             if(retrieved_last_name.next() != false)
                 return retrieved_last_name.getString(LNAME_COL);
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
        return "No match";
      
    } 
    
    /*store or update the account holder's last name to the database*/
    public static void storeOrUpdateLastName(String last_name, String nrc_number)
            throws ClassNotFoundException, SQLException {
        
        try {
            
            Connection connection = connectToDB();
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + LNAME_COL
                    + " = '" + last_name + "' where " + NRC_COL + " = '" + nrc_number + "'");
            
            connection.close();
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
    }
    
    /*retrieve the account holder's nationality from the database*/ 
    public static String retrieveNationality(String nrc_number) 
            throws ClassNotFoundException, SQLException {
        
        ResultSet retrieved_nationality = null;
        
        Connection connection;
        
        try {
            
            connection = connectToDB();
            
            Statement statement = connection.createStatement();
            statement.executeQuery("select " + NATIONALITY_COL + " from "
                    + TABLE_NAME + " where " + NRC_COL + " = " + nrc_number);
            
             retrieved_nationality = statement.getResultSet();
            
             
             if(retrieved_nationality.next() != false)
                 return retrieved_nationality.getString(NATIONALITY_COL);
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
        return "No match";
      
    } 
    
    /*store or update the account holder's nationality to the database*/
    public static void storeOrUpdateNationality(String nationality, String nrc_number) 
            throws ClassNotFoundException, SQLException {
        
        try {
            
            Connection connection = connectToDB();
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + NATIONALITY_COL
                    + " = '" + nationality + "' where " + NRC_COL + " = '" + nrc_number + "'");
            
            connection.close();
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
    }
    
     /*retrieve the account holder's dob from the database*/ 
    public static String retrieveDOB(String nrc_number)
            throws ClassNotFoundException, SQLException {
        
        ResultSet retrieved_dob = null;
        
        Connection connection;
        
        try {
            
            connection = connectToDB();
            
            Statement statement = connection.createStatement();
            statement.executeQuery("select " + DOB_COL + " from "
                    + TABLE_NAME + " where " + NRC_COL + " = " + nrc_number);
            
             retrieved_dob = statement.getResultSet();
            
             
             if(retrieved_dob.next() != false)
                 return retrieved_dob.getString(DOB_COL);
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
        return "No match";
      
    } 
    
    /*store or update the account holder's dob to the database*/
    public static void storeOrUpdateDOB(String dob, String nrc_number) 
            throws ClassNotFoundException, SQLException {
        
        try {
            
            Connection connection = connectToDB();
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + DOB_COL
                    + " = '" + dob + "' where " + NRC_COL + " = '" + nrc_number + "'");
            
            connection.close();
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
    }
    
     /*retrieve the account holder's occupation from the database*/ 
    public static String retrieveOccupation(String nrc_number) 
            throws ClassNotFoundException, SQLException {
        
        ResultSet retrieved_occupation = null;
        
        Connection connection;
        
        try {
            
            connection = connectToDB();
            
            Statement statement = connection.createStatement();
            statement.executeQuery("select " + OCCUPATION_COL + " from "
                    + TABLE_NAME + " where " + NRC_COL + " = " + nrc_number);
            
             retrieved_occupation = statement.getResultSet();
            
             
             if(retrieved_occupation.next() != false)
                 return retrieved_occupation.getString(OCCUPATION_COL);
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
        return "No match";
      
    } 
    
    /*store or update the account holder's occupation to the database*/
    public static void storeOrUpdateOccupation(String occupation, String nrc_number) 
            throws ClassNotFoundException, SQLException {
        
        try {
            
            Connection connection = connectToDB();
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + OCCUPATION_COL
                    + " = '" + occupation + "' where " + NRC_COL + " = '" + nrc_number + "'");
            
            connection.close();
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
    }
   
    /*connect to the database*/
    private static Connection connectToDB() throws ClassNotFoundException,
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


