package banksy;

public class Main {

	public static void main(String[] args) 
	{
		DBManager db = new DBManager();
		db.createNewDatabase("Banksy.db");
	}

}
