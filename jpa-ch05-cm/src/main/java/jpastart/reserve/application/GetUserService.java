package jpastart.reserve.application;

import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

public class GetUserService {

    @Inject
    private UserRepository userRepository;

    @Transactional
    public Optional<User> getUser(String email) {
        User user = userRepository.find(email);
        return Optional.ofNullable(user);
    }
}
