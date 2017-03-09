package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class SimpleQueryTest extends JpaTestBase {
    @Test
    public void simpleTypedQuery() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("select u from User u", User.class);
            List<User> users = query.getResultList();
            for (User u : users) {
                System.out.println(u.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void simpleQuery() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Query query = em.createQuery("select u from User u");
            List<?> users = query.getResultList();
            for (Object u : users) {
                User user = (User)u;
                System.out.println(user.getName());
            }
        } finally {
            em.close();
        }
    }
}
