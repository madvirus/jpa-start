package jpastart.reserve.application;

import jpastart.reserve.model.UserDomain;
import jpastart.util.DBTestResource;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.fail;

public class WithdrawServiceTest {
    @Rule
    public DBTestResource resource = new DBTestResource();
    private WithdrawService withdrawService = new WithdrawService();

    @Test
    public void userExists_thenWirhdraw() throws Exception {
        withdrawService.withdraw("madvirus@madvirus.net");
        UserDomain.assertUserNotExist("madvirus@madvirus.net");
    }

    @Test
    public void noUser_thenException() throws Exception {
        try {
            withdrawService.withdraw("nouser@nouser.net");
            fail("발생해야함");
        } catch (UserNotFoundException e) {
        }
    }
}
