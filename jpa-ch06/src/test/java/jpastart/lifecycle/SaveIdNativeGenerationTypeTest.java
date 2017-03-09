package jpastart.lifecycle;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.Review;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class SaveIdNativeGenerationTypeTest extends JpaTestBase {

    @Test
    public void saveAndGetId() throws Exception {
        Long newId = null;
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Review review = new Review("hotel1", 5, "최고였어요", new Date());

            em.persist(review);
            newId = review.getId();

            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        assertThat(newId, notNullValue());

        em = EMF.createEntityManager();
        try {
            Review review2 = em.find(Review.class, newId);
            assertThat(review2, notNullValue());
        } finally {
            em.close();
        }
    }

    @Test
    public void saveWithoutTx() throws Exception {
        Long newId = null;
        EntityManager em = EMF.createEntityManager();
        try {
            Review review = new Review("hotel1", 5, "최고였어요", new Date());
            em.persist(review);
            newId = review.getId();
        } catch (RuntimeException ex) {
            throw ex;
        } finally {
            em.close();
        }
        assertThat(newId, nullValue());
    }
}
