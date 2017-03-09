package jpastart.perf;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ManyToManyUniDirTest extends JpaTestBase {

    private Performance findPerformance(String id) {
        Performance performance;
        EntityManager em = EMF.createEntityManager();
        try {
            performance = em.find(Performance.class, id);
            Set<Person> cast = performance.getCast();
            cast.size();
        } finally {
            em.close();
        }
        return performance;
    }

    private void assertPersons(Set<Person> cast, List<String> expectedNames) {
        List<Person> persons = cast.stream()
                .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                .collect(Collectors.toList());
        for (int i = 0; i < expectedNames.size(); i++) {
            assertThat(persons.get(i).getName(), equalTo(expectedNames.get(i)));
        }
    }

    @Test
    public void find() throws Exception {
        Performance performance = findPerformance("PF001");

        assertPersons(performance.getCast(), Arrays.asList("최승희", "한성준"));

        Performance performance2 = findPerformance("PF002");
        assertPersons(performance2.getCast(), Arrays.asList("국악인", "비보이", "최승희"));
    }

    @Test
    public void persist() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Performance performance = new Performance("PERF-X", "공연X");
            Person person1 = new Person("PX1", "허공전");
            Person person2 = new Person("PX2", "허생원");
            performance.addCast(person1);
            performance.addCast(person2);

            em.persist(performance);
            em.persist(person1);
            em.persist(person2);

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        Performance perf = findPerformance("PERF-X");
        assertPersons(perf.getCast(), Arrays.asList("허공전", "허생원"));
    }

    @Test
    public void name() throws Exception {


    }
}
