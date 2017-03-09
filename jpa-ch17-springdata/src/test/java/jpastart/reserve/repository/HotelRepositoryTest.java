package jpastart.reserve.repository;

import jpastart.main.SpringConfig;
import jpastart.reserve.model.Grade;
import jpastart.reserve.model.Hotel;
import jpastart.reserve.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Sql("classpath:/init.sql")
public class HotelRepositoryTest {
    @Autowired
    HotelRepository hotelRepository;

    @Test
    public void findOne() throws Exception {
        Hotel hotel = hotelRepository.findOne("H100-01");
        assertThat(hotel, notNullValue());
    }

    @Test
    public void page() throws Exception {
        Pageable pageable = new PageRequest(1, 5, new Sort("name"));
        Page<Hotel> hotelPage = hotelRepository.findByGrade(Grade.STAR7, pageable);

        List<Hotel> hotels = hotelPage.getContent();
        assertThat(hotels.size(), equalTo(3));
        assertThat(hotelPage.getTotalPages(), equalTo(2));
        assertThat(hotelPage.getTotalElements(), equalTo(8L));
        assertThat(hotelPage.getNumber(), equalTo(1));
        assertThat(hotelPage.getSize(), equalTo(5));
        assertThat(hotelPage.getNumberOfElements(), equalTo(3));
        assertThat(hotelPage.isFirst(), equalTo(false));
        assertThat(hotelPage.isLast(), equalTo(true));

        List<Grade> grades = hotelPage.map(h -> h.getGrade()).getContent();
        assertThat(grades, everyItem(equalTo(Grade.STAR7)));
    }

    @Test
    public void findFirst() throws Exception {
        Hotel hotel = hotelRepository.findFirstByGradeOrderByNameAsc(Grade.STAR7);
        assertThat(hotel, notNullValue());

        Hotel noHotel = hotelRepository.findFirstByGradeOrderByNameAsc(Grade.STAR1);
        assertThat(noHotel, nullValue());
    }

    @Test
    public void findFirst3() throws Exception {
        List<Hotel> hotels = hotelRepository.findFirst3ByGradeOrderByNameAsc(Grade.STAR7);
        assertThat(hotels.size(), equalTo(3));

        List<Hotel> hotels2 = hotelRepository.findFirst3ByGradeOrderByNameAsc(Grade.STAR3);
        assertThat(hotels2.size(), equalTo(1));
    }

    @Test
    public void queryAnnotation() throws Exception {
        List<Hotel> hotels1 = hotelRepository.findHotel1(Grade.STAR7, "%구로%");
        assertThat(hotels1.get(0).getId(), equalTo("H100-09"));

        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "name"));
        List<Hotel> hotels2 = hotelRepository.findHotel2(Grade.STAR7, sort);
        assertThat(hotels2.get(0).getId(), equalTo("H100-09"));

        Pageable pageable = new PageRequest(0, 3, sort);
        Page<Hotel> hotels3 = hotelRepository.findHotel3(Grade.STAR7, pageable);
        assertThat(hotels3.getContent().get(0).getId(), equalTo("H100-09"));
    }

    @Test
    public void specification() throws Exception {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "name"));
        Pageable pageable = new PageRequest(0, 10, sort);
        Specification<Hotel> bestGradeSpec = HotelSpecs.bestGrade();
        Page<Hotel> pageHotels = hotelRepository.findAll(bestGradeSpec, pageable);
        assertThat(pageHotels.getNumberOfElements(), equalTo(8));
    }

    @Test
    public void and() throws Exception {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "name"));
        Pageable pageable = new PageRequest(0, 10, sort);

        Specifications<Hotel> specs = Specifications.where(HotelSpecs.bestGrade());
        specs = specs.and(HotelSpecs.nameLike("구로"));
        Page<Hotel> hotels = hotelRepository.findAll(specs, pageable);
        assertThat(hotels.getNumberOfElements(), equalTo(8));
    }

    @Test
    public void emptySpec() throws Exception {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "name"));
        Pageable pageable = new PageRequest(0, 10, sort);
        Specifications<Hotel> emptySpec = Specifications.where(null);
        Page<Hotel> hotels = hotelRepository.findAll(emptySpec, pageable);
        assertThat(hotels.getNumberOfElements(), equalTo(10));
    }

    @Test
    public void nullSpec() throws Exception {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "name"));
        Pageable pageable = new PageRequest(0, 10, sort);
        Page<Hotel> hotels = hotelRepository.findAll(null, pageable);
        assertThat(hotels.getNumberOfElements(), equalTo(10));
    }
}
