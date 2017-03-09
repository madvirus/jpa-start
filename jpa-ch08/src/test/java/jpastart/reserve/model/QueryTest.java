package jpastart.reserve.model;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class QueryTest extends JpaTestBase {

    @Test
    public void query() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Hotel hotel = em.find(Hotel.class, "H100-01");
            TypedQuery<Review> query = em.createQuery(
                    "select r from Review r where r.hotel = :hotel " +
                    "order by r.id desc", Review.class);
            query.setParameter("hotel", hotel);
            query.setFirstResult(3);
            query.setMaxResults(3);
            List<Review> reviews = query.getResultList();
            assertThat(reviews.size(), equalTo(3));
        } finally {
            em.close();
        }
    }

    @Test
    public void criteria() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Hotel hotel = em.find(Hotel.class, "H100-01");

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Review> criteriaQuery = cb.createQuery(Review.class);
            // select root from Review root
            Root<Review> root = criteriaQuery.from(Review.class);
            criteriaQuery.select(root);

            // where root.hotel = hotel엔티티
            Predicate hotelPredicate = cb.equal(root.get("hotel"), hotel);
            criteriaQuery.where(hotelPredicate);

            // order by root.id desc
            criteriaQuery.orderBy(cb.desc(root.get("id")));

            TypedQuery<Review> query = em.createQuery(criteriaQuery);
            List<Review> reviews = query.getResultList();
            assertThat(reviews.size(), equalTo(6));
        } finally {
            em.close();
        }
    }
}
