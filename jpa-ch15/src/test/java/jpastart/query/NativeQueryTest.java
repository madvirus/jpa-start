package jpastart.query;

import jpastart.common.model.Address;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.Hotel;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class NativeQueryTest extends JpaTestBase {
    @Test
    public void nativeQuery_NoMapping() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Query query = em.createNativeQuery(
                    "select id, name, grade from hotel " +
                            "where grade = :grade order by id asc " +
                            "limit :first, :max"
            );
            query.setParameter("grade", "STAR4");
            query.setParameter("first", 0);
            query.setParameter("max", 1);

            List<Object[]> resultList = query.getResultList();
            for (Object[] row : resultList) {
                String id = (String) row[0];
                String name = (String) row[1];
                String grade = (String) row[2];
                System.out.printf("%s %s %s\n", id, name, grade);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void nativeQuery_EntityMapping() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Query query = em.createNativeQuery(
                    "select id, name, grade, zipcode, address1, address2 " +
                            "from hotel where grade = :grade order by id asc",
                    Hotel.class);
            query.setParameter("grade", "STAR4");
            List<Hotel> resultList = query.getResultList();
            for (Hotel hotel : resultList) {
                System.out.printf("%s %s %s\n",
                        hotel.getId(),
                        hotel.getName(),
                        hotel.getGrade());
            }
            if (resultList.size() > 0) {
                Hotel hotel = resultList.get(0);
                hotel.changeAddress(new Address("12345", "서울", "구로"));
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void xmlNamedNativeQuery_EntityMapping() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Query query = em.createNamedQuery("Hotel.byGrade");
            query.setParameter("grade", "STAR4");
            List<Hotel> resultList = query.getResultList();
            for (Hotel hotel : resultList) {
                System.out.printf("%s %s %s\n",
                        hotel.getId(),
                        hotel.getName(),
                        hotel.getGrade());
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void annotNamedNativeQuery_EntityMapping() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Query query = em.createNamedQuery("Hotel.all");
            List<Hotel> resultList = query.getResultList();
            for (Hotel hotel : resultList) {
                System.out.printf("%s %s %s\n",
                        hotel.getId(),
                        hotel.getName(),
                        hotel.getGrade());
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
