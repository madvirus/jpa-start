package jpastart.reserve.model;

import jpastart.common.model.Address;
import jpastart.guide.model.RecItem;
import jpastart.guide.model.Sight;
import jpastart.guide.model.SightDetail;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.hibernate.Hibernate;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

public class ValueSetMappingTest extends JpaTestBase {

    private Sight findSight(Long id) {
        EntityManager em = EMF.createEntityManager();
        try {
            Sight sight = em.find(Sight.class, id);
            Hibernate.initialize(sight.getRecItems());
            return sight;
        } finally {
            em.close();
        }
    }

    @Test
    public void persist() throws Exception {
        Long savedId = null;
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Sight sight = new Sight("경복궁",
                    new Address("03045", "서울시 종로구", "세종로 1-1"),
                    new Address("03045", "Jongno-gu, Seoul", "1-1, Sejong-ro")
            );
            sight.setDetail(new SightDetail("09~17시", "매주 화요일", "안내 설명"));

            Set<RecItem> recItems = new HashSet<>();
            recItems.add(new RecItem("광화문", "WALL"));
            recItems.add(new RecItem("근정전", "ARCH"));
            recItems.add(new RecItem("사정전", "ARCH"));
            recItems.add(new RecItem("교태전", "ARCH"));
            recItems.add(new RecItem("동궁", "ARCH"));
            recItems.add(new RecItem("경희루", "PARTY"));
            sight.setRecItems(recItems);

            em.persist(sight);

            em.getTransaction().commit();

            savedId = sight.getId();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        Sight sight = findSight(savedId);
        sight.getRecItems().forEach(System.out::println);
    }

    @Test
    public void addAndRemove() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Sight sight = em.find(Sight.class, 1L);
            sight.getRecItems().add(new RecItem("청계천", "STREAM"));
            sight.getRecItems().remove(new RecItem("사정전", "ARCH"));

            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void assignNewSet() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Sight sight = em.find(Sight.class, 1L);

            Set<RecItem> recItems = new HashSet<>();
            recItems.add(new RecItem("광화문", "WALL"));
            recItems.add(new RecItem("근정전", "ARCH"));
            sight.setRecItems(recItems);

            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void clearAndAdd() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Sight sight = em.find(Sight.class, 1L);
            sight.getRecItems().clear();
            sight.getRecItems().add(new RecItem("광화문", "WALL"));
            sight.getRecItems().add(new RecItem("청계천", "STREAM"));
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void clear() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Sight sight = em.find(Sight.class, 1L);
            sight.getRecItems().clear();
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void assignEmptySet() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Sight sight = em.find(Sight.class, 1L);
            sight.setRecItems(new HashSet<>());

            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
    @Test
    public void assignNull() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Sight sight = em.find(Sight.class, 1L);
            sight.setRecItems(null);

            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
