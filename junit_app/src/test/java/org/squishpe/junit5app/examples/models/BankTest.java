package org.squishpe.junit5app.examples.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    @Test
    void testTranferMoneyBetweenAccount() {
        Account account = new Account("Stalyn", new BigDecimal("2500"));
        Account account2 = new Account("Ricardo", new BigDecimal("1500.8989"));

        Bank pichicnha = new Bank("Pichicnha");
        pichicnha.transfer(account2, account, new BigDecimal(500));

        assertEquals("1000.8989", account2.getBalance().toPlainString());
        assertEquals("3000", account.getBalance().toPlainString());
    }

    @Test
    void testRelationBankAccounts() {
        Account account = new Account("Stalyn", new BigDecimal("2500"));
        Account account2 = new Account("Ricardo", new BigDecimal("1500.8989"));

        Bank bank = new Bank();
        bank.setName("Pichincha");
        bank.addAccount(account);
        bank.addAccount(account2);

        bank.transfer(account2, account, new BigDecimal(500));

        assertEquals(2, bank.getAccounts().size());
    }

    @Test
    void testBankHasAccountWithNameStalyn() {
        Account account = new Account("Stalyn", new BigDecimal("2500"));
        Account account2 = new Account("Ricardo", new BigDecimal("1500.8989"));

        Bank bank = new Bank();
        bank.setName("Pichincha");
        bank.addAccount(account);
        bank.addAccount(account2);

        bank.transfer(account2, account, new BigDecimal(500));

        assertEquals("Stalyn", bank.getAccounts().stream()
                .filter(a -> a .getPerson().equals("Stalyn"))
                .findFirst().get().getPerson()
        );
    }


}