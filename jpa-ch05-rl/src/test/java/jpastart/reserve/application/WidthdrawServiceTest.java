package jpastart.reserve.application;

import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.UserDomain;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class WidthdrawServiceTest extends JpaTestBase {
    @Test
    public void withdrawTest() throws Exception {
        String email = "newuser@jpsworld.net";
        UserDomain.instance().givenUser(email, "새사용자");

        WithdrawService withdrawService = new WithdrawService();

        withdrawService.withdraw(email);

        assertThat(UserDomain.instance().countsByEmail(email), equalTo(0));
    }
}
