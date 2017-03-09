package jpastart.reserve.model;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.repository.MembershipCardRepository;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxyHelper;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class UniDirectionOne2OneTest extends JpaTestBase {

    private final MembershipCardRepository membershipCardRepository = new MembershipCardRepository();

    private MembershipCard findMembershipCard(String cardNumber) {
        try {
            MembershipCard membershipCard = membershipCardRepository.find(cardNumber);
            Hibernate.initialize(membershipCard.getOwner());
            return membershipCard;
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }

    @Test
    public void save_with_owner() throws Exception {
        EntityManager em = EMF.currentEntityManager();

        try {
            em.getTransaction().begin();

            User owner = em.find(User.class, "madvirus@madvirus.net");

            ZonedDateTime zonedDateTime = ZonedDateTime.of(2020, 1, 31, 23, 59, 59, 0, ZoneId.systemDefault());
            Date expiryDate = Date.from(zonedDateTime.toInstant());

            MembershipCard memCard = new MembershipCard("1234", owner, expiryDate);
            em.persist(memCard);

            em.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }

        String cardNumber = "1234";
        MembershipCard membershipCard = findMembershipCard(cardNumber);
        assertThat(membershipCard.getOwner(), notNullValue());
    }

    @Test
    public void save_with_non_persistence_owner() throws Exception {
        EntityManager em = EMF.currentEntityManager();

        Exception failCause = null;
        try {
            em.getTransaction().begin();
            User owner = new User("jvm@javaworld.co", "JVM", new Date());

            ZonedDateTime zonedDateTime = ZonedDateTime.of(2020, 1, 31, 23, 59, 59, 0, ZoneId.systemDefault());
            Date expiryDate = Date.from(zonedDateTime.toInstant());

            MembershipCard memCard = new MembershipCard("1234", owner, expiryDate);
            em.persist(memCard);

            em.getTransaction().commit();
        } catch(Exception ex) {
            failCause = ex;
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }
        assertThat(failCause, notNullValue());
    }

    @Test
    public void save_without_owner() throws Exception {
        EntityManager em = EMF.currentEntityManager();

        try {
            em.getTransaction().begin();

            ZonedDateTime zonedDateTime = ZonedDateTime.of(2020, 1, 31, 23, 59, 59, 0, ZoneId.systemDefault());
            Date expiryDate = Date.from(zonedDateTime.toInstant());

            MembershipCard memCard = new MembershipCard("1010", null, expiryDate);
            em.persist(memCard);

            em.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }

        MembershipCard membershipCard = findMembershipCard("1010");
        assertThat(membershipCard.getOwner(), nullValue());
    }

    @Test
    public void find_with_owner_and_disable() throws Exception {
        EntityManager em = EMF.currentEntityManager();
        try {
            em.getTransaction().begin();

            MembershipCard memCard = em.find(MembershipCard.class, "5678");
            memCard.disable();
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }

    @Test
    public void find_without_owner() throws Exception {
        EntityManager em = EMF.currentEntityManager();

        try {
            em.getTransaction().begin();

            MembershipCard memCard = em.find(MembershipCard.class, "4040");
            assertThat(memCard.getOwner(), nullValue());

            User user = new User("eric@dddcrop.com", "Eric", new Date());
            memCard.assignTo(user);
            em.persist(user);

            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }

        MembershipCard membershipCard = findMembershipCard("4040");
        assertThat(membershipCard.getOwner(), notNullValue());
    }
}
