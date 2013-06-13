package BankAccount;
import BankAccountDTO.BankAccountDTO;
import BankAccountDAO.BankAccountDAO;
import TransactionDAO.TransactionDAO;
import TransactionDTO.TransactionDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 6/10/13
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccount {
    private static BankAccountDAO bankAccountDao;
    private static BankAccountDTO bankAccountDTO;
    private static TransactionDAO transactionDAO;

    public static void setBankAccountDAO(BankAccountDAO accountDAO) {
        BankAccount.bankAccountDao = accountDAO;
    }

    public static BankAccountDTO openAccount(String accountNumber) {
        BankAccountDTO account = new BankAccountDTO(accountNumber);
        bankAccountDao.save(account);
        bankAccountDTO=account;
        return account;
    }

    public static TransactionDTO deposit(String accountNumber, double amount, String description) {

        BankAccountDTO bankaccountDTO = bankAccountDao.getBankAccountDTO(accountNumber);

        if(bankaccountDTO!=null){
            double balanceMoney =bankaccountDTO.getbalance()+amount;
            bankaccountDTO.setBalance(balanceMoney);
            bankAccountDao.save(bankAccountDTO);
            transactionDAO.save(new TransactionDTO(accountNumber,amount,description));
        }
        return new TransactionDTO(accountNumber,amount,description);
    }

    public static BankAccountDTO deposit(String accountNumber, long timestamp, double amount, String descripton) {
        BankAccountDTO bankaccountDTO = bankAccountDao.getBankAccountDTO(accountNumber);
        if(bankaccountDTO!=null){
            double balanceMoney =bankaccountDTO.getbalance()+amount;
            bankaccountDTO.setTimeStamp(timestamp);
            bankaccountDTO.setBalance(balanceMoney);
            bankAccountDao.save(bankAccountDTO);
        }
        return bankaccountDTO;
    }

    public static TransactionDTO withDraw(String accountNumber, double amount, String description) {
        BankAccountDTO bankaccountDTO = bankAccountDao.getBankAccountDTO(accountNumber);
        if(bankaccountDTO!=null){
            double balanceMoney =bankaccountDTO.getbalance()+amount;
            bankaccountDTO.setBalance(balanceMoney);
            bankAccountDao.save(bankAccountDTO);
            transactionDAO.save(new TransactionDTO(accountNumber,amount,description));
        }
        return new TransactionDTO(accountNumber,amount,description);
    }

    public static TransactionDTO withDraw(String accountNumber, long timestamp, double amount, String description) {
        BankAccountDTO bankaccountDTO = bankAccountDao.getBankAccountDTO(accountNumber);
        TransactionDTO transactionDTO = null;
        if(bankaccountDTO!=null){
            timestamp = System.currentTimeMillis();
            double balanceMoney =bankaccountDTO.getbalance()+amount;
            bankaccountDTO.setTimeStamp(timestamp);
            bankaccountDTO.setBalance(balanceMoney);
            bankAccountDao.save(bankAccountDTO);
            transactionDTO = new TransactionDTO(accountNumber,amount,description);
        }
        return transactionDTO;
    }

    public static void setTransactionDAO(TransactionDAO transaction) {
        BankAccount.transactionDAO = transaction;
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber) {
        List<TransactionDTO> listStransactionDTO = transactionDAO.getTransactionsOccurred(accountNumber);
        return listStransactionDTO;
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber, long startTime, long stopTime) {
        return transactionDAO.getTransactionsOccurred(accountNumber,startTime,stopTime);
    }
}
