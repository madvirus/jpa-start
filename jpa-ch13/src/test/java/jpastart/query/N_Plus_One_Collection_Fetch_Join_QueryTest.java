package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class N_Plus_One_Collection_Fetch_Join_QueryTest extends JpaTestBase {

    @Test
    public void queryWithFetchJoin() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Team> query = em.createQuery(
                    "select t from Team t join fetch t.players p", Team.class);
            List<Team> teams = query.getResultList();

            System.out.println("=================== getResultList");
            for (Team  team : teams) {
                System.out.println(team.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void queryWithFetchJoinAndMaxResults() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Team> query = em.createQuery(
                    "select t from Team t join fetch t.players p", Team.class);
            query.setFirstResult(1);
            query.setMaxResults(1);
            List<Team> teams = query.getResultList();

            System.out.println("=================== getResultList");
            for (Team  team : teams) {
                System.out.println(team.getName());
            }
        } finally {
            em.close();
        }
    }
}
