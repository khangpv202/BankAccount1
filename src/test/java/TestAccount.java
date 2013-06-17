import BankAccount.BankAccount;
import BankAccountDAO.BankAccountDAO;
import BankAccountDTO.BankAccountDTO;
import TransactionDAO.TransactionDAO;
import TransactionDTO.TransactionDTO;
import Transaction.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
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
    TransactionDAO mockTransaction = mock(TransactionDAO.class);
    String accountNumber = "123456789";

    @Before
    public void initial(){
        reset(mockAccount);
        BankAccount.setBankAccountDAO(mockAccount);
        Transaction.setTransactionDAO(mockTransaction);
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

        BankAccount.withDraw(accountDTO.getAccountNumber(), -50.00, "first withdraw");

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
        when(mockTransaction.getBankAccountDTO(accountDTO.getAccountNumber())).thenReturn(accountDTO);

        ArgumentCaptor<BankAccountDTO> savedAccountRecords = ArgumentCaptor.forClass(BankAccountDTO.class);
        ArgumentCaptor<TransactionDTO> savedTransactionRecords = ArgumentCaptor.forClass(TransactionDTO.class);

        TransactionDTO transaction = BankAccount.withDraw(accountDTO.getAccountNumber(), -10.00, "second Deposit");
        verify(mockTransaction,times(1)).save(savedTransactionRecords.capture());
        assertEquals(savedTransactionRecords.getValue().getTimeStamp(),transaction.getTimeStamp(),0.01);
    }
    public void createAccountAndDoTransactions(){
        BankAccountDTO initialAccount= BankAccount.openAccount(accountNumber);
        when(mockAccount.getBankAccountDTO(initialAccount.getAccountNumber())).thenReturn(initialAccount);
        TransactionDTO accountAfterFirstTransaction = BankAccount.deposit(initialAccount.getAccountNumber(), 10.00, "first deposit");
        TransactionDTO accountAfterSecondTransaction= BankAccount.deposit(initialAccount.getAccountNumber(), 10.00, "second deposit");
        TransactionDTO accountAfterThirdTransaction = BankAccount.withDraw(initialAccount.getAccountNumber(),10.00, "first withdraw");

    }
    @Test
    public void testGetTransactionsOccurred(){

        List<TransactionDTO> accountDTOs = new ArrayList<TransactionDTO>();
        createAccountAndDoTransactions();
        ArgumentCaptor<TransactionDTO> savedTransactiontRecords = ArgumentCaptor.forClass(TransactionDTO.class);
        accountDTOs = savedTransactiontRecords.getAllValues();
        when(mockTransaction.getTransactionsOccurred(accountNumber)).thenReturn(accountDTOs);
        List<TransactionDTO> transactionDTOList= BankAccount.getTransactionsOccurred(accountNumber);
        verify(mockTransaction,times(3)).save(savedTransactiontRecords.capture());
        assertEquals(transactionDTOList,accountDTOs);

    }
    @Test
    public void testTransactionInIntervalTime(){
        long startTime = 1371094606541L;
        long stopTime = 1371094613741L;
        List<TransactionDTO> accountDTOs = new ArrayList<TransactionDTO>();
        ArgumentCaptor<TransactionDTO> savedTransactiontRecords = ArgumentCaptor.forClass(TransactionDTO.class);
        createAccountAndDoTransactions();
        verify(mockTransaction,times(3)).save(savedTransactiontRecords.capture());
        accountDTOs = savedTransactiontRecords.getAllValues();
        when(mockTransaction.getTransactionsOccurred(accountNumber,startTime,stopTime)).thenReturn(accountDTOs);
        List<TransactionDTO> transactionDTOList = BankAccount.getTransactionsOccurred(accountNumber, startTime, stopTime);
        assertEquals(transactionDTOList,accountDTOs);


    }

    @Test
    public void testTransactionNewest(){
        List<TransactionDTO> accountDTOs = new ArrayList<TransactionDTO>();
        ArgumentCaptor<TransactionDTO> savedTransactiontRecords = ArgumentCaptor.forClass(TransactionDTO.class);
        createAccountAndDoTransactions();
        verify(mockTransaction,times(3)).save(savedTransactiontRecords.capture());
        accountDTOs = savedTransactiontRecords.getAllValues();
        when(mockTransaction.getTransactionsOccurred(accountNumber,3)).thenReturn(accountDTOs);
        List<TransactionDTO> transactionDTOList = BankAccount.getTransactionsOccurred(accountNumber,3);
        assertEquals(transactionDTOList,accountDTOs);

    }

}
