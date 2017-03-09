package jpastart.authlog;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.net.InetAddress;
import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AuthLogTest extends JpaTestBase {
    @Test
    public void saveAndFind() throws Exception {
        Long savedId = null;
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            AuthLog log = new AuthLog("bk", InetAddress.getByName("192.168.0.1"), new Date(), true);
            em.persist(log);

            em.getTransaction().commit();

            savedId = log.getId();

        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        EntityManager em2 = EMF.createEntityManager();
        try {
            AuthLog log = em2.find(AuthLog.class, savedId);
            assertThat(log, notNullValue());
        } finally {
            em2.close();
        }
    }
}
