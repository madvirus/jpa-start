package jpastart.loc;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.hibernate.Hibernate;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class OneToManyUniList_JoinTable_Test extends JpaTestBase {

    private Location find(String id) {
        Location loc;
        EntityManager em = EMF.createEntityManager();
        try {
            loc = em.find(Location.class, id);
            Hibernate.initialize(loc.getEngineers());
        } finally {
            em.close();
        }
        return loc;
    }

    @Test
    public void find() throws Exception {
        Location loc = find("LOC01");

        List<Engineer> engs = loc.getEngineers();
        assertThat(engs.get(0).getId(), equalTo("ENG001"));
        assertThat(engs.get(1).getId(), equalTo("ENG002"));
        assertThat(engs.get(2).getId(), equalTo("ENG003"));
    }

    @Test
    public void remove() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Location loc = em.find(Location.class, "LOC01");
            loc.getEngineers().remove(1);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }

        Location loc = find("LOC01");
        List<Engineer> engs = loc.getEngineers();
        assertThat(engs.get(0).getId(), equalTo("ENG001"));
        assertThat(engs.get(1).getId(), equalTo("ENG003"));
    }
}
