package jpastart.reserve.application;

import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;

public class WithdrawService {
    private UserRepository userRepository;

    @Transactional
    public void withdraw(String email) {
        User user = userRepository.find(email);
        if (user == null) {
            throw new UserNotFoundException();
        }
        userRepository.remove(user);
    }

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
