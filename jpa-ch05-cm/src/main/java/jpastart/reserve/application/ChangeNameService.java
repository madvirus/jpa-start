package jpastart.reserve.application;

import jpastart.reserve.model.User;
import jpastart.reserve.repository.UserRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;

public class ChangeNameService {

    @Inject
    private UserRepository userRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public void changeName(String email, String newName) {
        User user = userRepository.find(email);
        if (user == null) throw new UserNotFoundException();
        user.changeName(newName);
    }
}
