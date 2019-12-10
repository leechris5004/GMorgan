package banksy;

//Just a Test
import java.util.Scanner;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class SQLite_DBManager implements DBManager {

	@Override
	public void createNewDatabase(String fileName) throws IOException 
	{	
		File db = new File(fileName);
		String dir = System.getProperty("user.dir");
		if(!db.exists()) 
		{
			try 
			{
				db.createNewFile();
			}catch(Exception e) {
				System.out.println("Error in File Creation Process");
				e.printStackTrace();
			}
		}
		
		Connection conn = null;
		try {
			String url = "jdbc:sqlite:"+dir+"\\src\\banksy\\"+fileName;
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established");
			System.out.println("Created the "+fileName+" DB at "+dir+"\\src\\banksy\\"+fileName);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try 
			{
				System.out.println("Closing Database");
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
//	public String generateSQL() 
//	{
//		System.out.println("Enter your sql command");
//		Scanner in = new Scanner(System.in);
//		StringBuffer sql = new StringBuffer();
//		String str = " ";
//		while(in.hasNextLine()&& !(str = in.nextLine()).equals(" "))
//		{
//			sql.append(str+"\n");
//			System.out.println(sql);
//		}
//		sql.append(");");
//		return sql.toString();
//	}
	
	
	public void createNewTable(String fileName, String sql) throws IOException 
	{
		String dir = System.getProperty("user.dir");
		String url = "jdbc:sqlite:"+dir+"\\src\\banksy\\"+fileName;
		String sql_cmd = "CREATE TABLE "+ sql;
		try(Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement())
		{
			stmt.execute(sql_cmd);
		} catch (SQLException e) {
			System.out.println("Error occurred while executing sql statement");
			System.out.println(e.getMessage());
		}
	}

	public void showAllTables(String fileName) throws IOException
	{
		String dir = System.getProperty("user.dir");
		String url = "jdbc:sqlite:"+dir+"\\src\\banksy\\"+fileName;
		String sql_cmd = "SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%'";
		try(Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement())
		{
			System.out.println(stmt.execute(sql_cmd));
			System.out.println(stmt.getResultSet().getRow());
		} catch (SQLException e) {
			System.out.println("Error occurred while executing sql statement");
			System.out.println(e.getMessage());
		}
	}
	
	
}
