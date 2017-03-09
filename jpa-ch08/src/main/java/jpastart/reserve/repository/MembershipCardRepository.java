package jpastart.reserve.repository;

import jpastart.jpa.EMF;
import jpastart.reserve.model.MembershipCard;

import javax.persistence.EntityManager;

public class MembershipCardRepository {

    public MembershipCard find(String cardNumber) {
        EntityManager em = EMF.currentEntityManager();
        return em.find(MembershipCard.class, cardNumber);
    }

    public void save(MembershipCard membershipCard) {
        EntityManager em = EMF.currentEntityManager();
        em.persist(membershipCard);
    }

}
