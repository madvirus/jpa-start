package jpastart.jpa;

import jpastart.reserve.model.*;
import jpastart.util.DBTestResource;
import org.junit.Rule;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class JpaFindTest extends JpaTestBase {

    @Rule
    public DBTestResource resource = new DBTestResource();

    @Test
    public void find() throws Exception {
        assertThat(findRoomById("R101"), notNullValue());
        assertThat(findRoomById("NO_ID"), nullValue());
    }

    private Room findRoomById(String roomId) {
        Room room = null;
        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            room = entityManager.find(Room.class, roomId);
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

