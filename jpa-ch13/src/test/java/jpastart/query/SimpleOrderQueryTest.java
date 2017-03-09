package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team.Player;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class SimpleOrderQueryTest extends JpaTestBase {
    @Test
    public void orderByAssociationId() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Player> query = em.createQuery("select p from Player p order by p.team.id, p.name", Player.class);
            List<Player> players = query.getResultList();
            for (Player p : players) {
                System.out.println(p.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void orderByAssociationProp() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Player> query = em.createQuery("select p from Player p order by p.team.name, p.name", Player.class);
            List<Player> players = query.getResultList();
            for (Player p : players) {
                System.out.println(p.getName());
            }
        } finally {
            em.close();
        }
    }

}
