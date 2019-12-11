package banksy;

import java.io.IOException;
import java.sql.SQLException;


public class Main {

	/*
	public static void main(String[] args) throws IOException
	{
		get(new Route("/hello") {
			@Override
			public Object handle(Request request, Response response) {
				return "Hello World";
			}
		});

	}

*/
	public static void main(String[] args) throws IOException, SQLException {
	Maria_DBManager	banksyAccess = new Maria_DBManager();

	banksyAccess.setPassword(14, "password");
	System.out.println(banksyAccess.realPasswordCheck(14,"password"));
	//Transaction t = new Transaction(3,1,100);
//	t.CreateTransaction(3,1,100, true);

	//banksyAccess.changeFunds(3,-40);
	//System.out.println(banksyAccess.getUserID("vickie.will@yahoo.com"));
//	banksyAccess.addAccount("DEP",0);
//    banksyAccess.generateAccounts(10);
//	banksyAccess.printaccounts();
	//banksyAccess.addUser("Gabriel","Morgan","gabrielmorgan1230@gmail.com","123456789","343 east 51st");
	//	banksyAccess.printusers();

	}
}


