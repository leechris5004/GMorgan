package banksy;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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

    // Needed for Transaction population methods
    public Transaction(){

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

    // show the change funds be in Transaction
    // I think I need the DBManager to be able to change funds

    public void changeFunds(int accountID, int otherAccountID, int amount, boolean willDeposit) throws SQLException
    {
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

    // Time Stamp Functions
    public void makeTimestamp(){
        //makes a timestamp
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public void printCurrentTimestamp(){
        System.out.println(sdf.format(this.timestamp));
    }
}
