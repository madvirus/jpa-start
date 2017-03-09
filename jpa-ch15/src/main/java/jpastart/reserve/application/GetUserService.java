package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;

public class GetUserService {
    private UserRepository userRepository = new UserRepository();

    public User getUser(String email) {
        try {
            return userRepository.find(email);
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }
}
