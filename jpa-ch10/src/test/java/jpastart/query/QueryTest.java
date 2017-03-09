package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team.Player;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class QueryTest extends JpaTestBase {
    @Test
    public void joinInFrom() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Team> query = em.createQuery("select t from Team t join t.players p where p.salary > 1000", Team.class);
            List<Team> result = query.getResultList();
            result.forEach(t -> System.out.println(t.getId() + " " + t.getName()));
        } finally {
            em.close();
        }
    }

    @Test
    public void leftJoinInFrom() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Query query = em.createQuery("select t, p from Team t left join t.players p on p.salary > 4000");
            List<Object[]> result = query.getResultList();
            result.forEach(row -> {
                Team team = (Team)row[0];
                Player player = (Player)row[1];
                System.out.println(team.getId() + " " + team.getName() + ", " +
                        (player == null ? "null" : player.getId() + "(" + player.getName() + ") " + player.getSalary()));
            });
        } finally {
            em.close();
        }
    }

    @Test
    public void maxmin() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            jpastart.team_bi.Team team = em.find(jpastart.team_bi.Team.class, "T2");
            Query query = em.createQuery("select max(p.salary), min(p.salary) from Player2 p where p.team = :team");
            query.setParameter("team", team);
            Object[] row = (Object[])query.getSingleResult();
            System.out.println(row[0]);
            System.out.println(row[1]);
        } finally {
            em.close();
        }
    }

    @Test
    public void maxmin_groupby() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Query query = em.createQuery("select t, max(p.salary), min(p.salary) from Team t left join t.players p group by t");
            List<Object[]> rows = query.getResultList();
            for (Object[] row : rows) {
                Team t = (Team) row[0];
                Integer max = (Integer) row[1];
                Integer min = (Integer) row[2];
                System.out.println(t.getId() + ", " + t.getName() + ", " + max + ", " + min);
            }
        } finally {
            em.close();
        }
    }

}
