package banksy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
			String url = "jdbc:sqlite:"+dir+"\\"+fileName;
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established");
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try 
			{
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}
