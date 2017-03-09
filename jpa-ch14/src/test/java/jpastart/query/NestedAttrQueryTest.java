package jpastart.query;

import jpastart.guide.model.UserBestSight;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class NestedAttrQueryTest extends JpaTestBase {

    @Test
    public void nestedAttribute() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<UserBestSight> cq = cb.createQuery(UserBestSight.class);
            Root<UserBestSight> root = cq.from(UserBestSight.class);
            cq.select(root);

            Predicate namePredicate = cb.equal(
                    root.get("user").get("name"), "고길동");
            cq.where(namePredicate);

            TypedQuery<UserBestSight> query = em.createQuery(cq);

            List<UserBestSight> sights = query.getResultList();
            for (UserBestSight s : sights) {
                System.out.println(s.getTitle());
            }
        } finally {
            em.close();
        }

    }
}
