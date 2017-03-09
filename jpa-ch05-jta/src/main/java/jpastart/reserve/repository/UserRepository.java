package jpastart.reserve.repository;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;

import javax.persistence.EntityManager;

public class UserRepository {

    public User find(String email) {
        EntityManager em = EMF.currentEntityManager();
        return em.find(User.class, email);
    }

    public void remove(User user) {
        EntityManager em = EMF.currentEntityManager();
        em.remove(user);
    }
}
