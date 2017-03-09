package jpastart.reserve.application;

import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JoinService {
    private UserRepository userRepository;

    @Transactional
    public void join(User user) {
        User found = userRepository.findOne(user.getEmail());
        if (found != null) {
            throw new DuplicateEmailException();
        }
        userRepository.save(user);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
