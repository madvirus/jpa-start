package jpastart.account.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {
    @Id
    @Column(name = "account_num")
    private String number;
    private int balance;

    public void withdraw(int num) {
        if (balance < num) throw new RuntimeException("잔고부족");
        balance -= num;
    }

    public void deposit(int num) {
        balance += num;
    }

    public int getBalance() {
        return balance;
    }
}
