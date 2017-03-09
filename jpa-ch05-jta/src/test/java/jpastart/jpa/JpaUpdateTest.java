package jpastart.jpa;

import com.atomikos.icatch.jta.UserTransactionImp;
import jpastart.common.model.Address;
import jpastart.reserve.model.Hotel;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.UserTransaction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class JpaUpdateTest extends JpaTestBase {

    @Test
    public void updateInTx() throws Exception {
        EntityManager entityManager = EMF.createEntityManager();
        UserTransaction utx = new UserTransactionImp();

        try {
            utx.begin();
            entityManager.joinTransaction();
            Hotel hotel = entityManager.find(Hotel.class, "H100-01");
            hotel.changeAddress(new Address("08393", "서울시 구로구", "디지털로32길 72"));
            utx.commit();
        } catch (Exception ex) {
            utx.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }

        entityManager = EMF.createEntityManager();
        Hotel hotel = entityManager.find(Hotel.class, "H100-01");
        entityManager.close();
        assertThat(hotel.getAddress().getAddress2(), equalTo("디지털로32길 72"));
    }

}

