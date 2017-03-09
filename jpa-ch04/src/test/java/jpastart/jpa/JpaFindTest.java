package jpastart.jpa;

import jpastart.reserve.model.Hotel;
import jpastart.reserve.model.Room;
import jpastart.util.DBTestResource;
import org.junit.Rule;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class JpaFindTest extends JpaTestBase {

    @Test
    public void findNotNullEmbeddedValue() throws Exception {
        Hotel hotel = findHotelById("H100-01");
        assertThat(hotel.getAddress(), notNullValue());
    }

    @Test
    public void findNullEmbeddedValue() throws Exception {
        Hotel hotel = findHotelById("H100-02");
        assertThat(hotel.getAddress(), nullValue());
    }

    private Hotel findHotelById(String hotelId) {
        Hotel room = null;
        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            room = entityManager.find(Hotel.class, hotelId);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
        return room;
    }

}

