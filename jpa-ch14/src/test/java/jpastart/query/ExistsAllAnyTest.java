package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.Hotel;
import jpastart.reserve.model.Review;
import jpastart.team.Player;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class ExistsAllAnyTest extends JpaTestBase {
    @Test
    public void exists() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Hotel> cq = cb.createQuery(Hotel.class);
            Root<Hotel> h = cq.from(Hotel.class);
            cq.select(h);

            Subquery<Review> subquery = cq.subquery(Review.class);
            Root<Review> r = subquery.from(Review.class);
            subquery.select(r).where(cb.equal(r.get("hotel"), h));

            Predicate existsPredicate = cb.exists(subquery);

            cq.where(cb.not(existsPredicate));

            TypedQuery<Hotel> query = em.createQuery(cq);

            // select h from Hotel h
            // where not exists (select r from Review r where r.hotel = h)
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
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Team> cq = cb.createQuery(Team.class);
            Root<Team> t = cq.from(Team.class);
            cq.select(t);

            Subquery<Integer> subquery = cq.subquery(Integer.class);
            Root<Player> p = subquery.from(Player.class);
            subquery.select(p.get("salary")).where(cb.equal(p.get("team"), t));

            Expression<Integer> allExp = cb.all(subquery);

            cq.where(cb.lt(cb.literal(500), allExp));

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
