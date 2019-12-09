package banksy;

import java.io.IOException;

public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		String fileName = "Banksy.db";
		SQLite_DBManager dbm = new SQLite_DBManager();
		dbm.createNewDatabase(fileName);
		dbm.createNewTable(fileName);
	}
}
