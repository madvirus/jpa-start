package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team.Player;
import jpastart.team.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Root;
import java.util.List;

public class N_Plus_One_Collection_Fetch_Join_QueryTest extends JpaTestBase {

    @Test
    public void queryWithFetchJoin() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            // select t from Team t join fetch t.players p
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Team> cq = cb.createQuery(Team.class);
            Root<Team> t = cq.from(Team.class);
            Fetch<Team, Player> fetchJoinP = t.fetch("players");
            cq.select(t);
            TypedQuery<Team> query = em.createQuery(cq);
            List<Team> teams = query.getResultList();

            System.out.println("=================== getResultList");
            for (Team team : teams) {
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
            // select t from Team t join fetch t.players p
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Team> cq = cb.createQuery(Team.class);
            Root<Team> t = cq.from(Team.class);
            Fetch<Team, Player> fetchJoinP = t.fetch("players");
            cq.select(t);

            TypedQuery<Team> query = em.createQuery(cq);
            query.setFirstResult(1);
            query.setMaxResults(1);
            List<Team> teams = query.getResultList();

            System.out.println("=================== getResultList");
            for (Team team : teams) {
                System.out.println(team.getName());
            }
        } finally {
            em.close();
        }
    }
}
