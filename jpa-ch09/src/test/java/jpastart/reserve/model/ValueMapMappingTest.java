package jpastart.reserve.model;

import jpastart.common.model.Address;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.hibernate.Hibernate;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ValueMapMappingTest extends JpaTestBase {

    private Hotel2 findHotel(String id) {
        EntityManager em = EMF.createEntityManager();
        try {
            Hotel2 hotel = em.find(Hotel2.class, id);
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
            Hotel2 hotel = new Hotel2("H-GURO", "구로호텔", Grade.STAR4, new Address("12345", "addr1", "addr2"));
            hotel.addProperty("VIEW", new PropValue("5", "int"));
            hotel.addProperty("NOISE", new PropValue("1", "int"));
            em.persist(hotel);
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        Hotel2 hotel = findHotel("H-GURO");
        assertThat(hotel.getProperties().get("VIEW"), equalTo(new PropValue("5", "int")));
        assertThat(hotel.getProperties().get("NOISE"), equalTo(new PropValue("1", "int")));
    }

    @Test
    public void find() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Hotel2 hotel = em.find(Hotel2.class, "H100-01");
            Map<String, PropValue> properties = hotel.getProperties();
            PropValue viewValue = properties.get("VIEW");
            assertThat(viewValue.getValue(), equalTo("5"));
            assertThat(viewValue.getType(), equalTo("int"));
        } finally {
            em.close();
        }
    }

    @Test
    public void addAndRemoveAndUpdate() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Hotel2 hotel = em.find(Hotel2.class, "H100-01");
            Map<String, PropValue> properties = hotel.getProperties();
            properties.put("NOISE", new PropValue("3", "int"));
            properties.remove("VIEW");
            properties.put("AIR", new PropValue("4", "int"));
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
            Hotel2 hotel = em.find(Hotel2.class, "H100-01");
            Map<String, PropValue> properties = new HashMap<>();
            properties.put("NOISE", new PropValue("3", "int"));
            properties.put("AIR", new PropValue("4", "int"));
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
            Hotel2 hotel = em.find(Hotel2.class, "H100-01");
            Map<String, PropValue> properties = hotel.getProperties();
            properties.clear();
            properties.put("NOISE", new PropValue("3", "int"));
            properties.put("AIR", new PropValue("4", "int"));
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
            Hotel2 hotel = em.find(Hotel2.class, "H100-01");
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
            Hotel2 hotel = em.find(Hotel2.class, "H100-01");
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
            Hotel2 hotel = em.find(Hotel2.class, "H100-01");
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
