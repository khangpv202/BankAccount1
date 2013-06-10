package BankAccountDTO;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 6/10/13
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDTO {
    private double  balance;
    private String accountNumber;

    public double getbalance() {
        return this.balance;  //To change body of created methods use File | Settings | File Templates.
    }
    public void setBalance(Double value){
        this.balance= value;
    }

    public void setAccountNumber(String accountnumber){
        this.accountNumber= accountnumber;
    }
    public String getAccountNumber(){
        return this.accountNumber;
    }
}
