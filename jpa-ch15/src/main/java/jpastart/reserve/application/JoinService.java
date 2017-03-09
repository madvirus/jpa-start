package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;

import javax.persistence.EntityManager;

public class JoinService {
    private UserRepository userRepository = new UserRepository();

    public void join(User user) {
        EntityManager em = EMF.currentEntityManager();
        try {
            em.getTransaction().begin();
            User found = userRepository.find(user.getEmail());
            if (found != null) {
                throw new DuplicateEmailException();
            }
            userRepository.save(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }
}
