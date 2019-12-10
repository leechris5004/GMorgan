package banksy;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
//Just a Test
import java.util.Scanner;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class Maria_DBManager implements DBManager {

	Connection conn = null;


	static final String JDBC_DRIVER = "org.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://ec2-52-202-114-229.compute-1.amazonaws.com/banksy";

    //  Database credentials
    static final String USER = "banksy";
    static final String PASS = "password";


    public void disconnectFromServer() {
    	try {
            if (conn != null) {
                conn.close();
                System.out.println("Connected Closed Successfully...");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }//end finally try
    }

	public void connectToServer(){

		Connection conn = null;
				Statement stmt = null;
				try {
						//STEP 2: Register JDBC driver
						Class.forName("com.mariadb.jdbc.Driver");

						//STEP 3: Open a connection
						System.out.println("Connecting to a selected database...");
						conn = DriverManager.getConnection(
										"jdbc:mysql://ec2-52-202-114-229.compute-1.amazonaws.com:3306/banksy", USER, PASS);
						System.out.println("Connected database successfully...");
	} catch (SQLException se) {
        //Handle errors for JDBC
        se.printStackTrace();
    } catch (Exception e) {
        //Handle errors for Class.forName
        e.printStackTrace();
    } finally {
        //finally block used to close resources
        try {
            if (stmt != null) {
                conn.close();
            }
        } catch (SQLException se) {
        }// do nothing
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }//end finally try
    }//end try

	}

	public void doesUserExist(int value) throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();
        String sql = " SELECT COUNT(1) FROM users WHERE userID =" + value;
    }

    public void doesUserExist(String fname, String lname){
        String sql = " SELECT COUNT(1) FROM users WHERE user_first =" + fname + " AND " + "user_last =" + lname;
    }

    public void addUser(String firstName, String lastName, String email, String ssn, String address) throws SQLException {
        connectToServer();

        String sql = "Insert into users(user_first, user_last, user_ssn, user_address, user_address,user_email)"  + "Values(?,?,?,?,?)";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        prepStmt.setString(1, firstName);
        prepStmt.setString(2, lastName);
        prepStmt.setString(3, ssn);
        prepStmt.setString(4, address);
        prepStmt.setString(5, email);

        prepStmt.executeUpdate();

        disconnectFromServer();
    }
    public void changeFunds(int amount){

    }



	@Override
	public void createRegistrationTable(String fileName) throws IOException
	{
		Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mariadb.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://ec2-52-202-114-229.compute-1.amazonaws.com:3306/banksy", USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();

            String sql = "CREATE TABLE REGISTRATION "
                    + "(id INTEGER not NULL, "
                    + " first VARCHAR(255), "
                    + " last VARCHAR(255), "
                    + " age INTEGER, "
                    + " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
//end JDBCExample


}
