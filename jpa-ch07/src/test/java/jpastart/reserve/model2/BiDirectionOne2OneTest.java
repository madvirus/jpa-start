package jpastart.reserve.model2;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model2.MembershipCard2;
import jpastart.reserve.model2.User2;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class BiDirectionOne2OneTest extends JpaTestBase {

    private MembershipCard2 findMembershipCard(String cardNumber) {
        try {
            return EMF.currentEntityManager().find(MembershipCard2.class, cardNumber);
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }

    @Test
    public void find_user_and_get_card() throws Exception {
        EntityManager em = EMF.currentEntityManager();
        try {
            User2 user = em.find(User2.class, "gildong@dooly.net");
            assertThat(user, sameInstance(user.getCard().getOwner()));
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }

    @Test
    public void find_user_and_card_then_issue() throws Exception {
        EntityManager em = EMF.currentEntityManager();

        try {
            em.getTransaction().begin();
            User2 user = em.find(User2.class, "madvirus@madvirus.net");
            MembershipCard2 memCard = em.find(MembershipCard2.class, "4040");
            user.issue(memCard);

            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }

        MembershipCard2 membershipCard = findMembershipCard("4040");
        assertThat(membershipCard.getOwner().getEmail(), equalTo("madvirus@madvirus.net"));
    }

    @Test
    public void find_user_and_card_then_no_issue_and_only_assignTo() throws Exception {
        EntityManager em = EMF.currentEntityManager();

        try {
            em.getTransaction().begin();
            User2 user = em.find(User2.class, "madvirus@madvirus.net");
            MembershipCard2 memCard = em.find(MembershipCard2.class, "4040");
            memCard.assignTo(user);
            assertThat(user.getCard(), nullValue());
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }

        MembershipCard2 membershipCard = findMembershipCard("4040");
        assertThat(membershipCard.getOwner().getEmail(), equalTo("madvirus@madvirus.net"));
    }

    @Test
    public void save_with_owner() throws Exception {
        EntityManager em = EMF.currentEntityManager();

        try {
            em.getTransaction().begin();

            User2 owner = em.find(User2.class, "madvirus@madvirus.net");

            ZonedDateTime zonedDateTime = ZonedDateTime.of(2020, 1, 31, 23, 59, 59, 0, ZoneId.systemDefault());
            Date expiryDate = Date.from(zonedDateTime.toInstant());

            MembershipCard2 memCard = new MembershipCard2("1234", owner, expiryDate);
            em.persist(memCard);

            em.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }

        String cardNumber = "1234";
        MembershipCard2 membershipCard = findMembershipCard(cardNumber);
        assertThat(membershipCard.getOwner(), notNullValue());
    }

    @Test
    public void save_with_non_persistence_owner() throws Exception {
        EntityManager em = EMF.currentEntityManager();

        Exception failCause = null;
        try {
            em.getTransaction().begin();
            User2 owner = new User2("jvm@javaworld.co", "JVM", new Date());

            ZonedDateTime zonedDateTime = ZonedDateTime.of(2020, 1, 31, 23, 59, 59, 0, ZoneId.systemDefault());
            Date expiryDate = Date.from(zonedDateTime.toInstant());

            MembershipCard2 memCard = new MembershipCard2("1234", owner, expiryDate);
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

            MembershipCard2 memCard = new MembershipCard2("1010", null, expiryDate);
            em.persist(memCard);

            em.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }

        MembershipCard2 membershipCard = findMembershipCard("1010");
        assertThat(membershipCard.getOwner(), nullValue());
    }

    @Test
    public void find_with_owner_and_disable() throws Exception {
        EntityManager em = EMF.currentEntityManager();
        try {
            em.getTransaction().begin();

            MembershipCard2 memCard = em.find(MembershipCard2.class, "5678");
            memCard.disable();
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }

    @Test
    public void find_with_owner_and_access_owners_name() throws Exception {
        EntityManager em = EMF.currentEntityManager();
        try {
            MembershipCard2 memCard = em.find(MembershipCard2.class, "5678");
            System.out.println(memCard.getOwner().getName());
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }

    @Test
    public void find_without_owner() throws Exception {
        EntityManager em = EMF.currentEntityManager();

        try {
            em.getTransaction().begin();

            MembershipCard2 memCard = em.find(MembershipCard2.class, "4040");
            User2 user = new User2("eric@dddcrop.com", "Eric", new Date());
            memCard.assignTo(user);
            em.persist(user);

            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }
}
