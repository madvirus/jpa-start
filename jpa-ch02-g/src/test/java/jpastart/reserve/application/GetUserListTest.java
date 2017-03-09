package jpastart.reserve.application;

import jpastart.reserve.model.User;
import jpastart.util.DBTestResource;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class GetUserListTest {
    @Rule
    public DBTestResource resource = new DBTestResource();
    private GetUserListService getUserListService = new GetUserListService();

    @Test
    public void userList() throws Exception {
        List<User> users = getUserListService.getAllUsers();
        assertThat(users.size(), equalTo(1));
    }
}
