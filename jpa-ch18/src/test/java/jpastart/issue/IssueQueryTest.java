package jpastart.issue;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.member.Member;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class IssueQueryTest extends JpaTestBase {
    @Test
    public void jpql_SuperType() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Issue> query = em.createQuery("select i from Issue i where i.closed = ? order by i.id desc", Issue.class);
            query.setParameter(0, false);
            List<Issue> results = query.getResultList();
        } finally {
            em.close();
        }
    }
}
