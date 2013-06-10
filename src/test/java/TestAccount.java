import BankAccount.BankAccount;
import BankAccountDAO.BankAccountDAO;
import BankAccountDTO.BankAccountDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

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
        BankAccount.openAccount("0123456");
        ArgumentCaptor<BankAccountDTO> savedAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
        verify(mockAccount).save(savedAccount.capture());
        assertEquals(savedAccount.getValue().getbalance(), 0.0,0.01);
        assertEquals(savedAccount.getValue().getAccountNumber(), "0123456");
    }
}
