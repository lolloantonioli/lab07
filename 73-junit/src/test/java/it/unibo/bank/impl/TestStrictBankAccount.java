package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static it.unibo.bank.impl.StrictBankAccount.TRANSACTION_FEE;
import static it.unibo.bank.impl.SimpleBankAccount.MANAGEMENT_FEE;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    public static final int FIRST_BALANCE = 10000;
    public static final int FIRST_DEPOSIT = 50;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, FIRST_BALANCE);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(FIRST_BALANCE, bankAccount.getBalance());
        assertEquals(mRossi, bankAccount.getAccountHolder());
        assertEquals(0, bankAccount.getTransactionsCount());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        bankAccount.deposit(mRossi.getUserID(), FIRST_DEPOSIT);
        assertEquals(1, bankAccount.getTransactionsCount());
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertEquals(FIRST_BALANCE + FIRST_DEPOSIT - MANAGEMENT_FEE - TRANSACTION_FEE * 1, bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), -FIRST_DEPOSIT);
        } catch (Exception e) {
            assertNotNull(e);
            assertFalse(e.getMessage().isEmpty());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), FIRST_BALANCE * 2);
        } catch (Exception e) {
            assertNotNull(e);
            assertFalse(e.getMessage().isEmpty());
        }
    }
}
