package jpastart.reserve.repository;

import jpastart.reserve.model.Grade;
import jpastart.reserve.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryDefinition(domainClass = Hotel.class, idClass = String.class)
public interface HotelRepository {

    Hotel findOne(String id);

    Page<Hotel> findByGrade(Grade grade, Pageable pageable);

    Hotel findFirstByGradeOrderByNameAsc(Grade grade);

    List<Hotel> findFirst3ByGradeOrderByNameAsc(Grade grade);

    @Query("select h from Hotel h " +
            "where h.grade = ?1 and h.name like ?2 order by h.name desc")
    List<Hotel> findHotel1(Grade grade, String name);

    @Query("select h from Hotel h where h.grade = :grade")
    List<Hotel> findHotel2(@Param("grade") Grade grade, Sort sort);

    @Query(value = "select h from Hotel h where h.grade = :grade",
            countQuery = "select count(h) from Hotel h where h.grade = :grade")
    Page<Hotel> findHotel3(@Param("grade") Grade grade, Pageable pageable);

    Page<Hotel> findAll(Specification<Hotel> spec, Pageable pageable);
}
