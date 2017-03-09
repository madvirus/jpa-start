package jpastart.member;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Date;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class MemberTest extends JpaTestBase {
    @Test
    public void save() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            EntMember emem = new EntMember("e002", "기업고객2", "ENT001");
            em.persist(emem);

            PersonalMember pmem = new PersonalMember("p002", "개인고객2", "p0022person.to");
            em.persist(pmem);

            TempMember tmem = new TempMember("t002", "임시고객", "t002@person.to", new Date());
            em.persist(tmem);

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void saveDup() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            EntMember emem = new EntMember("m001", "기업고객2", "ENT001");
            em.persist(emem);

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        EntityManager em2 = EMF.createEntityManager();
        try {
            em2.getTransaction().begin();

            PersonalMember pmem = new PersonalMember("m001", "개인고객2", "p0022@person.to");
            em2.persist(pmem);

            em2.getTransaction().commit();
        } catch (Exception ex) {
            em2.getTransaction().rollback();
            throw ex;
        } finally {
            em2.close();
        }
    }

    @Test
    public void find_superType() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            Member member = em.find(Member.class, "p001");
            assertThat(member, instanceOf(PersonalMember.class));
        } finally {
            em.close();
        }
    }

    @Test
    public void find_specificType() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            EntMember member = em.find(EntMember.class, "e001");
            assertThat(member, instanceOf(EntMember.class));

            PersonalMember pmember = em.find(PersonalMember.class, "p001");
            assertThat(pmember, instanceOf(PersonalMember.class));
        } finally {
            em.close();
        }
    }
}
