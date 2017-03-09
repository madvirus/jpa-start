package jpastart.reserve.application;

import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import jpastart.reserve.model.UserDomain;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class JoinServiceTest extends JpaTestBase {
    @Test
    public void join() throws Exception {
        UserDomain.instance().givenNoUser("newuser@newuser.com");

        JoinService joinService = new JoinService();

        joinService.join(new User("newuser@newuser.com", "새사용자", new Date()));

        assertThat(UserDomain.instance().countsByEmail("newuser@newuser.com"), equalTo(1));
    }
}
