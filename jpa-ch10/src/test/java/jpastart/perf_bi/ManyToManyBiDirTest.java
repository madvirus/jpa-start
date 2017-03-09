package jpastart.perf_bi;

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

public class ManyToManyBiDirTest extends JpaTestBase {
    private Performance findPerformance(String id) {
        EntityManager em = EMF.createEntityManager();
        try {
            Performance performance = em.find(Performance.class, id);
            Set<Person> cast = performance.getCast();
            cast.size();
            return performance;
        } finally {
            em.close();
        }
    }

    private Person findPerson(String id) {
        EntityManager em = EMF.createEntityManager();
        try {
            Person person = em.find(Person.class, id);
            person.getPerfs().size();
            return person;
        } finally {
            em.close();
        }
    }

    private void assertPersons(Set<Person> cast, List<String> expectedNames) {
        List<String> persons = cast.stream().map(p -> p.getName()).sorted().collect(Collectors.toList());
        assertThat(persons, equalTo(expectedNames));
    }

    private void assertPerformance(Set<Performance> perfs, List<String> expectedNames) {
        List<String> perfList = perfs.stream().map(p -> p.getName()).sorted().collect(Collectors.toList());
        assertThat(perfList, equalTo(expectedNames));
    }

    @Test
    public void findPerformance() throws Exception {
        Performance performance = findPerformance("PF001");

        assertPersons(performance.getCast(), Arrays.asList("최승희", "한성준"));

        Performance performance2 = findPerformance("PF002");
        assertPersons(performance2.getCast(), Arrays.asList("국악인", "비보이", "최승희"));
    }

    @Test
    public void findPerson() throws Exception {
        Person person = findPerson("P01");
        assertPerformance(person.getPerfs(), Arrays.asList("공연X", "한국무용"));

        Person person2 = findPerson("P02");
        Performance perf = person2.getPerfs().iterator().next();
        assertThat(perf.getId(), equalTo("PF001"));
    }


    @Test
    public void assignPersonToPerformance() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Person person = em.find(Person.class, "P05");

            Performance perf1 = em.find(Performance.class, "PF001");
            perf1.addCast(person); // 연관 소유한 쪽에 연관 적용
            person.addPerformance(perf1); // 양방향 연관이 코드에서도 동작하도록 연관 적용

            Performance perf2 = em.find(Performance.class, "PF002");
            perf2.addCast(person);
            person.addPerformance(perf2);

            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        Person person = findPerson("P05");
        assertPerformance(person.getPerfs(), Arrays.asList("공연X", "한국무용"));
    }

}
