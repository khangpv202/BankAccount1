package TransactionDTO;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 6/12/13
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionDTO {
    private String accountNumber;
    private double amount;
    private final String description;
    private boolean executed = false;
    private long timestamp;

    public TransactionDTO(String accountNumber, double amount, String description) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.description = description;
        this.timestamp = System.currentTimeMillis();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isExecuted() {
        return executed;
    }

    public long getTimeStamp() {
        return timestamp;
    }
}
