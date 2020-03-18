/*Author - DividedByZ3r0*/
/*Since we are re-designing the whole atm project to an fxml aplication,with the idea of separating the business logic from the
 the graphical user interface the db handler class is the core of our program because it allos us to interact with the
 db on our local machine. If the db was to be hosted on a remote server then it would be resposible for connection
 with the remotely hosted db*/
package utils;

/*importting all the packages we are going to be needing in the Class*/
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHandler {
    /*Declaring all the class varibles to be used within the class or any sub-classes*/
    protected static final String DB_URL = "jdbc:mysql://localhost:3306/atm";
    protected static final String UNAME = "root";
    protected static final String PASS = "thebestdamnthingever2!";
    protected static Connection con = null;
    protected static Statement stmt = null;

    /*Default constructor to connect the db and setup any tables we are going to need in the db*/
    public DatabaseHandler(){
        createConnection();
        setupLoginTable();
        setUpAccountHolderTable();
        setUpAccountTable();
        setUpTransactionTable();
        setUpContactInfoTable();
    }

    /*The createConnection method returns a Connection obj and is responsiblre for creating a coonection to the db*/
    public static final Connection createConnection(){

        /*load the JDBC driver and establish a connection to the database*/
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
             con = DriverManager.getConnection(DB_URL, UNAME, PASS);
             // the following linee only for testing purposes System.out.println("Connection Succesful!");
        }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){
            return null;
            /*No need to close the connection once we are done because after execution exits the try-catch
            block the connection is automatically closed for us*/
        }
        return con;
    }

    /*The folling method is a template method to setup a table within the db
      All subsiquent methods of setting up the required tables will folow the same format*/
    protected static final void setupLoginTable(){
        /*We save the table to a String variable*/
        String TABLE_NAME = "user_login";
        try {
            /*Creating a statement to execute in our db*/
            stmt = con.createStatement();

            /*DatabaseMetaData Allows us to gather db meta data such as  informstion about tables, views, column types, results sets, stored procedures etc*/
            DatabaseMetaData dbmd = con.getMetaData();
            try (

              /*The folling command checks to see if there are any tables in the db with the same name as TABLE_NAME*/
              ResultSet tables = dbmd.getTables(null, null, TABLE_NAME, null)) {

                /*If there is already a table in the db with the same name as TABLE_NAME...*/
                if(tables.next()){
                    System.out.println("Table " + TABLE_NAME + " already exists...");
                }else{
                    /*If the table doesnt exist create a table w/ name TABLE_NAME and the following columns*/
                    stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                            + " id VARCHAR(4) primary key,"
                            + " username VARCHAR(15),"
                            + " password VARCHAR(15)" + ")");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*this methods sets up the accoun holder table*/
    protected static final void setUpAccountHolderTable() {
        
        /*We save the table to a String variable*/
        String TABLE_NAME = "account_holder";
        
        try {
            /*Creating a statement to execute in our db*/
            stmt = con.createStatement();

            /*DatabaseMetaData Allows us to gather db meta data such as  informstion about tables, views, column types, results sets, stored procedures etc*/
            DatabaseMetaData dbmd = con.getMetaData();
            try (

              /*The folling command checks to see if there are any tables in the db with the same name as TABLE_NAME*/
              ResultSet tables = dbmd.getTables(null, null, TABLE_NAME, null)) {

                /*If there is already a table in the db with the same name as TABLE_NAME...*/
                if(tables.next()){
                    System.out.println("Table " + TABLE_NAME + " already exists...");
                }
                
                else{
                    /*If the table doesnt exist create a table w/ name TABLE_NAME and the following columns*/
                    stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                            + "nrc_number VARCHAR(9) primary key,"
                            + " first_name VARCHAR(20),"
                            + " middle_name VARCHAR(20), last_name VARCHAR(20), dob DATE, nationality VARCHAR(45))");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*this method sets up the accoun table*/
    protected static final void setUpAccountTable() {
        
        /*We save the table to a String variable*/
        final String TABLE_NAME = "account";
        
        try {
            /*Creating a statement to execute in our db*/
            stmt = con.createStatement();

            /*DatabaseMetaData Allows us to gather db meta data such as  informstion about tables, views, column types, results sets, stored procedures etc*/
            DatabaseMetaData dbmd = con.getMetaData();
            try (

              /*The folling command checks to see if there are any tables in the db with the same name as TABLE_NAME*/
              ResultSet tables = dbmd.getTables(null, null, TABLE_NAME, null)) {

                /*If there is already a table in the db with the same name as TABLE_NAME...*/
                if(tables.next()){
                    System.out.println("Table " + TABLE_NAME + " already exists...");
                }
                
                else{
                    /*If the table doesnt exist create a table w/ name TABLE_NAME and the following columns*/
                    stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                            + " account_number INT(9) primary key,"
                            + " holder_nrc_number VARCHAR(9)," +  "pin INT(4), balance INT(11)");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*this method sets up the transaction table*/    
    protected static final void setUpTransactionTable() {
        
        /*We save the table to a String variable*/
        final String TABLE_NAME = "transaction";
        
        try {
            /*Creating a statement to execute in our db*/
            stmt = con.createStatement();

            /*DatabaseMetaData Allows us to gather db meta data such as  informstion about tables, views, column types, results sets, stored procedures etc*/
            DatabaseMetaData dbmd = con.getMetaData();
            try (

              /*The folling command checks to see if there are any tables in the db with the same name as TABLE_NAME*/
              ResultSet tables = dbmd.getTables(null, null, TABLE_NAME, null)) {

                /*If there is already a table in the db with the same name as TABLE_NAME...*/
                if(tables.next()){
                    System.out.println("Table " + TABLE_NAME + " already exists...");
                }
                
                else{
                    /*If the table doesnt exist create a table w/ name TABLE_NAME and the following columns*/
                    stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                            + " id INT(9) primary key, date DATE, type VARCHAR(15), amount_transacted INT(11),"
                            + " start_time TIME, end_time TIME, account_number INT(11))");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    protected static final void setUpContactInfoTable() {
        
        /*We save the table to a String variable*/
        final String TABLE_NAME = "contact_information";
        
        try {
            /*Creating a statement to execute in our db*/
            stmt = con.createStatement();

            /*DatabaseMetaData Allows us to gather db meta data such as  informstion about tables, views, column types, results sets, stored procedures etc*/
            DatabaseMetaData dbmd = con.getMetaData();
            try (

              /*The folling command checks to see if there are any tables in the db with the same name as TABLE_NAME*/
              ResultSet tables = dbmd.getTables(null, null, TABLE_NAME, null)) {

                /*If there is already a table in the db with the same name as TABLE_NAME...*/
                if(tables.next()){
                    System.out.println("Table " + TABLE_NAME + " already exists...");
                }
                
                else{
                    /*If the table doesnt exist create a table w/ name TABLE_NAME and the following columns*/
                    stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                            + " nrc_number VARCHAR(9) references account_holder(nrc_number), email VARCHAR(50), phone_number INT(10))");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }

