package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import jpastart.reserve.model.User_;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class WhereQueryTest extends JpaTestBase {
    @Test
    public void simpleWhereQuery() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);

            Predicate namePredicate = cb.equal(root.get("name"), "고길동");
            cq.where(namePredicate);

            TypedQuery<User> query = em.createQuery(cq);

            List<User> users = query.getResultList();
            for (User u : users) {
                System.out.println(u.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void simpleWhereQuery_staticMetaModel() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);

            Predicate namePredicate = cb.equal(root.get(User_.name), "고길동");
            cq.where(namePredicate);

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
