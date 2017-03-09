package jpastart.reserve.model;

import jpastart.guide.model.Sight;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SetSortOrderTest extends JpaTestBase {

    private <T> boolean isSorted(Set<T> values) {
        Comparator<T> comparator = (Comparator<T>) Comparator.naturalOrder();
        return this.isSorted(values, comparator);
    }

    private <T> boolean isSorted(Set<T> values, Comparator<T> comparator) {
        List<T> vals = new ArrayList<>(values);
        List<T> vals2 = new ArrayList<>(values);
        vals.sort(comparator);
        for (int i = 0 ; i < vals.size() ; i++) {
            if (!vals2.get(i).equals(vals.get(i))) return false;
        }
        return true;
    }

    @Test
    public void sort() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            User2 user2 = em.find(User2.class, "madvirus@madvirus.net");
            assertTrue(isSorted(user2.getKeywords()));
        } finally {
            em.close();
        }
    }

    @Test
    public void nonsortedset() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            User user = em.find(User.class, "madvirus@madvirus.net");
            assertFalse(isSorted(user.getKeywords()));
        } finally {
            em.close();
        }
    }

    @Test
    public void orderBy() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Sight sight = em.find(Sight.class, 1L);
            assertTrue(this.isSorted(sight.getRecItems(),
                    (o1, o2) -> o1.getName().compareTo(o2.getName())));
        } finally {
            em.close();
        }

    }
}
