package jpastart.reserve.application;

import jpastart.reserve.model.User;
import jpastart.util.DBTestResource;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class GetUserServiceTest {
    @Rule
    public DBTestResource resource = new DBTestResource();
    private GetUserService getUserService = new GetUserService();

    @Test
    public void userFound_thenReturn() throws Exception {
        Optional<User> userOpt = getUserService.getUser("madvirus@madvirus.net");
        assertTrue(userOpt.isPresent());
        User user = userOpt.get();
        assertThat(user.getEmail(), equalTo("madvirus@madvirus.net"));
        assertThat(user.getName(), equalTo("최범균"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        assertThat(dateFormat.format(user.getCreateDate()), equalTo("2016-06-05 01:02:03"));
    }


    @Test
    public void noUser_returnNull() throws Exception {
        Optional<User> userOpt = getUserService.getUser("madvirus2@madvirus.net");
        assertFalse(userOpt.isPresent());
    }
}
