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
    protected static final String DB_URL = "jdbc:mysql://localhost:3306/example";
    protected static final String UNAME = "root";
    protected static final String PASS = "DividedByZ3r0";
    protected static Connection con = null;
    protected static Statement stmt = null;

    /*Default constructor to connect the db and setup any tables we are going to need in the db*/
    public DatabaseHandler(){
        createConnection();
        setupTable();
    }

    /*The createConnection method returns a Connection obj and is responsiblre for creating a coonection to the db*/
    protected static final Connection createConnection(){

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
    protected static final void setupTable(){
        /*We save the table to a String variable*/
        String TABLE_NAME = "User_logins";
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
                            + " id varchar(4) primary key autoincrement,"
                            + " username varchar(15),"
                            + " password varchar(15)" + ")");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}