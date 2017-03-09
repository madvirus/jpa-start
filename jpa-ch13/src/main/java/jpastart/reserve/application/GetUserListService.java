package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class GetUserListService {
    private UserRepository userRepository = new UserRepository();

    public List<User> getAllUsers() {
        EntityManager em = EMF.currentEntityManager();
        try {
            em.getTransaction().begin();
            List<User> result = userRepository.findAll();
            em.getTransaction().commit();
            return result;
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }
}
