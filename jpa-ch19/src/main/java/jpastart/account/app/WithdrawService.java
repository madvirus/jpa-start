package jpastart.account.app;

import jpastart.account.model.Account;
import jpastart.jpa.EMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.HashMap;
import java.util.Map;

public class WithdrawService {
    private Logger logger = LoggerFactory.getLogger(DepositService.class);

    public int withdraw(String accountNum, int value) {
        EntityManager em = EMF.currentEntityManager();
        try {
            em.getTransaction().begin();
            Map<String, Object> hints = new HashMap<>();
            hints.put("javax.persistence.lock.timeout", 1000);
            Account account = em.find(Account.class, accountNum,
                    LockModeType.PESSIMISTIC_WRITE, hints);
            logger.info("acquire lock");
            if (account == null) {
                throw new AccountNotFoundException();
            }
            sleep(200); // 잠금 테스트를 위한 슬립처리
            account.withdraw(value);
            em.getTransaction().commit();
            return account.getBalance();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private void sleep(int sleeptime) {
        try {
            Thread.sleep(sleeptime);
        } catch (InterruptedException e) {
        }
    }

}
