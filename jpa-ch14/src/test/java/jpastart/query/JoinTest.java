package jpastart.query;

import jpastart.guide.model.RecItem;
import jpastart.guide.model.Sight;
import jpastart.guide.model.UserBestSight;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import jpastart.team.Player;
import jpastart.team.Player_;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class JoinTest extends JpaTestBase {
    @Test
    public void implicitJoin() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            // select p from Player p
            // where p.team.name = :teamName order by p.name
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            Root<Player> p = cq.from(Player.class);
            cq.select(p);
            Predicate teamNamePredicate = cb.equal(p.get("team").get("name"), "팀");
            cq.where(teamNamePredicate);
            cq.orderBy(cb.asc(p.get("name")));

            TypedQuery<Player> query = em.createQuery(cq);
            List<Player> players = query.getResultList();

            for (Player player : players) {
                System.out.println(player.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void join() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            // select p from Player p join p.team t
            // where t.name = :teamName order by p.name
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            Root<Player> p = cq.from(Player.class);
            cq.select(p);
            Join<Player, Team> t = p.join("team");
            cq.where(cb.equal(t.get("name"), "팀"));
            cq.orderBy(cb.asc(p.get("name")));

            TypedQuery<Player> query = em.createQuery(cq);
            List<Player> players = query.getResultList();
            for (Player player : players) {
                System.out.println(player.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void joinOn() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            // select p from Player p join p.team t on t = :team
            // order by p.name
            Team team = em.find(Team.class, "T1");

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            Root<Player> p = cq.from(Player.class);
            Join<Player, Team> t = p.join("team");
            t.on(cb.equal(t, team));

            cq.select(p);
            cq.orderBy(cb.asc(p.get("name")));

            TypedQuery<Player> query = em.createQuery(cq);

            List<Player> players = query.getResultList();
            for (Player player : players) {
                System.out.println(player.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void leftJoin() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            // select p, t from Player p left join p.team t
            // order by p.name
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<Player> p = cq.from(Player.class);
            //Join<Player, Team> t = p.join(Player_.team, JoinType.LEFT);
            Join<Player, Team> t = p.join("team", JoinType.LEFT);

            cq.orderBy(cb.asc(p.get("name")));

            cq.multiselect(p, t);

            TypedQuery<Object[]> query = em.createQuery(cq);

            List<Object[]> rows = query.getResultList();
            for (Object[] row : rows) {
                Player player = (Player) row[0];
                Team team = (Team) row[1];
                System.out.println(player.getName() + ", 소속팀 = " + (team == null ? "없음" : team.getName()));
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void whereJoin() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            // select u, s from User u, UserBestSight s where u.email = s.email
            // order by u.name
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<User> u = cq.from(User.class);
            Root<UserBestSight> s = cq.from(UserBestSight.class);
            cq.where(cb.equal(u.get("email"), s.get("email")));
            cq.multiselect(u, s);
            cq.orderBy(cb.asc(u.get("name")));

            TypedQuery<Object[]> query = em.createQuery(cq);
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
            // select distinct s from Sight s left join s.recItems i where i.type = ? order by s.name
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Sight> cq = cb.createQuery(Sight.class);
            Root<Sight> s = cq.from(Sight.class);
            Join<Sight, RecItem> i = s.join("recItems", JoinType.LEFT);
            cq.where(cb.equal(i.get("type"), "ARCH"));
            cq.select(s).distinct(true);

            TypedQuery<Sight> query = em.createQuery(cq);
            List<Sight> sights = query.getResultList();
            for (Sight sight : sights) {
                System.out.println(sight.getName());
            }
        } finally {
            em.close();
        }
    }
}
