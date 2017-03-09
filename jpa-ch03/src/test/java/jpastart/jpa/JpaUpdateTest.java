package jpastart.jpa;

import jpastart.reserve.model.Room;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class JpaUpdateTest extends JpaTestBase {

    @Test
    public void updateInTx() throws Exception {
        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Room room = entityManager.find(Room.class, "R101");
            room.changeName("카프리");
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }

        entityManager = EMF.createEntityManager();
        Room room = entityManager.find(Room.class, "R101");
        entityManager.close();
        assertThat(room.getName(), equalTo("카프리"));
    }

}

