package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class GroupByTest extends JpaTestBase {
    @Test
    public void aggGroupBy() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                    "select p.team.id, count(p), avg(p.salary), max(p.salary), min(p.salary) " +
                            "from Player p group by p.team.id", Object[].class);
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
            TypedQuery<Object[]> query = em.createQuery(
                    "select t, count(p), avg(p.salary) " +
                            "from Player p left join p.team t group by t", Object[].class);
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
            TypedQuery<Object[]> query = em.createQuery(
                    "select t, count(p), avg(p.salary) " +
                            "from Team t left join t.players p " +
                            "group by t having count(p) > 1", Object[].class);
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
