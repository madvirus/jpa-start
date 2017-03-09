package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;

import javax.persistence.EntityManager;

public class JoinService {
    public void join(User user) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        try {
            User found = em.find(User.class, user.getEmail());
            if (found != null) {
                throw new DuplicateEmailException();
            }
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
