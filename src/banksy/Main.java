package banksy;

import java.io.IOException;

public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		SQLite_DBManager dbm = new SQLite_DBManager();
		dbm.createNewDatabase("Banksy.db");
	}
}
