package banksy;

import java.sql.Connection;  
import java.sql.DatabaseMetaData;  
import java.sql.DriverManager;  
import java.sql.SQLException; 

public class DBManager
//merge conflictsa
{
	public static void createNewDatabase(String fileName) 
	{
		//String url =  "jdbc:sqlite:~\\eclipse-workspace\\GMorgan\\src" + fileName;
		String urlpath = System.getProperty("user.dir")+"\\src\\banksy";
		System.out.println(urlpath);
		try {
			Connection conn = DriverManager.getConnection(urlpath);
			if(conn != null) 
			{
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is "+meta.getDriverName());
				System.out.println("A new Database has been created at "+urlpath);
			}
		}catch(SQLException e) 
		{
			System.out.println(e.getMessage());
			//Hello
		}
	}
}