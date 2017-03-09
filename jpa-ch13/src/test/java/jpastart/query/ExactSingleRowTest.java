package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team.Player;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

public class ExactSingleRowTest extends JpaTestBase {
    @Test
    public void singleResult() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Long> query =
                    em.createQuery("select count(p) from Player p", Long.class);
            Long count = query.getSingleResult();
            System.out.println(count);
        } finally {
            em.close();
        }
    }

    @Test(expected = NonUniqueResultException.class)
    public void notSingleResult() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Player> query =
                    em.createQuery("select p from Player p", Player.class);
            Player p = query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
