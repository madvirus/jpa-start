package jpastart.reserve.repository;

import jpastart.reserve.model.Grade;
import jpastart.reserve.model.Hotel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class HotelSpecs {
    public static Specification<Hotel> bestGrade() {
        return new Specification<Hotel>() {
            @Override
            public Predicate toPredicate(
                    Root<Hotel> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                return cb.equal(root.get("grade"), Grade.STAR7);
            }
        };
    }

    public static Specification<Hotel> nameLike(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }
}
