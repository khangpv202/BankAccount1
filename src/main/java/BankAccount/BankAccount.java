package BankAccount;
import BankAccountDTO.BankAccountDTO;
import BankAccountDAO.BankAccountDAO;

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
        bankAccountDTO = account;
        return account;
    }

    public static void deposit(double amount) {
        double  balanceMoney =bankAccountDTO.getbalance()+amount;
        bankAccountDTO.setBalance(balanceMoney);
        bankAccountDao.save(bankAccountDTO);

    }
}
