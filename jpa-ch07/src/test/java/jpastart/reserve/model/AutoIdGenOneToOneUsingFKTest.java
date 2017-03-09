package jpastart.reserve.model;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Date;

public class AutoIdGenOneToOneUsingFKTest extends JpaTestBase {

    @Test
    public void associate_before_generating_id() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Review review = new Review("H001", 5, "최고에요", new Date());
            RealUserLog realUserLog = new RealUserLog(review, new Date());
            em.persist(review);
            em.persist(realUserLog);

            em.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }
}
