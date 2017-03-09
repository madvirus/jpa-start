package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.Grade;
import jpastart.reserve.model.Hotel;
import jpastart.reserve.model.MembershipCard;
import jpastart.reserve.model.User;
import jpastart.team.Player;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class WhereTest extends JpaTestBase {
    @Test
    public void positionParameter() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Player> query = em.createQuery("select p from Player p where p.team.id = ? and p.salary > ?", Player.class);
            query.setParameter(0, "T1");
            query.setParameter(1, 1000);
            List<Player> players = query.getResultList();
            for (Player p : players) {
                System.out.println(p.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void namedParameter() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Player> query = em.createQuery("select p from Player p where p.team.id = :teamId and p.salary > :minSalary", Player.class);
            query.setParameter("teamId", "T1");
            query.setParameter("minSalary", 1000);
            List<Player> players = query.getResultList();
            for (Player p : players) {
                System.out.println(p.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void enttityParameter() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Team team = em.find(Team.class, "T1");
            TypedQuery<Player> query = em.createQuery("select p from Player p where p.team = :team and p.salary > :minSalary", Player.class);
            query.setParameter("team", team);
            query.setParameter("minSalary", 1000);
            List<Player> players = query.getResultList();
            for (Player p : players) {
                System.out.println(p.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void temporal() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("select u from User u where u.createDate < ? order by u.email", User.class);
            query.setParameter(0, new Date(), TemporalType.TIMESTAMP);
            List<User> users = query.getResultList();
            for (User u : users) {
                System.out.println(u.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void in() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Hotel> query = em.createQuery("select h from Hotel h where h.grade in ('STAR4', 'STAR7')", Hotel.class);
            List<Hotel> hotels = query.getResultList();
            for (Hotel h : hotels) {
                System.out.println(h.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void in2() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Hotel> query = em.createQuery("select h from Hotel h where h.grade in (?, ?)", Hotel.class);
            query.setParameter(0, Grade.STAR4);
            query.setParameter(1, Grade.STAR1);
            List<Hotel> hotels = query.getResultList();
            for (Hotel h : hotels) {
                System.out.println(h.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void isNull() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<MembershipCard> query = em.createQuery("select mc from MembershipCard mc where mc.owner is not null", MembershipCard.class);
            List<MembershipCard> cards = query.getResultList();
            for (MembershipCard card : cards) {
                System.out.println(card.getNumber());
            }
        } finally {
            em.close();
        }
    }
}
