package jpastart.account.app;

import jpastart.account.model.Customer;
import jpastart.jpa.EMF;

import javax.persistence.EntityManager;
import java.util.concurrent.ThreadLocalRandom;

public class ChangeSecretCodeService {
    public void changeSecretCode(String id, String newSecCode) {
        EntityManager em = EMF.currentEntityManager();
        try {
            em.getTransaction().begin();
            Customer customer = em.find(Customer.class, id);
            if (customer == null) {
                throw new CustomerNotFoundException();
            }
            sleepRandom(); // 잠금 테스트를 위한 슬립처리
            customer.changeSecretCode(newSecCode);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private void sleepRandom() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(3000) + 500);
        } catch (InterruptedException e) {
        }
    }

}
