package jpastart.item;

import jpastart.common.Money;
import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ItemTest extends JpaTestBase {
    @Test
    public void save() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            em.persist(new Item("I002", "아이템2", new Money(1000.0, "KRW")));

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
            Item item = em.find(Item.class, "I001");
            assertThat(item.getPrice().getValue().doubleValue(), equalTo(1000.0));
        } finally {
            em.close();
        }
    }
}
