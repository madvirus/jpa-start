package jpastart.member;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class MemberQueryTest extends JpaTestBase {
    @Test
    public void jpql_SuperType() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Member> query = em.createQuery("select m from Member m where m.name like :name order by m.name", Member.class);
            query.setParameter("name", "%최%");
            query.setFirstResult(0);
            query.setMaxResults(2);
            List<Member> results = query.getResultList();
        } finally {
            em.close();
        }
    }
}
