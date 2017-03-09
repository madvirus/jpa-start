package jpastart.guide.model;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class UniDirectionOne2OneSharedPKTest extends JpaTestBase {
    private UserBestSight getUserBestSight(String email) {
        EntityManager em = EMF.currentEntityManager();
        try {
            return em.find(UserBestSight.class, email);
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }

    @Test
    public void persist_new_user_and_user_bestsight() throws Exception {
        EntityManager em = EMF.currentEntityManager();
        try {
            em.getTransaction().begin();
            User user = new User("hgd@hgd.co", "홍길동", new Date());
            UserBestSight bestSight = new UserBestSight(user, "율도국", "이상 사회");
            em.persist(user);
            em.persist(bestSight);
            em.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }

        UserBestSight userBestSight = getUserBestSight("hgd@hgd.co");
        assertThat(userBestSight, notNullValue());
        assertThat(userBestSight.getUser(), notNullValue());
        assertThat(userBestSight.getUser().getName(), equalTo("홍길동"));
    }

    @Test
    public void get_user_and_persist_new_user_bestsight() throws Exception {
        EntityManager em = EMF.currentEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, "gildong@dooly.net");
            UserBestSight bestSight = new UserBestSight(user, "순천만", "멋진 갈대");
            em.persist(bestSight);
            em.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }
        UserBestSight userBestSight = getUserBestSight("gildong@dooly.net");
        assertThat(userBestSight, notNullValue());
        assertThat(userBestSight.getTitle(), equalTo("순천만"));
        assertThat(userBestSight.getUser(), notNullValue());
        assertThat(userBestSight.getUser().getEmail(), equalTo("gildong@dooly.net"));
    }

    @Test
    public void find_user_best_sight() throws Exception {
        EntityManager em = EMF.currentEntityManager();
        UserBestSight bestSight = null;
        try {
            bestSight = em.find(UserBestSight.class, "madvirus@madvirus.net");
        } finally {
            EMF.closeCurrentEntityManager();
        }
        assertThat(bestSight.getUser(), notNullValue());
        assertThat(bestSight.getUser().getEmail(), equalTo("madvirus@madvirus.net"));
    }
}
