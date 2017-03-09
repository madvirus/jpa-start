package jpastart.query;

import jpastart.guide.model.Itinerary;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CollectionFuncTest extends JpaTestBase {
    @Test
    public void size() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Team> query = em.createQuery(
                    "select t from Team t where size(t.players) > 1", Team.class);
            List<Team> rows = query.getResultList();
            for (Team team : rows) {
                System.out.println(team.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void index() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Itinerary> query = em.createQuery(
                    "select i from Itinerary i join i.sites s " +
                            "where s = ? and index(s) = 0", Itinerary.class);
            query.setParameter(0, "부소산성");
            List<Itinerary> rows = query.getResultList();
            for (Itinerary itinerary : rows) {
                System.out.println(itinerary.getName());
            }
        } finally {
            em.close();
        }
    }
}
