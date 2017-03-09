package jpastart.lifecycle;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import org.junit.Test;

import javax.persistence.EntityManager;

public class RemovedStateTest extends JpaTestBase {
    @Test
    public void cannot_merge_removed_entity() throws Exception {
        User user = null;
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            user = em.find(User.class, "madvirus@madvirus.net");
            em.remove(user);
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
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

}
