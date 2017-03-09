package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;

import javax.persistence.EntityManager;

public class ChangeNameService {
    private UserRepository userRepository = new UserRepository();

    public void changeName(String email, String newName) {
        EntityManager em = EMF.currentEntityManager();
        try {
            em.getTransaction().begin();
            User user = userRepository.find(email);
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
