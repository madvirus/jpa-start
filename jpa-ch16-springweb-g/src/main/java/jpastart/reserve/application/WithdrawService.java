package jpastart.reserve.application;

import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
