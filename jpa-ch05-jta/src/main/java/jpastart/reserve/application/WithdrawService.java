package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class WithdrawService {
    private UserRepository userRepository;

    public void withdraw(String email) {
        try {
            EntityManager em = EMF.currentEntityManager();
            UserTransaction utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            utx.begin();
            em.joinTransaction();
            try {
                User user = userRepository.find(email);
                if (user == null) {
                    throw new UserNotFoundException();
                }
                userRepository.remove(user);
                utx.commit();
            } catch (Exception ex) {
                try {
                    utx.rollback();
                } catch (SystemException e) {
                }
                throw new RuntimeException(ex);
            } finally {
                EMF.closeCurrentEntityManager();
            }
        } catch(SystemException | NotSupportedException | NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
