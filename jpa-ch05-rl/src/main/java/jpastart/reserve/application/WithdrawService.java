package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class WithdrawService {
    private UserRepository userRepository = new UserRepository();

    public void withdraw(String email) {
        EntityManager em = EMF.currentEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User user = userRepository.find(email);
            if (user == null) {
                throw new UserNotFoundException();
            }
            userRepository.remove(user);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }

}
