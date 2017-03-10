package jpastart.jpa;

import jpastart.reserve.model.MonChargeId;
import jpastart.reserve.model.MonthCharge;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class CompositKeyTest extends JpaTestBase {
    @Test
    public void find() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            MonthCharge monthCharge = em.find(MonthCharge.class, new MonChargeId("H100-01", "201608"));
            assertThat(monthCharge, notNullValue());
            assertThat(monthCharge.getId().getHotelId(), equalTo("H100-01"));
            assertThat(monthCharge.getId().getYearMon(), equalTo("201608"));
            assertThat(monthCharge.getChargeAmount(), equalTo(1000));
            assertThat(monthCharge.getUnpayAmount(), equalTo(0));
        } finally {
            em.close();
        }
    }

    @Test
    public void persist() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            MonthCharge monthCharge = new MonthCharge();
            monthCharge.setId(new MonChargeId("H101-01", "201607"));
            monthCharge.setChargeAmount(30000);
            monthCharge.setUnpayAmount(10000);
            em.persist(monthCharge);
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
