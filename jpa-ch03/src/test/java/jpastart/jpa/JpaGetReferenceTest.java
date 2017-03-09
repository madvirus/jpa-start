package jpastart.jpa;

import jpastart.reserve.model.Hotel;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class JpaGetReferenceTest extends JpaTestBase {

    @Test
    public void accessInTx() throws Exception {
        Hotel hotel = null;
        EntityManager entityManager = EMF.createEntityManager();
        try {
            hotel = entityManager.getReference(Hotel.class, "H100-01");
            String name = hotel.getName(); //또는 Hibernate.initialize(hotel);
        } catch (Exception ex) {
            throw ex;
        } finally {
            entityManager.close();
        }
        String name = hotel.getName();
        assertThat(name, notNullValue());
    }

    @Test
    public void accessOutTx() throws Exception {
        Hotel hotel = null;
        EntityManager entityManager = EMF.createEntityManager();
        try {
            hotel = entityManager.getReference(Hotel.class, "H100-01");
        } catch (Exception ex) {
            throw ex;
        } finally {
            entityManager.close();
        }

        try {
            hotel.getName();
            fail("발생해야 함");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

