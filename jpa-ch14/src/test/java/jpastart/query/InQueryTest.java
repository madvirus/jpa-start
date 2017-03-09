package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import jpastart.reserve.model.User_;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class InQueryTest extends JpaTestBase {
    @Test
    public void inQuery() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);

            CriteriaBuilder.In<String> nameIn = cb.in(root.get(User_.name));
            nameIn.value("고길동").value("최범균");
            // CriteriaBuilder.In<Object> nameIn = cb.in(root.get("name")).value("1").value(1);

            cq.where(nameIn);

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
