package jpastart.jpa;

import jpastart.guide.model.SightDomain;
import jpastart.common.model.Address;
import jpastart.guide.model.Sight;
import jpastart.guide.model.SightDetail;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class SeparateValueTableTest extends JpaTestBase {
    @Test
    public void save_with_secondaryData() throws Exception {
        Sight sight = new Sight("경복궁",
                new Address("03045", "서울시 종로구", "세종로 1-1"),
                new Address("03045", "Jongno-gu, Seoul", "1-1, Sejong-ro")
        );
        sight.setDetail(new SightDetail("09~17시", "매주 화요일", "안내 설명"));

        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(sight);

            sight.getId();
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }

        Sight loadedSight = null;
        entityManager = EMF.createEntityManager();
        try {
            loadedSight = entityManager.find(Sight.class, sight.getId());
        } finally {
            entityManager.close();
        }
        assertThat(
                loadedSight.getDetail().getHoursOfOperation(),
                equalTo(sight.getDetail().getHoursOfOperation()));
    }

    @Test
    public void save_without_secondaryData() throws Exception {
        Sight sight = new Sight("광화문",
                new Address("03045", "서울시 종로구", "세종로 1-58"),
                new Address("03045", "Jongno-gu, Seoul", "1-58, Sejong-ro")
        );

        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(sight);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }

        Sight loadedSight = null;
        entityManager = EMF.createEntityManager();
        try {
            loadedSight = entityManager.find(Sight.class, sight.getId());
        } finally {
            entityManager.close();
        }
        assertThat(loadedSight.getDetail(), nullValue());
    }

    @Test
    public void find_when_secondaryData_exists() throws Exception {
        SightDomain.instance().truncate().initHorimSightWithDetail();

        Sight sight = null;
        EntityManager entityManager = EMF.createEntityManager();
        try {
            sight = entityManager.find(Sight.class, 1L);
        } finally {
            entityManager.close();
        }
        assertThat(sight.getDetail(), notNullValue());
        assertThat(sight.getDetail().getHoursOfOperation(), equalTo("오전10시-오후5시"));
    }

    @Test
    public void find_when_no_secondaryData_exists() throws Exception {
        SightDomain.instance().truncate().initGwanakMtSightWithoutDetail();

        Sight sight = null;
        EntityManager entityManager = EMF.createEntityManager();
        try {
            sight = entityManager.find(Sight.class, 1L);
        } finally {
            entityManager.close();
        }
        assertThat(sight.getDetail(), nullValue());
    }

    @Test
    public void set_newobj_when_secondaryData_exists() throws Exception {
        SightDomain.instance().truncate().initHorimSightWithDetail();

        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Sight sight = entityManager.find(Sight.class, 1L);
            sight.setDetail(new SightDetail("오전9시~오후5시", "연중무휴", "100여대 주차 가능"));
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }


    @Test
    public void change_property_when_secondaryData_exists() throws Exception {
        SightDomain.instance().truncate().initHorimSightWithDetail();

        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Sight sight = entityManager.find(Sight.class, 1L);
            sight.getDetail().setHolidays("일,월");
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    @Test
    public void set_null_when_secondaryData_exists() throws Exception {
        SightDomain.instance().truncate().initHorimSightWithDetail();

        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Sight sight = entityManager.find(Sight.class, 1L);
            sight.setDetail(null);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    @Test
    public void set_newobj_when_no_secondaryData() throws Exception {
        SightDomain.instance().truncate().initGwanakMtSightWithoutDetail();

        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Sight sight = entityManager.find(Sight.class, 1L);
            sight.setDetail(new SightDetail("오전9시~오후5시", "연중무휴", "100여대 주차 가능"));
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    @Test
    public void set_null_when_no_secondaryData() throws Exception {
        SightDomain.instance().truncate().initGwanakMtSightWithoutDetail();

        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Sight sight = entityManager.find(Sight.class, 1L);
            sight.setDetail(null);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }
}
