package jpastart.query;

import jpastart.common.IdName;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.team.Player;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SelectColumnsTest extends JpaTestBase {
    @Test
    public void selectSingleColumn() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<String> cq = cb.createQuery(String.class);
            Root<Player> p = cq.from(Player.class);
            cq.select(p.get("id"));

            TypedQuery<String> query = em.createQuery(cq);
            List<String> rows = query.getResultList();
            for (String id : rows) {
                System.out.printf("%s\n", id);
            }
        } finally {
            em.close();
        }
    }
    @Test
    public void selectMultipleColumns() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<Player> p = cq.from(Player.class);
            cq.multiselect(p.get("id"), p.get("name"), p.get("salary"));

            TypedQuery<Object[]> query = em.createQuery(cq);
            List<Object[]> rows = query.getResultList();
            for (Object[] row : rows) {
                String id = (String) row[0];
                String name = (String) row[1];
                int salary = (int) row[2];
                System.out.printf("%s %s %d\n", id, name, salary);
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void selectByClass() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<IdName> cq = cb.createQuery(IdName.class);
            Root<Player> p = cq.from(Player.class);
            cq.select(cb.construct(IdName.class, p.get("id"), p.get("name")));

            TypedQuery<IdName> query = em.createQuery(cq);

            List<IdName> rows = query.getResultList();
            for (IdName row : rows) {
                System.out.printf("%s %s\n", row.getId(), row.getName());
            }
        } finally {
            em.close();
        }

    }
}
