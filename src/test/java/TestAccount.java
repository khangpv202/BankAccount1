import BankAccount.BankAccount;
import BankAccountDAO.BankAccountDAO;
import BankAccountDTO.BankAccountDTO;
import TransactionDAO.TransactionDAO;
import TransactionDTO.TransactionDTO;
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

    @Before
    public void initial(){
        reset(mockAccount);
        BankAccount.setBankAccountDAO(mockAccount);
        BankAccount.setTransactionDAO(mockTransaction);
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

    @Test
    public void testGetTransactionsOccurred(){

        BankAccountDTO initialAccount= BankAccount.openAccount("1234567890");
        List<TransactionDTO> accountDTOs = new ArrayList<TransactionDTO>();

        when(mockAccount.getBankAccountDTO(initialAccount.getAccountNumber())).thenReturn(initialAccount);
        when(mockTransaction.getBankAccountDTO(initialAccount.getAccountNumber())).thenReturn(initialAccount);

        ArgumentCaptor<TransactionDTO> savedTransactiontRecords = ArgumentCaptor.forClass(TransactionDTO.class);

        TransactionDTO accountAfterFirstTransaction = BankAccount.deposit(initialAccount.getAccountNumber(), 10.00, "first deposit");
        TransactionDTO accountAfterSecondTransaction= BankAccount.deposit(initialAccount.getAccountNumber(), 10.00, "second deposit");
        TransactionDTO accountAfterThirdTransaction = BankAccount.withDraw(initialAccount.getAccountNumber(),10.00, "first withdraw");


        //add transaction into mock()
        accountDTOs.add(accountAfterFirstTransaction);
        accountDTOs.add(accountAfterSecondTransaction);
        accountDTOs.add(accountAfterThirdTransaction);

        when(mockTransaction.getTransactionsOccurred(initialAccount.getAccountNumber())).thenReturn(accountDTOs);
        List<TransactionDTO> transactionDTOList= BankAccount.getTransactionsOccurred(initialAccount.getAccountNumber());
        verify(mockTransaction,times(3)).save(savedTransactiontRecords.capture());
        for(int i = 0;i < transactionDTOList.size(); i++){
            assertEquals(accountDTOs.get(i).getAmount(),transactionDTOList.get(i).getAmount(),0.01);
        }
    }
    @Test
    public void testTransactionInIntervalTime(){
        long startTime = 1371094606541L;
        BankAccountDTO initialAccount= BankAccount.openAccount("1234567890");
        List<TransactionDTO> accountDTOs = new ArrayList<TransactionDTO>();

        when(mockAccount.getBankAccountDTO(initialAccount.getAccountNumber())).thenReturn(initialAccount);

        ArgumentCaptor<TransactionDTO> savedTransactiontRecords = ArgumentCaptor.forClass(TransactionDTO.class);

        TransactionDTO accountAfterFirstTransaction = BankAccount.deposit(initialAccount.getAccountNumber(), 10.00, "first deposit");
        TransactionDTO accountAfterSecondTransaction= BankAccount.deposit(initialAccount.getAccountNumber(), 10.00, "second deposit");
        TransactionDTO accountAfterThirdTransaction = BankAccount.withDraw(initialAccount.getAccountNumber(),10.00, "first withdraw");

        //add transaction into mock()
        accountDTOs.add(accountAfterFirstTransaction);
        accountDTOs.add(accountAfterSecondTransaction);
        accountDTOs.add(accountAfterThirdTransaction);
        long stopTime = 1371094613741L;
        when(mockTransaction.getTransactionsOccurred(initialAccount.getAccountNumber(),startTime,stopTime)).thenReturn(accountDTOs);
        List<TransactionDTO> transactionDTOList = BankAccount.getTransactionsOccurred(initialAccount.getAccountNumber(), startTime, stopTime);
        verify(mockTransaction,times(1)).getTransactionsOccurred();
        assertEquals(savedTransactiontRecords.getValue(),startTime);


    }

}
