package jpastart.loc_map;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class OneToManyUniMap_JoinTable_Test extends JpaTestBase {
    private Location3 findLocation(String id) {
        Location3 location = null;
        EntityManager em = EMF.createEntityManager();
        try {
            location = em.find(Location3.class, id);
            Engineer3 mainEngineer = location.getMainCharge();
        } finally {
            em.close();
        }
        return location;
    }

    @Test
    public void find() {
        Location3 location = findLocation("LOC01");
        assertThat(location.getMainCharge().getName(), equalTo("최고수"));
    }

    @Test
    public void update() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Location3 location = em.find(Location3.class, "LOC01");
            Engineer3 engineer3 = em.find(Engineer3.class, "ENG003");
            location.setMainCharge(engineer3);
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        Location3 loc01 = findLocation("LOC01");
        assertThat(loc01.getMainCharge().getId(), equalTo("ENG003"));
    }

    @Test
    public void clear() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Location3 location = em.find(Location3.class, "LOC01");
            location.clearCharge();
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        Location3 loc01 = findLocation("LOC01");
        assertThat(loc01.getMainCharge(), nullValue());
        assertThat(loc01.getSubCharge(), nullValue());
    }
}
