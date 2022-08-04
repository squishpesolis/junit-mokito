package org.squishpe.junit5app.examples.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.squishpe.junit5app.examples.exceptions.InsufficientMoneyExeption;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    Account account;

    @BeforeEach
    void initMethodTest() {
        this.account =  new Account("Stalyn", new BigDecimal(10));
    }

    @Test
    @DisplayName("testing account name")
    void testCountName() {
          String expectedValue = "Stalyn";
        String realValue = account.getPerson();
        assertNotNull(realValue, () -> "Person of account cant null value"); // in expresion lambda will crear method thar build message error
        assertEquals(expectedValue, realValue, "The name od accoun is not expected");
    }

    @Test
    void testBalanceAccount() {
        assertNotNull(account.getBalance());
        assertEquals(10, account.getBalance().doubleValue());
    }

    @Test
    void testReferenceAccount() {
        account = new Account("Stalyn", new BigDecimal("89.997"));
        Account account2 = new Account("Stalyn", new BigDecimal("89.997"));

        //assertNotEquals(account2, account);
        //tomo con comparación el equal de la clase
        assertEquals(account2, account);
    }

    @Test
    void testDebitAccount() {
        Account account = new Account("Stalyn", new BigDecimal("1000.123456"));
        account.debit(new BigDecimal(100));
        assertNotNull(account.getBalance());
        assertEquals(900, account.getBalance().intValue());
        assertEquals("900.123456", account.getBalance().toPlainString());
    }

    @Test
    void testCreditAccount() {
        Account account = new Account("Stalyn", new BigDecimal("1000.123456"));
        account.credit(new BigDecimal(100));
        assertNotNull(account.getBalance());
        assertEquals(1100, account.getBalance().intValue());
        assertEquals("1100.123456", account.getBalance().toPlainString());
    }

    @Test
    void testInsufficientMoneyException() {
        Account account = new Account("Stalyn", new BigDecimal("1000.123456"));

        Exception exception =  assertThrows(InsufficientMoneyExeption.class, ()-> {
            account.debit(new BigDecimal(2000));
        });

        String messageException  = exception.getMessage();
        String messageExpected = "Insufficient money";

        assertEquals(messageExpected, messageException);

    }

    @Test
    void testRelationAccountBank() {
        Account account = new Account("Stalyn", new BigDecimal("2500"));
        Account account2 = new Account("Ricardo", new BigDecimal("1500.8989"));

        Bank bank = new Bank();
        bank.setName("Pichincha");
        bank.addAccount(account);
        bank.addAccount(account2);

        assertEquals("Pichincha", account.getBank().getName());
    }




}