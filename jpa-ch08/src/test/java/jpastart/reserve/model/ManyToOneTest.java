package jpastart.reserve.model;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class ManyToOneTest extends JpaTestBase {
    private Review find(Long newId) {
        EntityManager em2 = EMF.createEntityManager();
        try {
            Review review = em2.find(Review.class, newId);
            return review;
        } finally {
            em2.close();
        }
    }

    @Test
    public void persist_review() throws Exception {
        Long newId = null;
        EntityManager em = EMF.createEntityManager();

        try {
            em.getTransaction().begin();

            Hotel hotel = em.find(Hotel.class, "H100-01");
            Review review = new Review(hotel, 5, "매우 좋았음", new Date());
            em.persist(review);

            em.getTransaction().commit();

            newId = review.getId();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        assertThat(newId, notNullValue());

        Review review = find(newId);
        assertThat(review.getHotel(), notNullValue());
    }

    @Test
    public void find() throws Exception {
        EntityManager em2 = EMF.createEntityManager();
        try {
            Review review1 = em2.find(Review.class, 1L);
            Review review2 = em2.find(Review.class, 2L);
            assertThat(review1.getHotel(), sameInstance(review2.getHotel()));
        } finally {
            em2.close();
        }

    }
}
