package jpastart.query;

import jpastart.common.IdName;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class SelectColumnsTest extends JpaTestBase {
    @Test
    public void selectColumn() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Object[]> query =
                    em.createQuery("select p.id, p.name, p.salary from Player p", Object[].class);
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
            TypedQuery<IdName> query =
                    em.createQuery("select new jpastart.common.IdName(p.id, p.name) from Player p", IdName.class);
            List<IdName> rows = query.getResultList();
            for (IdName row : rows) {
                System.out.printf("%s %s\n", row.getId(), row.getName());
            }
        } finally {
            em.close();
        }

    }
}
