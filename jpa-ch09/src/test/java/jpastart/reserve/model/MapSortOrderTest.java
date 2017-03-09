package jpastart.reserve.model;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MapSortOrderTest extends JpaTestBase {

    private <T> boolean isSorted(Map<String, T> properties) {
        Comparator<String> comparator = (Comparator<String>) Comparator.naturalOrder();
        return this.isSorted(properties, comparator);
    }

    private <T> boolean isSorted(Map<String, T> values, Comparator<String> comparator) {
        List<String> vals = new ArrayList<>(values.keySet());
        List<String> vals2 = new ArrayList<>(values.keySet());
        vals.sort(comparator);
        for (int i = 0 ; i < vals.size() ; i++) {
            if (!vals2.get(i).equals(vals.get(i))) return false;
        }
        return true;
    }

    @Test // Hote2의 properties로 @SortNatural로 설정한 뒤에 테스트
    @Ignore
    public void sort() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Hotel2 hotel = em.find(Hotel2.class, "H100-02");
            assertTrue(isSorted(hotel.getProperties()));
        } finally {
            em.close();
        }
    }

    @Test
    public void nonsortedset() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Hotel hotel = em.find(Hotel.class, "H100-02");
            assertFalse(isSorted(hotel.getProperties()));
        } finally {
            em.close();
        }
    }

    @Test
    @Ignore // Hote2의 properties를 @OrderBy로 설정한 뒤에 테스트
    public void orderBy() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Hotel2 hotel = em.find(Hotel2.class, "H100-02");
            assertTrue(isSorted(hotel.getProperties()));
        } finally {
            em.close();
        }
    }
}
