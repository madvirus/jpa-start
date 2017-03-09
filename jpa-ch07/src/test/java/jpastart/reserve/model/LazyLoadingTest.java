package jpastart.reserve.model;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.lang.reflect.Field;

import static org.junit.Assert.fail;

/**
 * MembershipCard에서 User의 @OneToOne의 fetch가 Lazy여야 함
 */
public class LazyLoadingTest extends JpaTestBase {

    @Before
    public void checkLazy() throws Exception {
        Field ownerField = MembershipCard.class.getDeclaredField("owner");
        OneToOne oneToOne = ownerField.getAnnotation(OneToOne.class);
        if (oneToOne.fetch() != FetchType.LAZY)
            throw new RuntimeException("테스트 실행 전에 MembershipCard.owner를 LAZY로 설정해야 함");
    }

    @Test
    public void find_with_owner_and_access_after_session_closed() throws Exception {
        EntityManager em = EMF.currentEntityManager();
        MembershipCard memCard = null;
        try {
            memCard = em.find(MembershipCard.class, "5678");
        } finally {
            EMF.closeCurrentEntityManager();
        }
        User owner = memCard.getOwner();
        try {
            owner.getName();
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
