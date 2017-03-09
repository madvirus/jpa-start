package jpastart.reserve.application;

import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class GetUserListService {
    @Inject
    private UserRepository userRepository;

    @Transactional
    public List<User> getAllUsers() {
        List<User> result = userRepository.findAll();
        return result;
    }
}
