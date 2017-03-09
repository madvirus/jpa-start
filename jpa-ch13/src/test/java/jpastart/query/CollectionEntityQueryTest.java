package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import jpastart.team.Player;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CollectionEntityQueryTest extends JpaTestBase {
    @Test
    public void memberOfEntityCollection() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Player player = em.find(Player.class, "P1");
            TypedQuery<Team> query = em.createQuery("select t from Team t where :player member of t.players order by t.name", Team.class);
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
    public void isEmptyOfEntityCollection() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Team> query = em.createQuery("select t from Team t where t.players is empty order by t.name", Team.class);
            List<Team> teams = query.getResultList();
            for (Team team : teams) {
                System.out.println(team.getName());
            }
        } finally {
            em.close();
        }
    }
}
