package jpastart.item;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class MappedSuperClassTest extends JpaTestBase {


    @Test
    public void save() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Category entity = new Category();
            entity.setId("ID");
            entity.setName("카레고리");
            entity.setCreationDate(new Date());
            entity.setCreationEmpId("operator");
            entity.setCreationIp("10.20.30.40");
            em.persist(entity);

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void find() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Category cat = em.find(Category.class, "CAT01");
            assertThat(cat, notNullValue());
        } finally {
            em.close();
        }
    }
}
