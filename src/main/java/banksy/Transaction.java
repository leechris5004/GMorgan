package banksy;

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

    public void CreateTransaction(int userID, int accountID, int amount) {
        //Active Transactions
        //Simply creates the information for the transaction
        Transaction record = new Transaction();
        this.accountID = accountID;
        this.amount = amount;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        record.checkType();
    }

    public void checkType(){//simply checks the type
        this.depositType =  this.amount > 0 ? "DEP" : "WTD" ;
    }

    public void makeTimestamp(){
        //makes a timestamp
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public void printCurrentTimestamp(){
        System.out.println(sdf.format(this.timestamp));
    }

    public boolean checkUserID(String userID){
        //Checks if there's a valid user ID
        //if there is, passes true then changes
        return true;

    }
    public boolean checkAccountID(String AccountID){
        //Checks if there's a valid account ID
        //If passing that validation, makes the type accountID
        return true;
    }

}
