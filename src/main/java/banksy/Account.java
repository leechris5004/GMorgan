package banksy;

import java.util.Random;

public class Account
{
    private String accountType;
    private int amount;
    //private int userID

    public Account(){}
    public Account(String accountType, int amount)
    {
        this.accountType = accountType;
        this.amount = amount;
    }

    public Account generateAccount()
    {
        Account generated = new Account();

        Random rand = new Random();
        String[] accountTypes = {"DEP","WTD"};
        int generatedAmount = rand.nextInt(1000);
        generated.setAmount(generatedAmount);
        generated.setAccountType(accountTypes[rand.nextInt(accountTypes.length)]);
        return generated;
    }


    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
