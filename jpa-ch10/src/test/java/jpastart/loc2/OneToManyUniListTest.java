package jpastart.loc2;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.hibernate.Hibernate;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class OneToManyUniListTest extends JpaTestBase {

    private Location2 find(String id) {
        Location2 loc;
        EntityManager em = EMF.createEntityManager();
        try {
            loc = em.find(Location2.class, id);
            Hibernate.initialize(loc.getEngineers());
        } finally {
            em.close();
        }
        return loc;
    }

    @Test
    public void find() throws Exception {
        Location2 loc = find("LOC01");

        List<Engineer2> engs = loc.getEngineers();
        assertThat(engs.get(0).getId(), equalTo("ENG001"));
        assertThat(engs.get(1).getId(), equalTo("ENG002"));
        assertThat(engs.get(2).getId(), equalTo("ENG003"));
    }

    @Test
    public void remove() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Location2 loc = em.find(Location2.class, "LOC01");
            loc.getEngineers().remove(1);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }

        Location2 loc = find("LOC01");
        List<Engineer2> engs = loc.getEngineers();
        assertThat(engs.get(0).getId(), equalTo("ENG001"));
        assertThat(engs.get(1).getId(), equalTo("ENG003"));
    }
}
