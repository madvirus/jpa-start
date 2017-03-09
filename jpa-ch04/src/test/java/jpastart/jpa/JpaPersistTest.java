package jpastart.jpa;

import jpastart.reserve.model.Grade;
import jpastart.reserve.model.Hotel;
import jpastart.util.DBUtil;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class JpaPersistTest extends JpaTestBase {

    @Test
    public void persistNullEmbeddable() throws Exception {
        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Hotel hotel = new Hotel("KR-S-01", "서울호텔", Grade.STAR5, null);
            entityManager.persist(hotel);

            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }

        Connection conn = DBUtil.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from hotel where id = 'KR-S-01'");
            rs.next();
            assertThat(rs.getString("zipcode"), nullValue());
            assertThat(rs.getString("address1"), nullValue());
            assertThat(rs.getString("address2"), nullValue());
        } finally {
            DBUtil.close(rs);
            DBUtil.close(stmt);
            DBUtil.close(conn);
        }
    }

}

