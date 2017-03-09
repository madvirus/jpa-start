package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team.Player;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class GroupByTest extends JpaTestBase {
    @Test
    public void aggGroupBy() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            // select p.team.id, count(p), avg(p.salary), max(p.salary), min(p.salary)
            // from Player p group by p.team.id
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<Player> p = cq.from(Player.class);
            cq.groupBy(p.get("team").get("id"));
            cq.multiselect(p.get("team").get("id"), cb.count(p), cb.avg(p.get("salary")), cb.max(p.get("salary")), cb.min(p.get("salary")));

            TypedQuery<Object[]> query = em.createQuery(cq);

            List<Object[]> rows = query.getResultList();
            for (Object[] aggValues : rows) {
                String teamId = (String) aggValues[0];
                Long count = (Long) aggValues[1];
                Double avgSal = (Double) aggValues[2];
                int maxSal = (Integer) aggValues[3];
                int minSal = (Integer) aggValues[4];
                System.out.printf("%s, %d, %f, %d, %d\n", teamId, count, avgSal, maxSal, minSal);
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void aggGroupBy2() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            // select t, count(p), avg(p.salary)
            // from Player p left join p.team t group by t
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<Player> p = cq.from(Player.class);
            Join<Player, Team> t = p.join("team", JoinType.LEFT);
            cq.groupBy(t);
            cq.multiselect(t, cb.count(p), cb.avg(p.get("salary")));

            TypedQuery<Object[]> query = em.createQuery(cq);
            List<Object[]> rows = query.getResultList();
            for (Object[] aggValues : rows) {
                Team team = (Team) aggValues[0];
                Long count = (Long) aggValues[1];
                Double avgSal = (Double) aggValues[2];
                System.out.printf("%s, %d, %f\n", (team == null ? "null" : team.getName()), count, avgSal);
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void aggGroupByHaving() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            // select t, count(p), avg(p.salary)
            // from Team t join t.players p
            // group by t having count(p) > 1
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<Team> t = cq.from(Team.class);
            Join<Team, Player> p = t.join("players");
            cq.groupBy(t);
            cq.having(cb.gt(cb.count(p), 1));
            cq.multiselect(t, cb.count(p), cb.avg(p.get("salary")));

            TypedQuery<Object[]> query = em.createQuery(cq);
            List<Object[]> rows = query.getResultList();
            for (Object[] aggValues : rows) {
                Team team = (Team) aggValues[0];
                Long count = (Long) aggValues[1];
                Double avgSal = (Double) aggValues[2];
                System.out.printf("%s, %d, %f\n", (team == null ? "null" : team.getName()), count, avgSal);
            }
        } finally {
            em.close();
        }
    }
}
