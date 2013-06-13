package TransactionDAO;

import BankAccountDTO.BankAccountDTO;
import TransactionDTO.TransactionDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 6/13/13
 * Time: 8:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionDAO {
    public void save(TransactionDTO transactionDTO) {
    }

    public BankAccountDTO getBankAccountDTO(String accountNumber) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public List<TransactionDTO> getTransactionsOccurred(String accountNumber) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public List<TransactionDTO> getTransactionsOccurred(String accountNumber, long startTime, long stopTime) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
