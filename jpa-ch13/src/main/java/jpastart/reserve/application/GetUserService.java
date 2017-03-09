package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;

import java.util.Optional;

public class GetUserService {
    private UserRepository userRepository = new UserRepository();

    public Optional<User> getUser(String email) {
        try {
            User user = userRepository.find(email);
            return Optional.ofNullable(user);
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }
}
