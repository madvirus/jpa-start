package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team_bi.Player;
import jpastart.team_bi.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class CriteriaQueryTest extends JpaTestBase {
    @Test
    public void simpleSelect() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Team> criteria = cb.createQuery(Team.class);
            Root<Team> t = criteria.from(Team.class);
            criteria.select(t);

            TypedQuery<Team> query = em.createQuery(criteria);
            List<Team> resultList = query.getResultList();
            resultList.forEach(team -> {
                System.out.println(team.getId() + " " + team .getName());
            });
        } finally {
            em.close();
        }
    }

    @Test
    public void whereOr() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Player> criteria = cb.createQuery(Player.class);
            Root<Player> p = criteria.from(Player.class);
            criteria.select(p);
            Predicate cond = cb.greaterThan(p.get("salary"), 3000);
            if (true) {
                Predicate teamCond = cb.equal(p.get("team").get("id"), "T1");
                cond = cb.or(cond, teamCond);
            }

            criteria.where(cond);

            TypedQuery<Player> query = em.createQuery(criteria);
            List<Player> resultList = query.getResultList();
            resultList.forEach(player -> {
                System.out.println(player.getId() + " " + player .getName());
            });
        } finally {
            em.close();
        }
    }

    @Test
    public void joinInFrom() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Team> criteria = cb.createQuery(Team.class);

            Root<Team> t = criteria.from(Team.class);
            Join<Team, Player> p = t.join("players");
            criteria.where(cb.greaterThan(p.get("salary"), 1000));

            criteria.select(t).distinct(true);

            TypedQuery<Team> query = em.createQuery(criteria);
            List<Team> resultList = query.getResultList();
            resultList.forEach(team -> {
                System.out.println(team.getId() + " " + team .getName());
            });
        } finally {
            em.close();
        }
    }

    @Test
    public void leftJoinInFrom() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery criteria = cb.createQuery();

            Root<Team> t = criteria.from(Team.class);
            Join<Team, Player> p = t.join("players", JoinType.LEFT);
            p.on(cb.greaterThan(p.get("salary"), 4000));

            criteria.multiselect(t, p);

            TypedQuery<Object[]> query = em.createQuery(criteria);
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
    public void maxmin_groupby() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery criteria = cb.createQuery();

            Root<Team> t = criteria.from(Team.class);
            Join<Team, Player> p = t.join("players", JoinType.LEFT);

            criteria.groupBy(t);

            criteria.multiselect(t, cb.max(p.get("salary")), cb.min(p.get("salary")));

            TypedQuery<Object[]> query = em.createQuery(criteria);
            List<Object[]> rows = query.getResultList();
            for (Object[] row : rows) {
                Team team = (Team) row[0];
                Integer max = (Integer) row[1];
                Integer min = (Integer) row[2];
                System.out.println(team.getId() + ", " + team.getName() + ", " + max + ", " + min);
            }
        } finally {
            em.close();
        }
    }
}
