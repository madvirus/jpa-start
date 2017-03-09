package jpastart.reserve.application;

import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUserListService {
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> result = userRepository.findAll();
        return result;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
