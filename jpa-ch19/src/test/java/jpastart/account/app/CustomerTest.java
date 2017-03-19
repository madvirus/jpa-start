package jpastart.account.app;

import jpastart.account.model.Customer;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;

public class CustomerTest extends JpaTestBase {

    @Test
    public void optimistic() throws Exception {
        ChangeSecretCodeService secCodeService = new ChangeSecretCodeService();

        Thread tx1 = new Thread(() -> secCodeService.changeSecretCode("gildong", "9876"));
        Thread tx2 = new Thread(() -> secCodeService.changeSecretCode("gildong", "0000"));

        tx1.start();
        tx2.start();

        tx1.join();
        tx2.join();
    }

    @Test
    public void save() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            em.persist(new Customer("heedong", "9878"));

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

    }
}
