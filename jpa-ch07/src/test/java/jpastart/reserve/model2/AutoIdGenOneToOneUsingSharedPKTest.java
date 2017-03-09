package jpastart.reserve.model2;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.RealUserLog;
import jpastart.reserve.model.Review;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AutoIdGenOneToOneUsingSharedPKTest extends JpaTestBase {

    @Test
    public void associate_before_generating_id() throws Exception {
        Long newReviewId = null;
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Review2 review = new Review2("H001", 5, "최고에요", new Date());
            em.persist(review);
            RealUserLog2 realUserLog = new RealUserLog2(review, new Date());
            em.persist(realUserLog);
            em.getTransaction().commit();

            newReviewId = review.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }

        RealUserLog2 realUserLog2;
        EntityManager em2 = EMF.currentEntityManager();
        try {
            realUserLog2 = em2.find(RealUserLog2.class, newReviewId);
        } finally {
            EMF.closeCurrentEntityManager();
        }
        assertThat(realUserLog2, notNullValue());
    }
}
