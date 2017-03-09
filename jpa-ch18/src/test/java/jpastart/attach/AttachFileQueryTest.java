package jpastart.attach;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AttachFileQueryTest extends JpaTestBase {

    @Test
    public void jpql_SuperType() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<AttachFile> query = em.createQuery("select af from AttachFile af order by af.uploadDate desc", AttachFile.class);
            query.setFirstResult(0);
            query.setMaxResults(3);
            List<AttachFile> results = query.getResultList();
        } finally {
            em.close();
        }
    }

    @Test
    public void criteria_SuperType() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<AttachFile> cq = cb.createQuery(AttachFile.class);
            Root<AttachFile> root = cq.from(AttachFile.class);
            cq.select(root);
            cq.orderBy(cb.desc(root.get("uploadDate")));

            TypedQuery<AttachFile> query = em.createQuery(cq);
            query.setFirstResult(0);
            query.setMaxResults(3);
            List<AttachFile> results = query.getResultList();
        } finally {
            em.close();
        }
    }
}
