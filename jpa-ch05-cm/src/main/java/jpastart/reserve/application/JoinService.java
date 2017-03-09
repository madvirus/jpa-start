package jpastart.reserve.application;

import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;

public class JoinService {
    @Inject
    private UserRepository userRepository;

    @Transactional
    public void join(User user) {
        User found = userRepository.find(user.getEmail());
        if (found != null) {
            throw new DuplicateEmailException();
        }
        userRepository.save(user);
    }
}
