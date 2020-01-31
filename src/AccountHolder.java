package atm_interface.src;
import java.sql.*;


/**
 *
 * @author MTH2
 * This is the account holder class for the ATM program. The class has methods
 * for storing or updating the various properties of an account
 * holder including the nrc number, the first name, middle name, last name, 
 * nationality etc. The various properties are stored and retrieved based on
 * the nrc number that the user enters into a text field hence the retrieve 
 * method takes a column(attribute) name and nrc number as argument.
 * On the other hand all the store methods take a particular attribute value
 * and an nrc number as arguments. All methods in this class are static so 
 * there's no need for instantiation.
 * The class uses the connectToDB() method contained in the ATM class that 
 * connects to the database and returns a Connection object to avoid redundant 
 * "connection to database" code in the methods.
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
 
    /*retrieve the property specified by col_name by the nrc_number*/
    public static String retrieveData(String col_name, String nrc_number)
        throws ClassNotFoundException, SQLException {
        
        Connection connection;
        ResultSet retrieved_data = null;
        
        try {
            
            connection = ATM.connectToDB();
            
            Statement statement = connection.createStatement();
            
            statement.executeQuery("select * from " + TABLE_NAME + " where "
             + NRC_COL + " = '" + nrc_number + "'");
            
            retrieved_data = statement.getResultSet();
            
            if(retrieved_data.next() != false) {
                
                switch(col_name) {
                    
                    case NRC_COL: return retrieved_data.getString(NRC_COL);
                    case FNAME_COL: return retrieved_data.getString(FNAME_COL);
                    case MIDNAME_COL: return retrieved_data.getString(MIDNAME_COL);
                    case LNAME_COL: return retrieved_data.getString(LNAME_COL);
                    case NATIONALITY_COL: return retrieved_data.getString(NATIONALITY_COL);
                    case DOB_COL: return retrieved_data.getString(DOB_COL);
                    case OCCUPATION_COL: return retrieved_data.getString(OCCUPATION_COL);
                    
                }
            }
        
            
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
            
            Connection connection = ATM.connectToDB();
            
            PreparedStatement statement = connection.prepareStatement
                ("insert into " + TABLE_NAME  + "(" + NRC_COL + ", " + FNAME_COL
                        + ", " + MIDNAME_COL + ", " + LNAME_COL + ", " + 
                                NATIONALITY_COL + ", " + DOB_COL + ", " + 
                                OCCUPATION_COL + ") values(?, ?, ?, ?, ?, ?, ?)");
            
            //if the nrc number already exists DO NOT store it
            if(!retrieveData(NRC_COL, nrc_number).equals("No Match")) {
                
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
    
    /*store or update the account holder's first name to the database*/
    public static void storeOrUpdateFirstName(String first_name, String nrc_number) 
            throws ClassNotFoundException, SQLException {
        
        try {
            
            Connection connection = ATM.connectToDB();
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + FNAME_COL
                    + " = '" + first_name + "' where " + NRC_COL + " = '" + nrc_number + "'");
            
            connection.close();
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
    }
    
    /*store or update the account holder's middle name to the database*/
    public static void storeOrUpdateMiddleName(String middle_name, String nrc_number)
            throws ClassNotFoundException, SQLException {
        
        try {
            
            Connection connection = ATM.connectToDB();
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + MIDNAME_COL
                    + " = '" + middle_name + "' where " + NRC_COL + " = '" + nrc_number + "'");
            
            connection.close();
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
    }
    
    /*store or update the account holder's last name to the database*/
    public static void storeOrUpdateLastName(String last_name, String nrc_number)
            throws ClassNotFoundException, SQLException {
        
        try {
            
            Connection connection = ATM.connectToDB();
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + LNAME_COL
                    + " = '" + last_name + "' where " + NRC_COL + " = '" + nrc_number + "'");
            
            connection.close();
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
    }
    
    /*store or update the account holder's nationality to the database*/
    public static void storeOrUpdateNationality(String nationality, String nrc_number) 
            throws ClassNotFoundException, SQLException {
        
        try {
            
            Connection connection = ATM.connectToDB();
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + NATIONALITY_COL
                    + " = '" + nationality + "' where " + NRC_COL + " = '" + nrc_number + "'");
            
            connection.close();
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
    }
    
    /*store or update the account holder's dob to the database*/
    public static void storeOrUpdateDOB(String dob, String nrc_number) 
            throws ClassNotFoundException, SQLException {
        
        try {
            
            Connection connection = ATM.connectToDB();
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + DOB_COL
                    + " = '" + dob + "' where " + NRC_COL + " = '" + nrc_number + "'");
            
            connection.close();
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
    }
    
    /*store or update the account holder's occupation to the database*/
    public static void storeOrUpdateOccupation(String occupation, String nrc_number) 
            throws ClassNotFoundException, SQLException {
        
        try {
            
            Connection connection = ATM.connectToDB();
            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate("update " + TABLE_NAME + " set " + OCCUPATION_COL
                    + " = '" + occupation + "' where " + NRC_COL + " = '" + nrc_number + "'");
            
            connection.close();
            
        }
        catch(Exception exception) {
            
            exception.printStackTrace();
        }
        
    }
   
    


} 
    





