package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.Hotel;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class DeleteQueryTest extends JpaTestBase {
    @Test
    public void jpqlDelete() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("delete Hotel h where h.name = :name");
            query.setParameter("name", "베스트웨스턴 구로호텔");
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
            assertThat(hotel, nullValue());
        } finally {
            em2.close();
        }
    }

    @Test
    public void criteriaDelete() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaDelete<Hotel> cd = cb.createCriteriaDelete(Hotel.class);
            Root<Hotel> h = cd.from(Hotel.class);
            cd.where(cb.equal(h.get("name"), "베스트웨스턴 구로호텔"));

            Query query = em.createQuery(cd);
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
            assertThat(hotel, nullValue());
        } finally {
            em2.close();
        }
    }
}
