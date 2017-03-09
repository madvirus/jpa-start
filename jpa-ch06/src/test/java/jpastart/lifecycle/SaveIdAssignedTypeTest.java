package jpastart.lifecycle;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class SaveIdAssignedTypeTest extends JpaTestBase {
    @Test
    public void save() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            User user = new User("user@user.com", "user", new Date());
            em.persist(user);

            user.changeName("newUser");

            User user2 = em.find(User.class, "user@user.com");
            assertThat(user, sameInstance(user2));

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        em = EMF.createEntityManager();
        try {
            assertThat(em.find(User.class, "user@user.com"), notNullValue());
        } finally {
            em.close();
        }
    }

    @Test
    public void saveWithoutTx() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            User user = new User("user@user.com", "user", new Date());
            em.persist(user);

            User user2 = em.find(User.class, "user@user.com");
            assertThat(user, sameInstance(user2));
        } finally {
            em.close();
        }

        em = EMF.createEntityManager();
        try {
            assertThat(em.find(User.class, "user@user.com"), nullValue());
        } finally {
            em.close();
        }
    }
}
