package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.MembershipCard;
import jpastart.reserve.model.User;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class N_Plus_OneQueryTest extends JpaTestBase {
    @Test
    public void find() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            MembershipCard membershipCard = em.find(MembershipCard.class, "5678");
            System.out.println(membershipCard.getNumber());
        } finally {
            em.close();
        }
    }

    @Test
    public void queryWithoutJoin() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<MembershipCard> query = em.createQuery("select mc from MembershipCard mc", MembershipCard.class);
            List<MembershipCard> cards = query.getResultList();
            System.out.println("=================== getResultList");
            for (MembershipCard sight : cards) {
                User user = sight.getOwner();
                System.out.println(user == null ? "not issued" : user.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void queryWithJoin() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<MembershipCard> query = em.createQuery("select mc from MembershipCard mc left join mc.owner u", MembershipCard.class);
            List<MembershipCard> cards = query.getResultList();
            System.out.println("=================== getResultList");
            for (MembershipCard sight : cards) {
                User user = sight.getOwner();
                System.out.println(user == null ? "not issued" : user.getName());
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void queryWithFetchJoin() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<MembershipCard> query = em.createQuery("select mc from MembershipCard mc left join fetch mc.owner u", MembershipCard.class);
            List<MembershipCard> cards = query.getResultList();
            System.out.println("=================== getResultList");
            for (MembershipCard sight : cards) {
                User user = sight.getOwner();
                System.out.println(user == null ? "not issued" : user.getName());
            }
        } finally {
            em.close();
        }
    }

}
