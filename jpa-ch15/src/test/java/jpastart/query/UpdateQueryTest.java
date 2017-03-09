package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.Grade;
import jpastart.reserve.model.Hotel;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UpdateQueryTest extends JpaTestBase {
    @Test
    public void jpqlUpdate() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("update Hotel h set h.name = :newName where h.name = :oldName");
            query.setParameter("newName", "베스트웨스턴 구로");
            query.setParameter("oldName", "베스트웨스턴 구로호텔");
            query.executeUpdate();
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        EntityManager em2 = EMF.createEntityManager();
        try {
            Hotel hotel = em2.find(Hotel.class, "H100-01");
            assertThat(hotel.getName(), equalTo("베스트웨스턴 구로"));
        } finally {
            em2.close();
        }
    }

    @Test
    public void criteriaUpdate() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaUpdate<Hotel> cu = cb.createCriteriaUpdate(Hotel.class);
            Root<Hotel> h = cu.from(Hotel.class);
            cu.set(h.get("name"), "베스트웨스턴 구로");
            cu.set(h.get("grade"), Grade.STAR3);
            cu.where(cb.equal(h.get("name"), "베스트웨스턴 구로호텔"));

            Query query = em.createQuery(cu);
            query.executeUpdate();
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        EntityManager em2 = EMF.createEntityManager();
        try {
            Hotel hotel = em2.find(Hotel.class, "H100-01");
            assertThat(hotel.getName(), equalTo("베스트웨스턴 구로"));
        } finally {
            em2.close();
        }
    }
}
