package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.Hotel;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class PersistenceContextAndQueryTest extends JpaTestBase {
    @Test
    public void getThenUpdate() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Hotel hotel = em.find(Hotel.class, "H100-01");

            Query query = em.createQuery(
                    "update Hotel h set h.name = :newName " +
                            "where h.id = :id");
            query.setParameter("newName", "베스트웨스턴 구로");
            query.setParameter("id", "H100-01");
            query.executeUpdate();

            assertThat(hotel.getName(), not(equalTo("베스트웨스턴 구로")));
            em.refresh(hotel);
            assertThat(hotel.getName(), equalTo("베스트웨스턴 구로"));

            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void updateThenGet() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Query query = em.createQuery(
                    "update Hotel h set h.name = :newName " +
                            "where h.id = :id");
            query.setParameter("newName", "베스트웨스턴 구로");
            query.setParameter("id", "H100-01");
            query.executeUpdate();

            Hotel hotel = em.find(Hotel.class, "H100-01");
            assertThat(hotel.getName(), equalTo("베스트웨스턴 구로"));

            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

}
