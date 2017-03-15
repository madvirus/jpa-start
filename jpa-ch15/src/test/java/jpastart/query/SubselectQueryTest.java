package jpastart.query;

import jpastart.guide.model.BestSightSummary;
import jpastart.guide.model.Sight;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SubselectQueryTest extends JpaTestBase {
    @Test
    public void subselect() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Sight sight = em.find(Sight.class, 1L);
            sight.setName("새 이름");

            TypedQuery<BestSightSummary> query = em.createQuery("select s from BestSightSummary  s where s.id = :id", BestSightSummary.class);
            query.setParameter("id", 1L);
            List<BestSightSummary> summaries = query.getResultList();

            assertThat(summaries.get(0).getName(), equalTo("새 이름"));
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
