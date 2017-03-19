package jpastart.account.app;

import jpastart.jpa.JpaTestBase;
import org.junit.Test;

public class AccountTest extends JpaTestBase {

    @Test
    public void pessimistic() throws Exception {
        DepositService depositService = new DepositService();
        WithdrawService withdrawService = new WithdrawService();

        Thread tx1 = new Thread(() -> depositService.deposit("02-008", 500));
        Thread tx2 = new Thread(() -> withdrawService.withdraw("02-008", 300));

        tx1.start();
        tx2.start();

        tx1.join();
        tx2.join();
    }
}
