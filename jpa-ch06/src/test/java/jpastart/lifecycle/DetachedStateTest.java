package jpastart.lifecycle;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class DetachedStateTest extends JpaTestBase {
    @Test
    public void detached_entity_has_no_dirth_checking() throws Exception {
        User user = null;
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            user = em.find(User.class, "madvirus@madvirus.net");

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        user.changeName("Choi, Beom Kyun");

        em = EMF.createEntityManager();
        try {
            assertThat(em.find(User.class, "madvirus@madvirus.net").getName(), not("Choi, Beom Kyun"));
        } finally {
            em.close();
        }
    }

    @Test
    public void detach_entity_force() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            User user = em.find(User.class, "madvirus@madvirus.net");
            em.detach(user);

            user.changeName("Choi, Beom Kyun");

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        em = EMF.createEntityManager();
        try {
            assertThat(em.find(User.class, "madvirus@madvirus.net").getName(), not("Choi, Beom Kyun"));
        } finally {
            em.close();
        }
    }

    @Test
    public void merge_detached_entity() throws Exception {
        EntityManager em = EMF.createEntityManager();
        User user = null;
        try {
            user = em.find(User.class, "madvirus@madvirus.net");
        } finally {
            em.close();
        }

        user.changeName("Choi, Beom Kyun");

        em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        em = EMF.createEntityManager();
        try {
            assertThat(em.find(User.class, "madvirus@madvirus.net").getName(), equalTo("Choi, Beom Kyun"));
        } finally {
            em.close();
        }

    }
}
