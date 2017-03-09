package jpastart.reserve.repository;

import jpastart.main.SpringConfig;
import jpastart.reserve.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Sql("classpath:/init.sql")
public class UseRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void save() throws Exception {
        userRepository.save(new User("bkchoi@bkchoi.com", "bkchoi", new Date()));
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "user", "email = 'bkchoi@bkchoi.com'");
        assertThat(count, equalTo(1));
    }

    @Test
    public void findOne() throws Exception {
        User user = userRepository.findOne("madvirus@madvirus.net");
        assertThat(user, notNullValue());
        assertThat(userRepository.findOne("no@no.com"), nullValue());
    }

    @Test
    public void orderBy() throws Exception {
        List<User> users = userRepository.findByNameStartingWithOrderByNameAscCreateDateDesc("최");
        assertThat(users.isEmpty(), equalTo(false));
    }

    @Test
    public void sort() throws Exception {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "createDate"));
        // Sort sort2 = new Sort("name");
        // Sort sort3 = new Sort(Sort.Direction.ASC, "name", "createDate");
        List<User> users = userRepository.findByNameStartingWith("최", sort);
        assertThat(users.isEmpty(), equalTo(false));
    }

    @Test
    public void page() throws Exception {
        Sort sort = new Sort("name");
        PageRequest pageRequest = new PageRequest(0, 10, sort);
        List<User> users = userRepository.findByNameStartingWith("최", pageRequest);
        assertThat(users.isEmpty(), equalTo(false));
    }

    @Test
    public void findSingleResultByName() throws Exception {
        User user = userRepository.findByName("홍길동");
        assertThat(user, nullValue());
        User user1 = userRepository.findByName("고길동");
        assertThat(user1, notNullValue());

        try {
            User user3 = userRepository.findByName("최범균");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
