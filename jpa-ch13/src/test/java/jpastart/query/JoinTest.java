package jpastart.query;

import jpastart.guide.model.Sight;
import jpastart.guide.model.UserBestSight;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import jpastart.team.Player;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class JoinTest extends JpaTestBase {
    @Test
    public void implicitJoin() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Player> query = em.createQuery(
                    "select p from Player p " +
                            "where p.team.name = :teamName " +
                            "order by p.name", Player.class);
            query.setParameter("teamName", "팀1");
            List<Player> players = query.getResultList();
            for (Player p : players) {
                System.out.println(p.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void join() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Player> query = em.createQuery(
                    "select p from Player p join p.team t " +
                            "where t.name = :teamName " +
                            "order by p.name", Player.class);
            query.setParameter("teamName", "팀1");
            List<Player> players = query.getResultList();
            for (Player p : players) {
                System.out.println(p.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void joinOn() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Team team = em.find(Team.class, "T1");
            TypedQuery<Player> query = em.createQuery(
                    "select p from Player p join p.team t on t = :team " +
                            "order by p.name", Player.class);
            query.setParameter("team", team);
            List<Player> players = query.getResultList();
            for (Player p : players) {
                System.out.println(p.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void leftJoin() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                    "select p, t from Player p left join p.team t " +
                            "order by p.name", Object[].class);
            List<Object[]> rows = query.getResultList();
            for (Object[] row : rows) {
                Player p = (Player) row[0];
                Team t = (Team) row[1];
                System.out.println(p.getName() + ", 소속팀 = " + (t == null ? "없음" : t.getName()));
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void whereJoin() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                    "select u, s from User u, UserBestSight s where u.email = s.email " +
                            "order by u.name", Object[].class);
            List<Object[]> rows = query.getResultList();
            for (Object[] row : rows) {
                User user = (User) row[0];
                UserBestSight sight = (UserBestSight) row[1];
                System.out.println(user.getName() + ": " + sight.getTitle());
            }
        } finally {
            em.close();
        }
    }


    @Test
    public void leftJoinEmbeddable() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Sight> query = em.createQuery(
                    "select distinct s from Sight s left join s.recItems i where i.type = ?" +
                            "order by s.name", Sight.class);
            query.setParameter(0, "ARCH");
            List<Sight> sights = query.getResultList();
            for (Sight sight : sights) {
                System.out.println(sight.getName());
            }
        } finally {
            em.close();
        }
    }
}
