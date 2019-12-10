package banksy;

import java.io.IOException;
import static spark.Spark.*;

public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		get("/hello", (req, res) -> "Hello World");
		/*String fileName = "Banksy.db";
		SQLite_DBManager dbm = new SQLite_DBManager();
		dbm.createNewDatabase(fileName);
		
		String sql = "IF NOT EXISTS user (\n"
                + "    user_id integer PRIMARY KEY,\n"
                + "    name text NOT NULL,\n"
                + "    user_ssn integer\n"
                + ");";
		dbm.createNewTable(fileName, sql);
		dbm.showAllTables(fileName);*/
	}
}
