package jpastart.reserve;

import jpastart.reserve.model.User;
import jpastart.reserve.model.UserSearch;
import jpastart.util.DBTestResource;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class MetaModelTest {
    @Rule
    public DBTestResource resource = new DBTestResource();

    @Test
    public void metamodel() throws Exception {
        List<User> users = new UserSearch().search("최%");
        assertThat(users.size(), equalTo(1));
    }
}
