package jpastart.reserve.application;

import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeNameService {
    private UserRepository userRepository;

    @Transactional
    public void changeName(String email, String newName) {
        User user = userRepository.findOne(email);
        if (user == null) throw new UserNotFoundException();
        user.changeName(newName);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
