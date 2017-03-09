package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.Review;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PagingTest extends JpaTestBase {
    @Test
    public void paging() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Review> query = em
                    .createQuery("select r from Review r " +
                            "where r.hotel.id = :hotelId order by r.id desc", Review.class);
            query.setParameter("hotelId", "H-001");
            query.setFirstResult(10);
            query.setMaxResults(5);
            List<Review> reviews = query.getResultList();
            for (Review r : reviews) {
                System.out.println(r.getRate() + " : " + r.getComment());
            }
        } finally {
            em.close();
        }

    }
}
