package banksy;

import java.sql.Array;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.Random;

/* Transaction is called by main to manage transactions.
In the future, it may have concurrency capabilities.
 */
public class Transaction {

    protected int accountID;
    protected int amount; //Positive amount means deposit, Negative amount means withdraw of some sort
    protected int otherAccountID; //NULL default, given a value if sending or taking money from account
    protected String transactionType; //
    protected Timestamp timestamp;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public Transaction() {

    }

    public Transaction(int accountID, int otherAccountID, int amount){
        this.accountID = accountID;
        this.otherAccountID = otherAccountID;
        this.amount = amount;
    }

    public Transaction CreateTransaction(int accountID, int otherAccountID, int amount, boolean willDeposit) throws SQLException {
        //Active Transactions
        //Simply creates the information for the transaction
        Maria_DBManager accountTable = new Maria_DBManager();
        Transaction record = new Transaction();

        // Changed references to new transaction record instead of current one
        //record.accountID = accountTable.doesAccountExist(accountID) ? accountID : null;
        try {
            if (accountTable.doesAccountExist(accountID) == true && accountTable.doesAccountExist(otherAccountID) == true) {
                record.accountID = accountID;
                record.otherAccountID = otherAccountID;
            }
        }
        catch(NullPointerException e){
            System.out.println("One of the two accounts do not exist in the Accounts Table");
            System.out.println(e.getMessage());
        }

        record.amount = amount;
        record.timestamp = new Timestamp(System.currentTimeMillis());
        record.transactionType = willDeposit == true ? "DEP" : "WTD";
        record.changeFunds(record.accountID, record.otherAccountID, amount, willDeposit);
        return record;
    }

    public void changeFunds(int accountID, int otherAccountID, int amount, boolean willDeposit) throws SQLException {
        Maria_DBManager accounts = new Maria_DBManager();

        if(willDeposit == true){
            // Need to get SQL record with Account ID 1 and Account ID 2 and need to modify amount
            accounts.changeFunds(accountID, -1*amount);
            accounts.changeFunds(otherAccountID, amount);
        }
        else{
            // Same logic but funds are modified in reverse
            accounts.changeFunds(accountID, amount);
            accounts.changeFunds(otherAccountID, -1*amount);
        }
    }

    public int generateRandomAccountID(int bound)
    {
        Random rand = new Random();
        int account = rand.nextInt(bound);
        return account;
    }

    public Transaction[] generateRandomTransactions(int num_transactions) throws SQLException {
        Maria_DBManager accounts = new Maria_DBManager();
        //Will use a function to bound the Random Numbers to User accounts
        Transaction[] transactions = new Transaction[num_transactions];
        int bounds = accounts.countUsers();
        Random rand = new Random();
        for(int transaction_number = 0; transaction_number < num_transactions; transaction_number++)
        {
            int account1 = generateRandomAccountID(bounds);
            int account2 = generateRandomAccountID(bounds);
            while(account1 == account2) {
                account2 = generateRandomAccountID(bounds);
            }
            transactions[transaction_number] = CreateTransaction(account1,account2,rand.nextInt(), rand.nextInt()%2==0 ? true:false);
        }
        return transactions;
    }

    // Time Stamp Functions
    public void makeTimestamp(){
        //makes a timestamp
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public void printCurrentTimestamp(){
        System.out.println(sdf.format(this.timestamp));
    }
}
