package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.Hotel;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ExistsAllAnyTest extends JpaTestBase {
    @Test
    public void exists() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Hotel> query = em.createQuery(
                    "select h from Hotel h " +
                            "where not exists (select r from Review r where r.hotel = h) " +
                            "order by h.name", Hotel.class);
            List<Hotel> hotels = query.getResultList();
            for (Hotel hotel : hotels) {
                System.out.println(hotel.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void all() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Team> query = em.createQuery(
                    "select t From Team t " +
                            "where 500 < all (select p.salary from Player p where p.team = t) " +
                            "order by t.name", Team.class);
            List<Team> teams = query.getResultList();
            for (Team team : teams) {
                System.out.println(team.getName());
            }
        } finally {
            em.close();
        }
    }
}
