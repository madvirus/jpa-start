package jpastart.query;

import jpastart.guide.model.UserBestSight;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AndOrQueryTest extends JpaTestBase {
    @Test
    public void and() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<UserBestSight> cq = cb.createQuery(UserBestSight.class);
            Root<UserBestSight> ubs = cq.from(UserBestSight.class);
            Predicate emailPredicate = cb.equal(ubs.get("email"), "madvirus@madvirus.net");
            Predicate titlePredicate = cb.like(ubs.get("title"), "%랜드");
            Predicate andPredicate = cb.and(emailPredicate, titlePredicate);
            cq.where(andPredicate);

            TypedQuery<UserBestSight> query = em.createQuery(cq);

            List<UserBestSight> sights = query.getResultList();
            for (UserBestSight sight : sights) {
                System.out.println(sight.getEmail() + " - " + sight.getTitle());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void andConditional() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            String email = "madvirus@madvirus.net";
            String keyword = "랜드";

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<UserBestSight> cq = cb.createQuery(UserBestSight.class);
            Root<UserBestSight> ubs = cq.from(UserBestSight.class);

            Predicate p = null;
            if (email != null) p = cb.equal(ubs.get("email"), email);
            if (keyword != null) {
                Predicate titlePre = cb.like(ubs.get("title"), "%" + keyword);
                if (p == null) p = titlePre;
                else p = cb.and(p, titlePre);
            }
            if (p != null) cq.where(p);

            TypedQuery<UserBestSight> query = em.createQuery(cq);

            List<UserBestSight> sights = query.getResultList();
            for (UserBestSight sight : sights) {
                System.out.println(sight.getEmail() + " - " + sight.getTitle());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void andConditional_by_jpql() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            String email = "madvirus@madvirus.net";
            String keyword = null; // "랜드";

            String jpql = "select ubs from UserBestSight ubs";
            String wherePart = "";
            if (email != null) wherePart += "ubs.email = :email";
            if (keyword != null) {
                if (email != null) wherePart += " and "; // and 앞/뒤 공백에 신경써야 함
                wherePart += "ubs.title like :keyword";
            }
            if (!wherePart.isEmpty()) jpql += " where " + wherePart;

            TypedQuery<UserBestSight> query = em.createQuery(jpql, UserBestSight.class);
            if (email != null) query.setParameter("email", email);
            if (keyword != null) query.setParameter("keyword", "%" + keyword);

            List<UserBestSight> sights = query.getResultList();
            for (UserBestSight sight : sights) {
                System.out.println(sight.getEmail() + " - " + sight.getTitle());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void conjunctionConditional() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            String email = null; // "madvirus@madvirus.net";
            String keyword = "랜드";

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<UserBestSight> cq = cb.createQuery(UserBestSight.class);
            Root<UserBestSight> ubs = cq.from(UserBestSight.class);

            Predicate p = cb.conjunction();
            if (email != null) p = cb.equal(ubs.get("email"), email);
            if (keyword != null) p = cb.and(p, cb.like(ubs.get("title"), "%" + keyword));
            cq.where(p);

            TypedQuery<UserBestSight> query = em.createQuery(cq);

            List<UserBestSight> sights = query.getResultList();
            for (UserBestSight sight : sights) {
                System.out.println(sight.getEmail() + " - " + sight.getTitle());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void predicateListConditional() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            String email = "madvirus@madvirus.net";
            String keyword = "랜드";

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<UserBestSight> cq = cb.createQuery(UserBestSight.class);
            Root<UserBestSight> ubs = cq.from(UserBestSight.class);

            List<Predicate> predicates = new ArrayList<>();
            if (email != null) predicates.add(cb.equal(ubs.get("email"), email));
            if (keyword != null) predicates.add(cb.like(ubs.get("title"), "%" + keyword));
            cq.where(predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery<UserBestSight> query = em.createQuery(cq);

            List<UserBestSight> sights = query.getResultList();
            for (UserBestSight sight : sights) {
                System.out.println(sight.getEmail() + " - " + sight.getTitle());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void disjunctionConditional() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            String email = "madvirus@madvirus.net";
            String keyword = "랜드";

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<UserBestSight> cq = cb.createQuery(UserBestSight.class);
            Root<UserBestSight> ubs = cq.from(UserBestSight.class);

            Predicate p = cb.disjunction();

            List<Predicate> predicates = new ArrayList<>();
            if (email != null) predicates.add(cb.equal(ubs.get("email"), email));
            if (keyword != null) predicates.add(cb.like(ubs.get("title"), "%" + keyword));

            p.in(predicates);
            cq.where(
                    cb.or(
                            p,
                            cb.and(predicates.toArray( new Predicate[predicates.size()] ))
                    )
            );

            TypedQuery<UserBestSight> query = em.createQuery(cq);

            List<UserBestSight> sights = query.getResultList();
            for (UserBestSight sight : sights) {
                System.out.println(sight.getEmail() + " - " + sight.getTitle());
            }
        } finally {
            em.close();
        }
    }
}
