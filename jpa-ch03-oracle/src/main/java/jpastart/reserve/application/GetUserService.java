package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;

import javax.persistence.EntityManager;
import java.util.Optional;

public class GetUserService {
    public Optional<User> getUser(String email) {
        EntityManager em = EMF.createEntityManager();
        try {
            User user = em.find(User.class, email);
            return Optional.ofNullable(user);
        } finally {
            em.close();
        }
    }
}
