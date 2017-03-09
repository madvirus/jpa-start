package jpastart.issue;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;

import java.util.Date;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class IssueTest extends JpaTestBase {
    @Test
    public void find_by_SuperType() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Issue issue = em.find(Issue.class, 1L);
            Issue vrIssue = em.find(Issue.class, 2L);
            assertThat(vrIssue, instanceOf(VisitReservation.class));
            Issue apIssue = em.find(Issue.class, 3L);
            assertThat(apIssue, instanceOf(Appeal.class));
        } finally {
            em.close();
        }
    }

    @Test
    public void find_by_SpecificType() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Issue issue = em.find(Issue.class, 1L);
            VisitReservation vrIssue = em.find(VisitReservation.class, 2L);
            assertThat(vrIssue.getAssigneeEngineerId(), notNullValue());
            Appeal apIssue = em.find(Appeal.class, 3L);
            assertThat(apIssue.getResponse(), notNullValue());
        } finally {
            em.close();
        }
    }

    @Test
    public void save() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Issue issue = new Issue(new Date(), "신림파", "010-9999-8888", "이슈");
            em.persist(issue);

            VisitReservation visitRes = new VisitReservation(
                    new Date(), "신림파", "010-9999-8888", "방문",
                    "eng01", new Date());
            em.persist(visitRes);

            Appeal appeal = new Appeal(
                    new Date(), "신림파", "010-9999-8888", "요청",
                    "응답");
            em.persist(appeal);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
