package banksy;

import com.j256.ormlite.stmt.BaseArgumentHolder;

import java.io.IOException;
import java.sql.SQLException;


public class Main {

	public static void main(String[] args) throws IOException, SQLException {
	Maria_DBManager	banksyAccess = new Maria_DBManager();

	/**
	//System.out.println(banksyAccess.getUserInfo(15));
	for(int i = 1; i < banksyAccess.countAccounts(); i++)
		System.out.println(banksyAccess.getAccountInfo(i));
	Transaction t = new Transaction();
	Transaction[] transactions = t.generateRandomTransactions(50);
	for(Transaction transaction:transactions)
	{
		banksyAccess.addTransaction(transaction.accountID, transaction.otherAccountID, transaction.amount, transaction.transactionType);
	}

	//Get's 5 most recent transactions
	banksyAccess.getMostRecentTransactions();
	*/
	String transactions = banksyAccess.getMostRecentTransactions(8);
	System.out.print(transactions);

	
	//banksyAccess.setPassword(14, "password");
	//System.out.println(banksyAccess.realPasswordCheck(14,"password"));
	//System.out.println(banksyAccess.countUsers());


	//banksyAccess.changeFunds(3,-40);
	//System.out.println(banksyAccess.getUserID("vickie.will@yahoo.com"));
//	banksyAccess.addAccount("DEP",0);
//    banksyAccess.generateAccounts(10);
//	banksyAccess.printaccounts();
	//banksyAccess.addUser("Gabriel","Morgan","gabrielmorgan1230@gmail.com","123456789","343 east 51st");
	//	banksyAccess.printusers();

	}
}


