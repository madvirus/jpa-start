package jpastart.reserve.model;

import jpastart.common.model.Address;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.hibernate.Hibernate;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

public class ElementMapMappingTest extends JpaTestBase {

    private Hotel findHotel(String id) {
        EntityManager em = EMF.createEntityManager();
        try {
            Hotel hotel = em.find(Hotel.class, id);
            Hibernate.initialize(hotel.getProperties());
            return hotel;
        } finally {
            em.close();
        }
    }

    @Test
    public void persist() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Hotel hotel = new Hotel("H-GURO", "구로호텔", Grade.STAR4, new Address("12345", "addr1", "addr2"));
            hotel.addProperty("추가1", "추가 정보");
            hotel.addProperty("추가2", "추가 정보2");
            em.persist(hotel);
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        Hotel hotel = findHotel("H-GURO");
        assertThat(hotel.getProperties().get("추가1"), equalTo("추가 정보"));
        assertThat(hotel.getProperties().get("추가2"), equalTo("추가 정보2"));
    }

    @Test
    public void find() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Hotel hotel = em.find(Hotel.class, "H100-01");
            Map<String, String> properties = hotel.getProperties();
            String viewValue = properties.get("VIEW");
            assertThat(viewValue, equalTo("좋음"));
        } finally {
            em.close();
        }
    }

    @Test
    public void addAndRemoveAndUpdate() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Hotel hotel = em.find(Hotel.class, "H100-01");
            Map<String, String> properties = hotel.getProperties();
            properties.put("NOISE", "조용");
            properties.remove("VIEW");
            properties.put("AIR", "좋음");
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void assignNewMap() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Hotel hotel = em.find(Hotel.class, "H100-01");
            Map<String, String> properties = new HashMap<>();
            properties.put("NOISE", "조용");
            properties.put("AIR", "좋음");
            hotel.setProperties(properties);
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
            Hotel hotel = em.find(Hotel.class, "H100-01");
            Map<String, String> properties = hotel.getProperties();
            properties.clear();
            properties.put("NOISE", "조용");
            properties.put("AIR", "좋음");
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
            Hotel hotel = em.find(Hotel.class, "H100-01");
            hotel.getProperties().clear();
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
            Hotel hotel = em.find(Hotel.class, "H100-01");
            hotel.setProperties(Collections.emptyMap());
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
            Hotel hotel = em.find(Hotel.class, "H100-01");
            hotel.setProperties(null);
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }


}
