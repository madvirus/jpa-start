package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SimpleQueryTest extends JpaTestBase {
    @Test
    public void simpleCriteriaQuery() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);
            TypedQuery<User> query = em.createQuery(cq);
            List<User> users = query.getResultList();
            for (User u : users) {
                System.out.println(u.getName());
            }
        } finally {
            em.close();
        }
    }

}
