package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team.Player;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CollectionEntityQueryTest extends JpaTestBase {
    @Test
    public void memberOfEntityCollection() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Player player = em.find(Player.class, "P1");

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Team> cq = cb.createQuery(Team.class);
            Root<Team> t = cq.from(Team.class);
            cq.where(cb.isMember(player, t.get("players")));
            cq.select(t);
            cq.orderBy(cb.asc(t.get("name")));

            TypedQuery<Team> query = em.createQuery(cq);

            List<Team> teams = query.getResultList();
            for (Team team : teams) {
                System.out.println(team.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void isEmptyOfEntityCollection() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Team> cq = cb.createQuery(Team.class);
            Root<Team> t = cq.from(Team.class);
            cq.where(cb.isEmpty(t.get("players")));
            cq.select(t);
            cq.orderBy(cb.asc(t.get("name")));

            TypedQuery<Team> query = em.createQuery(cq);

            List<Team> teams = query.getResultList();
            for (Team team : teams) {
                System.out.println(team.getName());
            }
        } finally {
            em.close();
        }
    }
}
