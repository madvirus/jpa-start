package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team.Player;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class AggFuncTest extends JpaTestBase {
    @Test
    public void aggAvg() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            // select count(p), avg(p.salary), max(p.salary), min(p.salary) from Player p
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<Player> p = cq.from(Player.class);
            cq.multiselect(cb.count(p), cb.sum(p.get("salary")), cb.avg(p.get("salary")), cb.max(p.get("salary")), cb.min(p.get("salary")));

            TypedQuery<Object[]> query = em.createQuery(cq);
            Object[] aggValues = query.getSingleResult();
            Long count = (Long) aggValues[0];
            long sum = (Long) aggValues[1];
            Double avgSal = (Double) aggValues[2];
            int maxSal = (Integer) aggValues[3];
            int minSal = (Integer) aggValues[4];
            System.out.printf("%d, %d, %f, %d, %d\n", count, sum, avgSal, maxSal, minSal);
        } finally {
            em.close();
        }
    }
}
