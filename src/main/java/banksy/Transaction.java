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
    protected String depositType; //
    protected Timestamp timestamp;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    // Needed for Transaction population methods
    public Transaction(){

    }

    // Added another constructor
    public Transaction(int accountID, int amount){
        this.accountID = accountID;
        this.amount = amount;
    }

    public void CreateTransaction(int accountID, int amount) throws SQLException {
        //Active Transactions
        //Simply creates the information for the transaction
        Transaction record = new Transaction();
        Maria_DBManager accountTable = new Maria_DBManager();

        // Changed references to new transaction record instead of current one
        record.accountID = accountID;
        record.amount = amount;
        record.timestamp = new Timestamp(System.currentTimeMillis());
        record.checkType();

    }

    // Deposit Type Functions
    public void checkType(){//simply checks the type
        this.depositType =  this.amount > 0 ? "DEP" : "WTD" ;
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
