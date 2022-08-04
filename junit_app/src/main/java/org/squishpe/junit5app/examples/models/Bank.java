package org.squishpe.junit5app.examples.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Bank {

    private String name;

    private List<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public Bank(String name) {
        this.name = name;
    }

    







    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {

        accounts.add(account);
        account.setBank(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void transfer(Account origin, Account destinity, BigDecimal money) {
        origin.debit(money);
        destinity.credit(money);

    }
    
}
