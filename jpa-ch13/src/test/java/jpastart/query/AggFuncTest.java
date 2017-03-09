package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class AggFuncTest extends JpaTestBase {
    @Test
    public void aggAvg() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery("select count(p), avg(p.salary), max(p.salary), min(p.salary) from Player p", Object[].class);
            Object[] aggValues = query.getSingleResult();
            Long count = (Long) aggValues[0];
            Double avgSal = (Double) aggValues[1];
            int maxSal = (Integer) aggValues[2];
            int minSal = (Integer) aggValues[3];
            System.out.printf("%d, %f, %d, %d\n", count, avgSal, maxSal, minSal);
        } finally {
            em.close();
        }
    }
}
