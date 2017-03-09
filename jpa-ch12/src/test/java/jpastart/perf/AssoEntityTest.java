package jpastart.perf;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class AssoEntityTest extends JpaTestBase {

    private List<Person> queryByPerformance(String perfId) {
        List<Person> peronList;
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery(
                    "select cm.person from CastMap cm " +
                            "where cm.id.performanceId = :perfId " +
                            "order by cm.person.name",
                    Person.class);
            query.setParameter("perfId", perfId);
            peronList = query.getResultList();
        } finally {
            em.close();
        }
        return peronList;
    }

    private void assertPersons(List<Person> cast, List<String> expectedNames) {
        assertThat(cast.size(), equalTo(expectedNames.size()));
        for (int i = 0 ; i < cast.size() ; i++) {
            assertThat(cast.get(i).getName(), equalTo(expectedNames.get(i)));
        }
    }
    @Test
    public void queryByPerformance() throws Exception {
        List<Person> peronList = queryByPerformance("PF001");
        assertThat(peronList.get(0).getName(), equalTo("최승희"));
        assertThat(peronList.get(1).getName(), equalTo("한성준"));
    }

    @Test
    public void find() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            CastMap castMap = em.find(CastMap.class, new CastMapId("PF001", "P01"));
            assertThat(castMap.getPerson().getName(), equalTo("최승희"));
            assertThat(castMap.getPerformance().getName(), equalTo("한국무용"));
        } finally {
            em.close();
        }
    }

    @Test
    public void persist() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Performance perf = em.find(Performance.class, "PF002");
            Person person = em.find(Person.class, "P05");
            CastMap castMap = new CastMap(perf, person);
            em.persist(castMap);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }

        List<Person> peronList = queryByPerformance("PF002");
        assertPersons(peronList, Arrays.asList("국악인", "비보이", "자유인", "최승희"));
    }

    @Test
    public void remove() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            CastMap castMap = em.find(CastMap.class, new CastMapId("PF002", "P03"));
            em.remove(castMap);

            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }

        List<Person> peronList = queryByPerformance("PF002");
        assertPersons(peronList, Arrays.asList("국악인", "최승희"));
    }

    @Test
    public void nameLike() {
        List<Person> peronList;
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery(
                    "select cm.person from CastMap cm " +
                            "where cm.id.performanceId = :perfId " +
                            "and cm.person.name like :name "+
                            "order by cm.person.name",
                    Person.class);
            query.setParameter("perfId", "PF001");
            query.setParameter("name", "최%");
            peronList = query.getResultList();
        } finally {
            em.close();
        }
        assertPersons(peronList, Arrays.asList("최승희"));
    }
}
