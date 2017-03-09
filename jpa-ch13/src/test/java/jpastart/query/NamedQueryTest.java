package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.Hotel;
import jpastart.team.Player;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class NamedQueryTest extends JpaTestBase {
    @Test
    public void useXmlNamedQuery1() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Hotel> query = em.createNamedQuery("Hotel.noReview", Hotel.class);
            List<Hotel> reviews = query.getResultList();
            for (Hotel hotel : reviews) {
                System.out.println(hotel.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void useXmlNamedQuery2() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Player player = em.find(Player.class, "P1");
            TypedQuery<Team> query = em.createNamedQuery("Team.hasPlayer", Team.class);
            query.setParameter("player", player);
            List<Team> teams = query.getResultList();
            for (Team team : teams) {
                System.out.println(team.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void useJavaNamedQuery1() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Hotel> query = em.createNamedQuery("Hotel.all", Hotel.class);
            List<Hotel> reviews = query.getResultList();
            for (Hotel hotel : reviews) {
                System.out.println(hotel.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void useJavaNamedQuery2() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Hotel> query = em.createNamedQuery("Hotel.findById", Hotel.class);
            query.setParameter("id", "H-001");
            List<Hotel> reviews = query.getResultList();
            for (Hotel hotel : reviews) {
                System.out.println(hotel.getName());
            }
        } finally {
            em.close();
        }
    }
}
