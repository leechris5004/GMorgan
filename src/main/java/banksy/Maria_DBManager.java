package banksy;

import java.sql.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Maria_DBManager implements DBManager {

	public Connection conn;

	static final String JDBC_DRIVER = "org.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://ec2-52-202-114-229.compute-1.amazonaws.com/banksy";

    //  Database credentials
    static final String USER = "banksy";
    static final String PASS = "password";

    Maria_DBManager() throws SQLException {
        conn = DriverManager.getConnection(
                "jdbc:mysql://ec2-52-202-114-229.compute-1.amazonaws.com:3306/banksy", USER, PASS);
    }
//======================================================================================================================
    //Server Connection Functions

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

	public void connectToServer() {

		Connection conn = null;
				Statement stmt = null;
				try {
						//STEP 2: Register JDBC driver
						Class.forName("com.mysql.jdbc.Driver");

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
//======================================================================================================================
	//User Table Functions
	public void printusers() throws SQLException {
    String sql = "Select * from users";
    Statement stmt;
    stmt = conn.createStatement();
    ResultSet results = stmt.executeQuery(sql);
        while (results.next()) {
            System.out.println(results.getString("user_email") + ", " +
                    results.getString("user_first") + ", " +
                    results.getString("user_last") + ", " +
                    results.getString("user_address") + ", " +
                    results.getString("user_ssn") );

        }
    }

    public void generateUser(int num_users) throws SQLException {
        User u = new User();
        for(int i = 0; i < num_users; i++)
        {
            User new_user = u.generateUser();
            addUser(new_user.getFirstName(), new_user.getLastName(), new_user.getEmail(), new_user.getSsn(), new_user.getAddress());
        }
    }

	public boolean doesUserExist(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_email = ?";
        PreparedStatement  prepStmt;

        prepStmt = conn.prepareStatement(sql);
        prepStmt.setString(1, email);
        ResultSet results = prepStmt.executeQuery();
      return (results.next());
    }

    public boolean doesUserExist(int uid) throws SQLException {
        String sql = "SELECT * FROM users WHERE userID = ?";
        PreparedStatement  prepStmt;

        prepStmt = conn.prepareStatement(sql);
        prepStmt.setInt(1,uid);
        ResultSet results = prepStmt.executeQuery();
        return (results.next());
    }

    public int getUserID(String email) throws SQLException {
        if(!doesUserExist(email)){
            return 0;
        }else{
            String sql = "SELECT * from users where user_email = ?";
            PreparedStatement  prepStmt;

            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1,email);
            ResultSet results = prepStmt.executeQuery();
            results.next();
            return results.getInt("userID");
        }

    }


    public void doesUserExist(String fname, String lname) {
        String sql = " SELECT COUNT(1) FROM users WHERE user_first =" + fname + " AND " + "user_last =" + lname;
    }

    public void addUser(String firstName, String lastName, String email, String ssn, String address) throws SQLException {
        String sql = "Insert into users(user_first, user_last, user_ssn, user_address,user_email)"  + "Values(?,?,?,?,?)";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        prepStmt.setString(1, firstName);
        prepStmt.setString(2, lastName);
        prepStmt.setString(3, ssn);
        prepStmt.setString(4, address);
        prepStmt.setString(5, email);
    try {
        prepStmt.executeUpdate();
    }
    catch(Exception e){
        System.out.println("That user already exists.");
        }
    }

    //======================================================================================================================
    //Account Table Functions
    public void addAccount(String accountType, int amount) throws SQLException {
        String sql = "Insert into accounts(accountType, amount)"  + "Values(?,?)";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        prepStmt.setString(1, accountType);
        prepStmt.setInt(2, amount);
        try {
            prepStmt.executeUpdate();
        }
        catch(Exception e){
            System.out.println("That account already exists.");
        }
    }

    public void generateAccounts(int num_accounts) throws SQLException {
        Account a = new Account();
        for(int i = 0; i < num_accounts; i++)
        {
            Account new_account = a.generateAccount();
            addAccount(new_account.getAccountType(), new_account.getAmount());
        }
    }

    public boolean doesAccountExist(String accountType, int amount) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE accountType = ? AND amount = ?";
        PreparedStatement prepStmt;

        prepStmt = conn.prepareStatement(sql);
        prepStmt.setString(1,accountType);
        prepStmt.setInt(2, amount);
        ResultSet results = prepStmt.executeQuery();
        return(results.next());
    }
    public boolean doesAccountExist(int aid) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE accountID = ?";
        PreparedStatement  prepStmt;

        prepStmt = conn.prepareStatement(sql);
        prepStmt.setInt(1,aid);
        ResultSet results = prepStmt.executeQuery();
        return (results.next());
    }

    public void printAccounts() throws SQLException {
        String sql = "Select * from accounts";
        Statement stmt;
        stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery(sql);
        while (results.next()) {
            System.out.println(results.getString("accountType") + ", " +
                    results.getString("amount"));
        }
    }

    public void assignAccount(int userID, int accountID) throws SQLException {
        PreparedStatement prepStmt;
        if (!doesUserExist(userID) || !doesAccountExist(accountID)){
            System.out.println("This user does not exist. Not assigning account.");
        }else{
            String sql = "update accounts set userID = ? where accountID = ?";

            prepStmt = conn.prepareStatement(sql);

            prepStmt.setInt(1, userID);
            prepStmt.setInt(2, accountID);

            prepStmt.executeUpdate();

        }
    }


    //etc
    public void changeFunds(int amount){

    }

	@Override
	public void createRegistrationTable(String fileName) throws IOException {
		Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

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

}
