package jpastart.reserve.model;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.OneToOne;
import java.lang.reflect.Field;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class CascadeTest extends JpaTestBase {

    @Before
    public void checkCascade() throws Exception {
        Field ownerField = MembershipCard.class.getDeclaredField("owner");
        OneToOne oneToOne = ownerField.getAnnotation(OneToOne.class);

        List<CascadeType> cascadeConfig = Arrays.asList(oneToOne.cascade());
        if (!cascadeConfig.contains(CascadeType.ALL)) {
            if (!cascadeConfig.contains(CascadeType.PERSIST))
                throw new RuntimeException("테스트 실행 전에 MembershipCard.owner의 cascade에 PERSIST를 추가해야 함");
        }
    }

    private MembershipCard findMembershipCard(String cardNo) {
        EntityManager em = EMF.createEntityManager();
        try {
            MembershipCard card = em.find(MembershipCard.class, cardNo);
            Hibernate.initialize(card.getOwner());
            return card;
        } finally {
            em.close();
        }
    }

    @Test
    public void save_with_owner() throws Exception {
        EntityManager em = EMF.currentEntityManager();

        try {
            em.getTransaction().begin();
            User owner = new User("jvm@javaworld.co", "JVM", new Date());

            ZonedDateTime zonedDateTime = ZonedDateTime.of(2020, 1, 31, 23, 59, 59, 0, ZoneId.systemDefault());
            Date expiryDate = Date.from(zonedDateTime.toInstant());

            MembershipCard memCard = new MembershipCard("1234", owner, expiryDate);

            //em.persist(owner);
            em.persist(memCard);
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            EMF.closeCurrentEntityManager();
        }

        MembershipCard card = findMembershipCard("1234");
        assertThat(card.getOwner(), notNullValue());
    }


}
