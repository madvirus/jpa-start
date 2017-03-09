package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.Review;
import jpastart.reserve.model.Review_;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class StaticMetaModel_Test extends JpaTestBase {
    Date fromDate = Date.from(ZonedDateTime.of(2016, 6, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant());
    Date toDate = Date.from(ZonedDateTime.of(2016, 6, 5, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant());

    @Test
    public void noStaticModel_matchType() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> r = cq.from(Review.class);
            cq.select(r);
            Predicate betweenPredicate = cb.between(r.get("ratingDate"), fromDate, toDate);
            cq.where(betweenPredicate);
            TypedQuery<Review> query = em.createQuery(cq);
            List<Review> reviews = query.getResultList();
            for (Review review : reviews) {
                System.out.println(review.getRatingDate());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void noStaticModel_typeParameter() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> r = cq.from(Review.class);
            cq.select(r);
            Predicate betweenPredicate = cb.between(r.<Date>get("ratingDate"), fromDate, toDate);
            cq.where(betweenPredicate);
            TypedQuery<Review> query = em.createQuery(cq);
            List<Review> reviews = query.getResultList();
            for (Review review : reviews) {
                System.out.println(review.getRatingDate());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void staticMetaModel() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> r = cq.from(Review.class);
            cq.select(r);
            Predicate betweenPredicate = cb.between(r.get(Review_.ratingDate), fromDate, toDate);
            cq.where(betweenPredicate);
            TypedQuery<Review> query = em.createQuery(cq);
            List<Review> reviews = query.getResultList();
            for (Review review : reviews) {
                System.out.println(review.getRatingDate());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void noStaticModel_noMatchType() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> r = cq.from(Review.class);
            cq.select(r);

            String fromDate = "2016-06-01";
            String toDate = "2016-06-05";
            Predicate betweenPredicate = cb.between(r.get("ratingDate"), fromDate, toDate);
            cq.where(betweenPredicate);

            TypedQuery<Review> query = em.createQuery(cq);

            List<Review> reviews = query.getResultList();
        } catch(Exception ex) {
            // TODO 타입이 맞지 않아 익셉션 발생함
        } finally {
            em.close();
        }
    }

}
