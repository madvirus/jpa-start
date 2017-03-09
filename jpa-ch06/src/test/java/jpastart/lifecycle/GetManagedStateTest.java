package jpastart.lifecycle;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class GetManagedStateTest extends JpaTestBase {
    @Test
    public void get() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            User user = em.find(User.class, "madvirus@madvirus.net");

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
            assertThat(em.find(User.class, "madvirus@madvirus.net").getName(), equalTo("Choi, Beom Kyun"));
        } finally {
            em.close();
        }
    }

    @Test
    public void getWithoutTx() throws Exception {
        String originName = null;
        EntityManager em = EMF.createEntityManager();
        try {
            User user = em.find(User.class, "madvirus@madvirus.net");
            originName = user.getName();
            user.changeName("Choi, Beom Kyun");
        } finally {
            em.close();
        }

        em = EMF.createEntityManager();
        try {
            assertThat(em.find(User.class, "madvirus@madvirus.net").getName(), equalTo(originName));
        } finally {
            em.close();
        }
    }
}
