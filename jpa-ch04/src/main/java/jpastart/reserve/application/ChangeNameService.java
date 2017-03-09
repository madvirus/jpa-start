package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;

import javax.persistence.EntityManager;

public class ChangeNameService {
    public void changeName(String email, String newName) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, email);
            if (user == null) throw new UserNotFoundException();
            user.changeName(newName);
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
