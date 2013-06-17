package Transaction;

import TransactionDAO.TransactionDAO;
import TransactionDTO.TransactionDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 6/17/13
 * Time: 8:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class Transaction {
    private static TransactionDAO transactionDAO;
    public static void get(String accountNumber, long startTime, long stopTime) {
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber,long startTime,long stopTime){
        return transactionDAO.getTransactionsOccurred(accountNumber,startTime,stopTime);
    }
    public static void setTransactionDAO(TransactionDAO transaction) {
        Transaction.transactionDAO = transaction;
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber) {
        return transactionDAO.getTransactionsOccurred(accountNumber);  //To change body of created methods use File | Settings | File Templates.
    }

    public static void save(TransactionDTO transactionDTO) {
        transactionDAO.save(transactionDTO);
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber, int numberNewest) {
        return transactionDAO.getTransactionsOccurred(accountNumber,numberNewest);
    }
}
