package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team.Player;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;

public class SimpleOrderQueryTest extends JpaTestBase {
    @Test
    public void orderByAssociationId() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            Root<Player> p = cq.from(Player.class);
            cq.select(p);
            Order teamIdOrder = cb.asc(p.get("team").get("id"));
            Order nameOrder = cb.desc(p.get("name"));
            cq.orderBy(teamIdOrder, nameOrder);
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
    public void orderByAssociationProp() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            Root<Player> p = cq.from(Player.class);
            cq.select(p);
            Order teamIdOrder = cb.asc(p.get("team").get("name"));
            Order nameOrder = cb.desc(p.get("name"));
            cq.orderBy(teamIdOrder, nameOrder);
            TypedQuery<Player> query = em.createQuery(cq);
            List<Player> players = query.getResultList();
            for (Player player : players) {
                System.out.println(player.getName());
            }
        } finally {
            em.close();
        }
    }

}
