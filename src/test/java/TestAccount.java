import BankAccount.BankAccount;
import BankAccountDAO.BankAccountDAO;
import BankAccountDTO.BankAccountDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 6/10/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestAccount {
    BankAccountDAO mockAccount = mock(BankAccountDAO.class);


    @Before
    public void initial(){
        reset(mockAccount);
        BankAccount.setBankAccountDAO(mockAccount);
    }

    @Test
    public void testNewAccount(){
        BankAccount.openAccount("1234567890");

        ArgumentCaptor<BankAccountDTO> savedAccountRecords = ArgumentCaptor.forClass(BankAccountDTO.class);

        verify(mockAccount,times(1)).save(savedAccountRecords.capture());
        assertEquals(savedAccountRecords.getValue().getbalance(), 0.0, 0.01);
        assertEquals(savedAccountRecords.getValue().getAccountNumber(), "1234567890");
    }
    @Test
    public void testDeposit(){
        BankAccount.openAccount("1234567890");
        BankAccount.deposit(50000.00);
        ArgumentCaptor<BankAccountDTO> savedAccountRecords = ArgumentCaptor.forClass(BankAccountDTO.class);
        verify(mockAccount,times(2)).save(savedAccountRecords.capture());
        assertEquals(savedAccountRecords.getValue().getbalance(), 50000.00, 0.01);
        BankAccount.deposit(10000.00);
        verify(mockAccount,times(3)).save(savedAccountRecords.capture());
        assertEquals(savedAccountRecords.getValue().getbalance(), 60000.00, 0.01);
    }
}
