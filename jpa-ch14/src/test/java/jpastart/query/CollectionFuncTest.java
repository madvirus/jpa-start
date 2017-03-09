package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CollectionFuncTest extends JpaTestBase {
    @Test
    public void size() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            // select t from Team t where size(t.players) > 1
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Team> cq = cb.createQuery(Team.class);
            Root<Team> t = cq.from(Team.class);

            Predicate sizePred = cb.gt(cb.size(t.get("players")), 1);
            cq.where(sizePred);
            cq.select(t);

            TypedQuery<Team> query = em.createQuery(cq);
            List<Team> rows = query.getResultList();
            for (Team team : rows) {
                System.out.println(team.getName());
            }
        } finally {
            em.close();
        }
    }

}
