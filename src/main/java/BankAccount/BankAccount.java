package BankAccount;
import BankAccountDTO.BankAccountDTO;
import BankAccountDAO.BankAccountDAO;

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

    public static void setBankAccountDAO(BankAccountDAO accountDAO) {
        BankAccount.bankAccountDao = accountDAO;
    }

    public static BankAccountDTO openAccount(String accountNumber) {
        BankAccountDTO account = new BankAccountDTO(accountNumber);
        bankAccountDao.save(account);
        bankAccountDTO=account;
        return account;
    }

    public static BankAccountDTO deposit(String accountNumber, double amount, String description) {

        BankAccountDTO bankaccountDTO = bankAccountDao.getBankAccountDTO(accountNumber);
        if(bankaccountDTO!=null){
            double balanceMoney =bankaccountDTO.getbalance()+amount;
            bankaccountDTO.setBalance(balanceMoney);
            bankAccountDao.save(bankAccountDTO);
        }
        return bankaccountDTO;
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

    public static BankAccountDTO withDraw(String accountNumber, double amount, String description) {
        BankAccountDTO bankaccountDTO = bankAccountDao.getBankAccountDTO(accountNumber);
        if(bankaccountDTO!=null){
            double balanceMoney =bankaccountDTO.getbalance()+amount;
            bankaccountDTO.setBalance(balanceMoney);
            bankAccountDao.save(bankAccountDTO);
        }
        return bankaccountDTO;
    }

    public static BankAccountDTO withDraw(String accountNumber, long timestamp, double amount, String description) {
        BankAccountDTO bankaccountDTO = bankAccountDao.getBankAccountDTO(accountNumber);
        if(bankaccountDTO!=null){
            double balanceMoney =bankaccountDTO.getbalance()+amount;
            bankaccountDTO.setTimeStamp(timestamp);
            bankaccountDTO.setBalance(balanceMoney);
            bankAccountDao.save(bankAccountDTO);
        }
        return bankaccountDTO;
    }

    public static List<BankAccountDTO> getTransactionsOccurred(BankAccountDTO accountNumber) {
        List<BankAccountDTO> listDTO = bankAccountDao.getTransactionsOccurred(accountNumber);
        if(listDTO!=null)
            System.out.println("not null");
        else System.out.println("null");
        return listDTO;
    }
}
