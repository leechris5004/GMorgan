package banksy;

import java.sql.Timestamp;

/* Transaction is called by main to manage transactions.
In the future, it may have concurrency capabilities.
 */
public class Transaction {

    protected int userID;
    protected int accountID;
    protected int amount; //Positive amount means deposit, Negative amount means withdraw of some sort
    protected int otherAccountID; //NULL default, given a value if sending or taking money from account
    protected String depositType; //
    protected Timestamp TIMESTAMP;



    public void CreateTransaction(int userID, int accountID, int amount) {
        //Active Transactions

    }

    public void checkType(){
        if(this.amount > 0){
            this.depositType = "Deposit";
        }
        else{
            this.depositType = "Withdraw";
        }
        

    }
}
