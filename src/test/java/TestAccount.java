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
        BankAccountDTO accountDTO= BankAccount.openAccount("1234567890");
        when(mockAccount.getBankAccountDTO(accountDTO.getAccountNumber())).thenReturn(accountDTO);

        BankAccount.deposit(accountDTO.getAccountNumber(),50.00,"first deposit");

        ArgumentCaptor<BankAccountDTO> savedAccountRecords = ArgumentCaptor.forClass(BankAccountDTO.class);
        verify(mockAccount,times(2)).save(savedAccountRecords.capture());
        assertEquals(savedAccountRecords.getValue().getbalance(), 50.00, 0.01);

        //add more amount
        BankAccount.deposit(accountDTO.getAccountNumber(), 10.00, "second Deposit");
        verify(mockAccount,times(3)).save(savedAccountRecords.capture());
        assertEquals(savedAccountRecords.getValue().getbalance(), 60.00, 0.01);

        long timestamp = 10L;
        BankAccount.deposit(accountDTO.getAccountNumber(),timestamp, 10.00, "second Deposit");
        verify(mockAccount,times(4)).save(savedAccountRecords.capture());
        assertEquals(savedAccountRecords.getValue().getTimeStamp(), timestamp, 0);

    }

    @Test
    public void testDepositHasTimeStamp(){
        BankAccountDTO accountDTO= BankAccount.openAccount("1234567890");
        when(mockAccount.getBankAccountDTO(accountDTO.getAccountNumber())).thenReturn(accountDTO);
        ArgumentCaptor<BankAccountDTO> savedAccountRecords = ArgumentCaptor.forClass(BankAccountDTO.class);
        long timestamp = 10L;
        BankAccount.deposit(accountDTO.getAccountNumber(),timestamp, 10.00, "second Deposit");
        verify(mockAccount,times(2)).save(savedAccountRecords.capture());
        assertEquals(savedAccountRecords.getValue().getTimeStamp(), timestamp, 0);
    }

    @Test
    public void testWithDraw(){
        BankAccountDTO accountDTO= BankAccount.openAccount("1234567890");
        when(mockAccount.getBankAccountDTO(accountDTO.getAccountNumber())).thenReturn(accountDTO);

        BankAccount.withDraw(accountDTO.getAccountNumber(),-50.00,"first withdraw");

        ArgumentCaptor<BankAccountDTO> savedAccountRecords = ArgumentCaptor.forClass(BankAccountDTO.class);
        verify(mockAccount,times(2)).save(savedAccountRecords.capture());
        assertEquals(savedAccountRecords.getValue().getbalance(), -50.00, 0.01);

        //add more amount
        BankAccount.withDraw(accountDTO.getAccountNumber(), -10.00, "second withdraw");
        verify(mockAccount,times(3)).save(savedAccountRecords.capture());
        assertEquals(savedAccountRecords.getValue().getbalance(), -60.00, 0.01);
    }

    @Test
    public void testWithDrawHasTimestamp(){
        BankAccountDTO accountDTO= BankAccount.openAccount("1234567890");
        when(mockAccount.getBankAccountDTO(accountDTO.getAccountNumber())).thenReturn(accountDTO);
        ArgumentCaptor<BankAccountDTO> savedAccountRecords = ArgumentCaptor.forClass(BankAccountDTO.class);
        long timestamp = 10L;
        BankAccount.withDraw(accountDTO.getAccountNumber(),timestamp, -10.00, "second Deposit");
        verify(mockAccount,times(2)).save(savedAccountRecords.capture());
        assertEquals(savedAccountRecords.getValue().getTimeStamp(), timestamp, 0);
    }

    @Test
    public void testGetTransactionsOccurred(){

    }

}
