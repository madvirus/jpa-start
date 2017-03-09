package jpastart.jpa;

import jpastart.reserve.model.User;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class FirstLevelCacheTest extends JpaTestBase {
    @Test
    public void cache() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            User user1 = em.find(User.class, "madvirus@madvirus.net");
            User user2 = em.find(User.class, "madvirus@madvirus.net");
            assertThat(user1, sameInstance(user2));
        } finally {
            em.close();
        }
    }
}
