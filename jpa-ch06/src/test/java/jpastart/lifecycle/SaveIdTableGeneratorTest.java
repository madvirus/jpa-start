package jpastart.lifecycle;

import jpastart.common.model.Address;
import jpastart.guide.model.City;
import jpastart.guide.model.ContactInfo;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.Review;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SaveIdTableGeneratorTest extends JpaTestBase {

    @Test
    public void saveAndGetId() throws Exception {
        EntityManager em = EMF.createEntityManager();
        Long newId = null;
        try {
            em.getTransaction().begin();
            City city = new City("서울특별시", new ContactInfo("120", "seoul@seoul.go.kr", new Address("04524", "서울특별시 중구", "세종대로 110")));

            em.persist(city);

            newId = city.getId();

            city.setContactInfo(new ContactInfo("02-120", "call@seoul.go.kr", new Address("04524", "서울특별시 중구", "세종대로 110")));

            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        assertThat(newId, notNullValue());
        em = EMF.createEntityManager();
        try {
            assertThat(em.find(City.class, newId), notNullValue());
        } finally {
            em.close();
        }
    }

    @Test
    public void saveWithoutTx() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            City city = new City("서울특별시", new ContactInfo("02-120", "seoul@seoul.go.kr", new Address("04524", "서울특별시 중구", "세종대로 110")));

            em.persist(city);

            assertThat(city.getId(), notNullValue());

        } catch (RuntimeException ex) {
            throw ex;
        } finally {
            em.close();
        }
    }


}
