package jpastart.reserve.application;

import jpastart.reserve.model.User;
import jpastart.reserve.model.UserDomain;
import jpastart.util.DBTestResource;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.fail;

public class JoinServiceTest {
    @Rule
    public DBTestResource resource = new DBTestResource();
    private JoinService joinService = new JoinService();

    @Test
    public void newUser_join() throws Exception {
        User newUser = new User("newuser@newuser.net", "1234", new Date());
        joinService.join(newUser);
        UserDomain.assertUserExist(newUser.getEmail());
    }

    @Test
    public void duplicateEmail_exceptionThrown() {
        try {
            joinService.join(new User("madvirus@madvirus.net", "1234", new Date()));
            fail("익셉션 발생해야 함");
        } catch (DuplicateEmailException ex) {
        }
    }

}
